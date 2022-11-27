
import android.text.TextUtils;

import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;


public class FileDownloadUtils {
    public static class DownloadData {
        public String minType;
        public String filePath;
        public String fileName;
        public int downloadProgress;

        public DownloadData(  String fileName, int downloadProgress) {
            this.filePath = filePath;
            this.fileName = fileName;
            this.downloadProgress = downloadProgress;
        }

        public DownloadData(String minType, String filePath, int downloadProgress) {
            this.minType = minType;
            this.filePath = filePath;
            this.downloadProgress = downloadProgress;
        }

        public DownloadData(int downloadProgress) {
            this.downloadProgress = downloadProgress;
        }

        public DownloadData(String minType, String filePath) {
            this.minType = minType;
            this.filePath = filePath;
        }
    }

    public static Observable<DownloadData> downloadFileByOkio(final String url, HashMap<String, String> header, final String filePath, final String fileDefaultName) {
        return Observable.create((ObservableOnSubscribe<DownloadData>) emitter -> {

                    Request.Builder request = new Request.Builder().url(url);
                    for (Map.Entry<String, String> entry :
                            header.entrySet()) {
                        request.addHeader(entry.getKey(), entry.getValue());
                    }
                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(request.build()).execute();
                    ResponseBody body = response.body();
                    Headers headers = response.headers();
                    String contentType = headers.get("Content-Type");// a/pdf
                    File file = null;
                    String fileName = "";
                    if (!TextUtils.isEmpty(fileDefaultName)) {
                        if (filePath.endsWith("/")) {
                            file = new File(filePath + fileDefaultName);
                        } else {
                            file = new File(filePath + "/" + fileDefaultName);
                        }
                        fileName=fileDefaultName;
                    } else {
                        String contentDisposition = headers.get("Content-Disposition");// a/pdf
                        if (contentDisposition != null) {
                            String[] fileNames = contentDisposition.trim().split(";");
                            if (fileNames.length > 0) {
                                // 只支持utf8
                                fileName = URLDecoder.decode(fileNames[fileNames.length - 1].replace("filename*=UTF-8''", ""), "UTF-8");
                            }
                        }
                        if (filePath.endsWith("/")) {
                            file = new File(filePath + fileName);
                        } else {
                            file = new File(filePath + "/" + fileName);
                        }
                    }
                    if(file.exists()){
                        file.delete();
                    }
                    file.createNewFile();
                    if (body == null) {
                        emitter.onError(new NullPointerException("Cant download file"));
                    } else {
                        long contentLength = body.contentLength();
                        BufferedSource source = body.source();
                        BufferedSink sink = Okio.buffer(Okio.sink(file));
                        Buffer sinkBuffer = sink.getBuffer();
                        long totalBytesRead = 0;
                        int bufferSize = 8 * 1024;
                        long bytesRead;
                        while ((bytesRead = source.read(sinkBuffer, bufferSize)) != -1) {
                            sink.emit();
                            totalBytesRead += bytesRead;
                            int progress = (int) ((totalBytesRead * 100) / contentLength);
                            emitter.onNext(new DownloadData(fileName,progress));
                        }
                        sink.flush();
                        emitter.onNext(new DownloadData(contentType,
                                file.getPath()));
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}

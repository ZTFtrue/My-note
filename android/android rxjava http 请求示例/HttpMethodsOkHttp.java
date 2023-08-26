package com.ztftrue.app;

import com.google.gson.Gson;
import com.ztftrue.app.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
/**
 * 文件处理
 */
public class HttpMethodsOkHttp {

    public static String uploadFile(String userToken, File file) throws IOException {
        String url = BuildConfig.BaseUrl + "uploadFile";
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("multipart/form-data; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, file);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                .newBuilder();
        urlBuilder.addQueryParameter("1", "1");
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                //在此处添加多个requestBody实现多文件上传
                .addFormDataPart("file", file.getName(), requestBody)
                .build();
        // 第四步，构建Request请求对象
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .post(body)
                .addHeader("content-type", "multipart/form-data")
                .addHeader("Authorization", userToken)
                .build();
        String res = Objects.requireNonNull(okHttpClient.newCall(request).execute().body()).string();
        Gson gson = new Gson();
//        UploadReturn returnUpload = gson.fromJson(res, UploadReturn.class);
//        if (returnUpload.code==200) {
//            return returnUpload.getUrl();
//        } else {
//            throw new NullPointerException(returnUpload.getMessage());
//        }
        return null;
    }

    public interface DownloadFile {
        void downloadFile(int d);
    }

    public static void download(final String url, File file, DownloadFile downloadFile) {
        Request request = new Request.Builder().url(url).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // 下载失败
                e.printStackTrace();
                downloadFile.downloadFile(-1);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                BufferedSink bufferedSink = null;
                if (response.code() != 200) {
                    downloadFile.downloadFile(-1);
                }
                try {

//                    Sink sink = Okio.sink(file);
//                    bufferedSink = Okio.buffer(sink);

                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos;
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum =sum+ len;
                        int progress = (int) (sum*100 / total);
                        downloadFile.downloadFile(progress);
                    }
/** -2 完成 -1 错误 */
//                    bufferedSink.writeAll(response.body().source());
//                    bufferedSink.close();
                        downloadFile.downloadFile(-2);
                } catch (Exception e) {
                    e.printStackTrace();
                    downloadFile.downloadFile(-1);
                } finally {
                    if (bufferedSink != null) {
                        bufferedSink.close();
                    }
                }
            }
        });
    }
}

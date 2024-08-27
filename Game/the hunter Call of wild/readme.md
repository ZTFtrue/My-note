#

游戏文件提取

File extractors

## deca_gui

在 `github` 搜索 [deca_gui](https://github.com/kk49/deca)  下载工具文件。

其它内容加入：Discord

## 提取音频

先用工具提取出后缀为`fmod_bankc` 或 `fsb` 的文件。

After extracting the sound files from fmod_banks with deca
Download this :

<https://www.mediafire.com/file/2uqggx7fd4asj9i/quickbms.rar/file>
double click on quickbms.exe

chose script dump_fsbs.bms

then chose exemple char_machine_drea_c4.fmod_bankc , wil result this file
0000000.fsb


如果是 `fmod_bankc` 文件， 需要 `.\quickbms.exe .\dump_fsbs.bms .\music.fmod_bankc ./1` 转成  `fsb` 的文件.

## fsb 转wav

After resulted extract use this:

<https://www.mediafire.com/file/x88gch0v2t07ovf/fmod_extractors.rar/file>
Drag the resulted file "0000000.fsb" over this fsb_aud_extr.exe
Will result .wav files

批量转换 `java` 代码

```java
public class Main {
    public static void main(String[] args) {
        boolean isWindows = true;
        String workDic = "F:/GameFileExtractors/fmod_extractors";
        try (Stream<Path> files = Files.list(Path.of(workDic + "/eng/"))) {
            List<Path> list = files.toList();
            for (Path path : list) {
                Process process;
                String[] cmd = {"powershell", "Start-Process", "./fsb_aud_extr.exe", path.toString(), "-NoNewWindow -Wait"};
                try {
                    process = Runtime.getRuntime()
                            .exec(cmd, new String[]{}, new File(workDic));
                    InputStream outputStream = process.getInputStream();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(outputStream));
                    String line;
                    StringBuilder output = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                    System.out.println(output);
                    int exitVal = process.waitFor();
                    if (exitVal == 0) {
                        Files.move(Path.of(workDic + "/" + output.deleteCharAt(output.length() - 1) + ".wav"), Path.of(workDic + "/res/" + path.getFileName().toString().replace(".fsb", ".wav")));
                    } else {
                        //abnormal...
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        processBuilder.command("cmd", "/c", "hello.bat");
//        File dir = new File("C:\\Users\\mkyong\\");
//        processBuilder.directory(dir);
    }
}
```

import www.whisper.jsch.JschSFTP;
import www.whisper.jsch.ModifiedWatch;
import www.whisper.jsch.SFTPUtil;

/**
 * JSCH简单应用：监听指定目录下的文件修改，并同步到linux服务器
 * @author little whisper
 * @date 2021/1/4
 */
public class JSCHMain {
    public static void main(String[] args) {
        JschSFTP jschSFTP = new JschSFTP();
        SFTPUtil.connect(jschSFTP);
        System.out.println("连接成功......");
        ModifiedWatch.watch("D:\\sublime_text\\nginx", "/opt/data", jschSFTP);
    }
}

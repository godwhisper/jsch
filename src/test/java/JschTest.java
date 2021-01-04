import org.junit.Test;
import www.whisper.jsch.JschSFTP;
import www.whisper.jsch.SFTPUtil;

import java.util.Properties;

/**
 * 测试类
 * @author little whisper
 * @date 2021/1/4 13:20
 */
public class JschTest {

    @Test
    public void testLoadProperties() {
        Properties properties = SFTPUtil.loadJschConfig("jsch.properties");
        System.out.println(properties.toString());
    }

    @Test
    public void testDownload() {
        JschSFTP jschSFTP = new JschSFTP();
        SFTPUtil.connect(jschSFTP);
        SFTPUtil.download(jschSFTP, "/opt/data", "firewall-cmd.txt", "D:");
    }

}

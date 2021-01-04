package www.whisper.jsch;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Properties;

/**
 * sftp工具类
 * @author little whisper
 * @date 2021/1/4 12:19
 */
public class SFTPUtil {

    /**
     * 建立连接
     * @param jschSFTP 连接管理类
     */
    public static void connect(JschSFTP jschSFTP) {
        JSch jSch = new JSch();
        Properties connectProperties = loadJschConfig("jsch.properties");
        try {
            Session session = jSch.getSession(connectProperties.getProperty("username"), connectProperties.getProperty("host"), Integer.valueOf(connectProperties.getProperty("port", "22")));
            session.setPassword(connectProperties.getProperty("password", ""));
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            jschSFTP.jschSFTPFill(session, (ChannelSftp) channel, connectProperties);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载连接配置
     * @param path 配置路径
     * @return
     */
    public static Properties loadJschConfig(String path) {
        Properties properties = new Properties();
        try {
            InputStream in = SFTPUtil.class.getClassLoader().getResourceAsStream(path);
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 关闭连接
     * @param jschSFTP 连接管理类
     */
    public static void disConnect(JschSFTP jschSFTP) {
        if (jschSFTP.getChannelSftp() != null) {
            jschSFTP.getChannelSftp().disconnect();
        }
        if (jschSFTP.getSession() != null) {
            jschSFTP.getSession().disconnect();
        }
    }

    /**
     * 文件上传
     * @param jschSFTP 连接信息
     * @param directory 上传目录
     * @param uploadFile 上传文件绝对路径
     */
    public static void upload(JschSFTP jschSFTP, String directory, String uploadFile) {
        try {
            jschSFTP.getChannelSftp().cd(directory);
            File file = new File(uploadFile);
            InputStream in = new FileInputStream(file);
            jschSFTP.getChannelSftp().put(in, file.getName());
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     * @param jschSFTP 连接信息
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param saveFile 下载后文件的保存目录
     */
     public static void download(JschSFTP jschSFTP, String directory, String downloadFile, String saveFile) {
         try {
             jschSFTP.getChannelSftp().cd(directory);
             File file = new File(saveFile);
             if (!file.exists()) {
                 file.mkdirs();
             }
             OutputStream out = new FileOutputStream(new File(saveFile, downloadFile));
             jschSFTP.getChannelSftp().get(downloadFile, out);
             out.flush();
             out.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
}

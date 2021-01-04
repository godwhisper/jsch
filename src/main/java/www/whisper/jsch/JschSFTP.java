package www.whisper.jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

import java.util.Properties;

/**
 * 连接管理类
 * @author little whisper
 * @date 2021/1/4 12:14
 */
public class JschSFTP {
    /**
     * 会话
     */
    private Session session;
    /**
     * sftp操作类
     */
    private ChannelSftp channelSftp;
    /**
     * 连接参数
     */
    private Properties properties;

    public void jschSFTPFill(Session session, ChannelSftp channelSftp, Properties properties) {
        this.session = session;
        this.channelSftp = channelSftp;
        this.properties = properties;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public ChannelSftp getChannelSftp() {
        return channelSftp;
    }

    public void setChannelSftp(ChannelSftp channelSftp) {
        this.channelSftp = channelSftp;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}

package www.whisper.jsch;


import java.io.File;
import java.nio.file.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 目录文件修改监听
 * @author little whisper
 * @date 2021/1/4 15:09
 */
public class ModifiedWatch {
    /**
     * 监听目录文件变化，并上传
     * @param directory 待监听目录
     * @param jschSFTP sftp连接管理对象
     */
    public static void watch(String directory, JschSFTP jschSFTP) {
        Path path = Paths.get(directory);
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            // 只监听文件修改
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
            // 每隔5s定时检查文件修改
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                WatchKey watchKey = watchService.poll();
                while (watchKey != null) {
                    for (WatchEvent event : watchKey.pollEvents()) {
                        if (event.kind() != StandardWatchEventKinds.ENTRY_MODIFY) {
                            continue;
                        }
                        File parent = path.toFile();
                        File[] files = parent.listFiles(File::isFile);
                        if (files == null) {
                            continue;
                        }
                        for (File file : files) {
                            // 处理
                            SFTPUtil.upload(jschSFTP, directory, file.getAbsolutePath());
                        }
                    }
                    watchKey = watchService.poll();
                }
            }, 0, 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

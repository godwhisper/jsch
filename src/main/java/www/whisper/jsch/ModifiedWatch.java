package www.whisper.jsch;

import org.apache.commons.collections4.ListUtils;

import java.io.File;
import java.nio.file.*;

/**
 * 目录文件修改监听
 * @author little whisper
 * @date 2021/1/4 15:09
 */
public class ModifiedWatch {
    /**
     * 监听目录文件变化，并上传
     * @param directory 待监听目录
     */
    public static void watch(String directory, JschSFTP jschSFTP, String destDirectory) {
        Path path = Paths.get(directory);
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            // 只监听文件修改
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                WatchKey watchKey = watchService.take();
                for (WatchEvent event : watchKey.pollEvents()) {
                    if (event.kind() != StandardWatchEventKinds.ENTRY_MODIFY) {
                        continue;
                    }
                    File parent = path.toFile();
                    File[] files = parent.listFiles();
                    if (files == null) {
                        continue;
                    }
                    for (File file : files) {
                        // 处理
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

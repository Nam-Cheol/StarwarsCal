package starwarsCalender;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileLockHandler {

    private static FileLock lock;
    private static FileChannel channel;

    public static boolean lockInstance() {
        try {
            File file = new File("starwarsCalender.lock");
            channel = new FileOutputStream(file).getChannel();
            lock = channel.tryLock();
            if (lock == null) {
                channel.close();
                return false;
            }
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    try {
                        lock.release();
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

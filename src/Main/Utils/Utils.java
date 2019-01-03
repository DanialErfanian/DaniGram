package Main.Utils;

import java.io.File;

public class Utils {
    public static String getFileExtention(File file) {
        String extension = null;
        String fileName = file.getPath();
        int i = fileName.lastIndexOf('.');
        if (i > 0) extension = fileName.substring(i + 1);
        return extension;
    }
}

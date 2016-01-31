package utils;


import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by Administrator on 2016-01-25.
 */
public class FileUtils {
    /**
     * @author chenzheng_Java
     * 保存用户输入的内容到文件
     */
    public static void save(Context context, String fileName, String content) {
        try {

            FileOutputStream outputStream = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @author
     *
     */
    public static  String read(Context context, String fileName) {
        String content = "";
        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (inputStream.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            inputStream.close();
            arrayOutputStream.close();
            content = new String(arrayOutputStream.toByteArray());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    public static Vector<String> getTxetFileName(Context context) {
        Vector<String> vecFile = new Vector<>();
        File file = context.getFilesDir();
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {

            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                // end with txt
                if (filename.trim().toLowerCase().endsWith(".txt")) {
                    vecFile.add(filename);
                }
            }
        }
        Collections.reverse(vecFile);
        return vecFile;
    }

}

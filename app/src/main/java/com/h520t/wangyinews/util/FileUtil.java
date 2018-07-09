package com.h520t.wangyinews.util;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class FileUtil {
    public static File getImgFile(Context context,String md5_name){
        File directory = context.getFilesDir();
        File cacheFile = new File(directory, Contants.IMG_CACHE);
        if (!cacheFile.exists()) {
            cacheFile.mkdirs();
        }

        File imgFile = new File(cacheFile, md5_name + ".jpg");
        Log.i("h520it", "getImgFile: "+imgFile.getAbsolutePath());
        return imgFile;
    }
}

package cn.xd.desktopet.util;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

/**
 * 特定类型的文件扫描工具
 */
public class FileUtil {

    private static String[] audioFileSuffix={".mp3",".wma",".wav"};

    public static List<File> fileScan(File dir, String fileSuffix){
        List<File> res=new ArrayList<>();
        if(dir.isDirectory()){
            File[] tempFile=dir.listFiles();
            if(tempFile!=null){
                for(int i=0;i<tempFile.length;++i){
                    File file=tempFile[i];
                    res.addAll(fileScan(file,fileSuffix));
                }
            }
        }else if(dir.isFile()){
            int dot=dir.getName().lastIndexOf(".");
            if(dot>-1&&dot<dir.getName().length()){
                String extenName=dir.getName().substring(dot,dir.getName().length());
                if(extenName.equals(fileSuffix)){
                    res.add(dir);
                }
            }
        }
        return res;
    }

    public static List<File> fileScan(File dir,String[] fileSuffixs){
        List<File> res=new ArrayList<>();
        if(dir.isDirectory()){
            File[] tempFile=dir.listFiles();
            if(tempFile!=null){
                for(int i=0;i<tempFile.length;++i){
                    File file=tempFile[i];
                    res.addAll(fileScan(file,fileSuffixs));
                }
            }
        }else if(dir.isFile()){
            int dot=dir.getName().lastIndexOf(".");
            if(dot>-1&&dot<dir.getName().length()){
                String extenName=dir.getName().substring(dot,dir.getName().length());
                for(String fileSuffix:fileSuffixs){
                    if(extenName.equals(fileSuffix)){
                        res.add(dir);
                        break;
                    }
                }
            }
        }
        return res;


    }
    public static List<File> getAudioFileInStorage(){
        File externalStorgae= Environment.getExternalStorageDirectory();
        return fileScan(externalStorgae,audioFileSuffix);
    }

}

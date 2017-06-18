package cn.xd.desktopet.model;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class Sound {

    public static String defaultSoundPath="";

    private String name;
    private String path;


    public Sound(String name, String path){
        this.name=name;
        this.path=path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

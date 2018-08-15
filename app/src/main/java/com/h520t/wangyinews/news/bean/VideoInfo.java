package com.h520t.wangyinews.news.bean;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class VideoInfo {

    private String mp4_url;
    private int sizeHD;

    public VideoInfo() {
    }

    public VideoInfo(String mp4_url, int sizeHD) {

        this.mp4_url = mp4_url;
        this.sizeHD = sizeHD;
    }

    public String getmp4_url() {
        return mp4_url;
    }

    public void setmp4_url(String mp4_url) {
        this.mp4_url = mp4_url;
    }

    public int getSizeHD() {
        return sizeHD;
    }

    public void setSizeHD(int sizeHD) {
        this.sizeHD = sizeHD;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "mp4_url='" + mp4_url + '\'' +
                ", sizeHD=" + sizeHD +
                '}';
    }
}

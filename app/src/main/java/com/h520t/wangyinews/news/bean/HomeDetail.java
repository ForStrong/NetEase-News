package com.h520t.wangyinews.news.bean;

import java.util.List;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class HomeDetail {
    private String img;
    private String title;
    private String source;
    private String replyid;
    private int replyCount;
    private VideoInfo videoinfo;
    private List<ImgNewExtra> imgnewextra;


    public HomeDetail() {

    }
    public List<ImgNewExtra> getImgnewextra() {
        return imgnewextra;
    }

    public void setImgnewextra(List<ImgNewExtra> imgnewextra) {
        this.imgnewextra = imgnewextra;
    }

    public VideoInfo getVideoinfo() {
        return videoinfo;
    }

    public void setVideoinfo(VideoInfo videoinfo) {
        this.videoinfo = videoinfo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReplyid() {
        return replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    @Override
    public String toString() {
        return "HomeDetail{" +
                "img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", replyid='" + replyid + '\'' +
                ", replyCount=" + replyCount +
                ", videoinfo=" + videoinfo +
                ", imgnewextra=" + imgnewextra +
                '}';
    }
}

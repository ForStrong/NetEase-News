package com.h520t.wangyinews.news.bean;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class HotDetail {



    private String recSource;
    private String title;
    private String replyCount;
    private String imgsrc;
    public HotDetail() {
    }

    public HotDetail(String recSource, String title, String replyCount, String imgsrc) {
        this.recSource = recSource;
        this.title = title;
        this.replyCount = replyCount;
        this.imgsrc = imgsrc;
    }

    @Override
    public String toString() {
        return "HotDetail{" +
                "recSource='" + recSource + '\'' +
                ", title='" + title + '\'' +
                ", replyCount='" + replyCount + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                '}';
    }

    public String getRecSource() {
        return recSource;
    }

    public void setRecSource(String recSource) {
        this.recSource = recSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }
}

package com.h520t.wangyinews.news.bean;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class HotDetail {



    private String source;
    private String title;
    private int replyCount;
    private String img;
    public HotDetail() {
    }

    public HotDetail(String source, String title, int replyCount, String img) {
        this.source = source;
        this.title = title;
        this.replyCount = replyCount;
        this.img = img;
    }

    @Override
    public String toString() {
        return "HotDetail{" +
                "source='" + source + '\'' +
                ", title='" + title + '\'' +
                ", replyCount=" + replyCount +
                ", img='" + img + '\'' +
                '}';
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

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

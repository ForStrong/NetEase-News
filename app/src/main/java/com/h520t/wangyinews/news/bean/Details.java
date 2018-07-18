package com.h520t.wangyinews.news.bean;

import java.util.List;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class Details {
    List<DetailsImg> img;
    String title;
    String source;
    int replyCount;
    String ptime;
    String body;

    public Details() {
    }

    public List<DetailsImg> getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public String getPtime() {
        return ptime;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Details{" +
                "img=" + img +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", replyCount=" + replyCount +
                ", ptime='" + ptime + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}

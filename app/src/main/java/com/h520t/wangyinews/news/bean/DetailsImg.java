package com.h520t.wangyinews.news.bean;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class DetailsImg {

    /**
     * ref : <!--IMG#0-->
     * src : http://cms-bucket.nosdn.127.net/2018/07/16/a8a5b0d85ca1438fb1e58549196a0d32.jpeg
     * alt :
     * pixel : 509*334
     */

    private String ref;
    private String src;
    private String alt;
    private String pixel;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getPixel() {
        return pixel;
    }

    public void setPixel(String pixel) {
        this.pixel = pixel;
    }

    @Override
    public String toString() {
        return "DetailsImg{" +
                "ref='" + ref + '\'' +
                ", src='" + src + '\'' +
                ", alt='" + alt + '\'' +
                ", pixel='" + pixel + '\'' +
                '}';
    }
}

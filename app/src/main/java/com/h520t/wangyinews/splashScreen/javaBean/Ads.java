package com.h520t.wangyinews.splashScreen.javaBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class Ads implements Serializable{
    private int next_req ;
    private List<AdsBean> ads;

    @Override
    public String toString() {
        return "AdsDetail{" +
                "next_req=" + next_req +
                ", ads=" + ads +
                '}';
    }

    public int getNext_req() {
        return next_req;
    }

    public void setNext_req(int next_req) {
        this.next_req = next_req;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }
}

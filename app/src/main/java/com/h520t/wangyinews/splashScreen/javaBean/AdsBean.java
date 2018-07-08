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

public class AdsBean implements Serializable{
    private List<String> res_url;
    private Action action_params;

    public List<String>  getRes_url() {
        return res_url;
    }

    public void setRes_url(List<String>  res_url) {
        this.res_url = res_url;
    }

    public Action getAction_params() {
        return action_params;
    }

    public void setAction_params(Action action_params) {
        this.action_params = action_params;
    }

    @Override
    public String toString() {
        return "AdsBean{" +
                "res_url=" + res_url +
                ", action_params=" + action_params +
                '}';
    }
}

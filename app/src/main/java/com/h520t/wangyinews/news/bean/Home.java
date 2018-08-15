package com.h520t.wangyinews.news.bean;

import java.util.ArrayList;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class Home {
    private ArrayList<HomeDetail> T1348647909107;

    public Home() {
    }

    public Home(ArrayList<HomeDetail> t1348647909107) {
        T1348647909107 = t1348647909107;
    }

    public ArrayList<HomeDetail> getT1348647909107() {
        return T1348647909107;
    }

    public void setT1348647909107(ArrayList<HomeDetail> t1348647909107) {
        T1348647909107 = t1348647909107;
    }

    @Override
    public String toString() {
        return "Home{" +
                "T1348647909107=" + T1348647909107 +
                '}';
    }
}

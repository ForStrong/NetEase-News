package com.h520t.wangyinews.news.bean;

import java.util.List;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class Hot {
    private List<HotDetail> T1348647909107;

    public Hot() {

    }

    @Override
    public String toString() {
        return "Hot{" +
                "T1348647909107=" + T1348647909107 +
                '}';
    }

    public List<HotDetail> getT1348647909107() {
        return T1348647909107;
    }

    public void setT1348647909107(List<HotDetail> t1348647909107) {
        T1348647909107 = t1348647909107;
    }

    public Hot(List<HotDetail> t1348647909107) {

        T1348647909107 = t1348647909107;
    }
}

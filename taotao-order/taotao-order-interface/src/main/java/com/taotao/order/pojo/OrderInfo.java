package com.taotao.order.pojo;

import com.taotao.pojo.Tborder;
import com.taotao.pojo.Tborderitem;
import com.taotao.pojo.Tbordershipping;

import java.io.Serializable;
import java.util.List;

public class OrderInfo extends Tborder implements Serializable {
    private List<Tborderitem> tborderitems;
    private Tbordershipping tbordershipping;

    public List<Tborderitem> getTborderitems() {
        return tborderitems;
    }

    public void setTborderitems(List<Tborderitem> tborderitems) {
        this.tborderitems = tborderitems;
    }

    public Tbordershipping getTbordershipping() {
        return tbordershipping;
    }

    public void setTbordershipping(Tbordershipping tbordershipping) {
        this.tbordershipping = tbordershipping;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "tborderitems=" + tborderitems +
                ", tbordershipping=" + tbordershipping +
                '}';
    }
}

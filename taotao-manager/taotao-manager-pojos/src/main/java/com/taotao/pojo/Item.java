package com.taotao.pojo;

public class Item extends Tbitem {
    public String[] getImages() {
        String image2 = this.getImage();
        if (image2 != null && !"".equals(image2)) {
            String[] strings = image2.split(",");
            return strings;
        }
        return null;
    }

    public Item() {

    }

    public Item(Tbitem tbitem) {
        this.setBarcode(tbitem.getBarcode());
        this.setCid(tbitem.getCid());
        this.setCreated(tbitem.getCreated());
        this.setId(tbitem.getId());
        this.setImage(tbitem.getImage());
        this.setNum(tbitem.getNum());
        this.setPrice(tbitem.getPrice());
        this.setSellPoint(tbitem.getSellPoint());
        this.setStatus(tbitem.getStatus());
        this.setTitle(tbitem.getTitle());
        this.setUpdated(tbitem.getUpdated());
    }
}

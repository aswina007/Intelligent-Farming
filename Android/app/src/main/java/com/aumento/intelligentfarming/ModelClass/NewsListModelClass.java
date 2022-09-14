package com.aumento.intelligentfarming.ModelClass;

public class NewsListModelClass {

    String id;
    String headline;
    String desc;
    String image;

    public NewsListModelClass(String id, String headline, String desc, String image) {
        this.id = id;
        this.headline = headline;
        this.desc = desc;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getHeadline() {
        return headline;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }
}

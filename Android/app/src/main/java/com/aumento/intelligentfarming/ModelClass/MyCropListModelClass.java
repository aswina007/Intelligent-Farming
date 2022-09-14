package com.aumento.intelligentfarming.ModelClass;

public class MyCropListModelClass {

    String id;
    String crop_name;
    String crop_image;

    public MyCropListModelClass(String id, String crop_name, String crop_image) {
        this.id = id;
        this.crop_name = crop_name;
        this.crop_image = crop_image;
    }

    public String getId() {
        return id;
    }

    public String getCrop_name() {
        return crop_name;
    }

    public String getCrop_image() {
        return crop_image;
    }
}

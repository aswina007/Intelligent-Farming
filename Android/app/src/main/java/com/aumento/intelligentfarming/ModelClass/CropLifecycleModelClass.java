package com.aumento.intelligentfarming.ModelClass;

public class CropLifecycleModelClass {

    String id;
    String week;
    String desc;

    public CropLifecycleModelClass(String id, String week, String desc) {
        this.id = id;
        this.week = week;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public String getWeek() {
        return week;
    }

    public String getDesc() {
        return desc;
    }
}

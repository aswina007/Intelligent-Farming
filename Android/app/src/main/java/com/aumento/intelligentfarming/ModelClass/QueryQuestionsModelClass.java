package com.aumento.intelligentfarming.ModelClass;

public class QueryQuestionsModelClass {

    String id;
    String Qtn;
    String date;

    public QueryQuestionsModelClass(String id, String qtn, String date) {
        this.id = id;
        Qtn = qtn;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getQtn() {
        return Qtn;
    }

    public String getDate() {
        return date;
    }
}

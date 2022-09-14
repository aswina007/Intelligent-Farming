package com.aumento.intelligentfarming.ModelClass;

public class QueryAnswerModelClass {

    String name;
    String image;
    String date;
    String answer;

    public QueryAnswerModelClass(String name, String image, String date, String answer) {
        this.name = name;
        this.image = image;
        this.date = date;
        this.answer = answer;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getAnswer() {
        return answer;
    }
}

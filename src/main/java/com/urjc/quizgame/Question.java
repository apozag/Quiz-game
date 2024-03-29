package com.urjc.quizgame;

import java.util.ArrayList;

public class Question {
    private int id;
    private String question;
    private ArrayList<String> options = new ArrayList<String>();
    private int answer;
    private String imageId = null;
    private String musicId = null;


    public Question() {}

    public Question(int id, String question, ArrayList<String> options, int answer) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public Question(int id, String question, ArrayList<String> options, int answer, String imageId) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.imageId = imageId;
    }

    public Question(int id, String question, ArrayList<String> options, int answer, String imageId, String musicId) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.imageId = imageId;
        this.musicId = musicId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public String getOption(int pos){return options.get(pos);}

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getImageId() { return imageId; }

    public void setImageId(String imageId) { this.imageId = imageId; }

    public String getMusicId() { return musicId; }

    public void setMusicId(String musicId) { this.musicId = musicId; }
}




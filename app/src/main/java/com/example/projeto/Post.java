package com.example.projeto;

import java.util.Date;

public class Post {

    String author_name;
    String getAuthor_email;
    String date;
    String content;

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getGetAuthor_email() {
        return getAuthor_email;
    }

    public void setGetAuthor_email(String getAuthor_email) {
        this.getAuthor_email = getAuthor_email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post(){

    }

    public Post(String author_name, String getAuthor_email, String content) {
        this.author_name = author_name;
        this.getAuthor_email = getAuthor_email;
        this.content = content;
    }

    public Post(String author_name, String getAuthor_email, String date, String content) {
        this.author_name = author_name;
        this.getAuthor_email = getAuthor_email;
        this.date = date;
        this.content = content;
    }
}

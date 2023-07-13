package com.example.projeto;

import java.util.List;

public class Post {
    String postKey;
    String author_uuid;
    String author_name;
    String author_email;
    String date;
    String content;

    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getAuthor_uuid() {
        return author_uuid;
    }

    public void setAuthor_uuid(String author_uuid) {
        this.author_uuid = author_uuid;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_email() {
        return author_email;
    }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
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

    public Post(String postKey,String author_uuid, String author_name, String author_email, String date, String content) {
        this.postKey = postKey;
        this.author_uuid = author_uuid;
        this.author_name = author_name;
        this.author_email = author_email;
        this.date = date;
        this.content = content;
    }

    public Post(String postKey, String author_uuid, String author_name, String author_email, String date, String content, List<Comment> comments) {
        this.postKey = postKey;
        this.author_uuid = author_uuid;
        this.author_name = author_name;
        this.author_email = author_email;
        this.date = date;
        this.content = content;
        this.comments = comments;
    }
}

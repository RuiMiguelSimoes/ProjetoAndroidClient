package com.example.projeto;

import java.util.Date;
import java.util.UUID;

public class Post {

    String by;
    UUID uuid;
    Date date;
    String content;

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public Post(String by, String content) {
        this.by = by;
        this.content = content;
    }

    public Post(String by, UUID uuid, Date date, String content) {
        this.by = by;
        this.uuid = uuid;
        this.date = date;
        this.content = content;
    }

    public Post(String by, Date date, String content) {
        this.by = by;
        this.date = date;
        this.content = content;
    }
}

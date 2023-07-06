package com.example.projeto;

public class Comment {

    private String commentUid, comment_author_name, comment;

    public String getCommentUid() {
        return commentUid;
    }

    public void setCommentUid(String commentUid) {
        this.commentUid = commentUid;
    }

    public String getComment_author_name() {
        return comment_author_name;
    }

    public void setComment_author_name(String comment_author_name) {
        this.comment_author_name = comment_author_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    Comment(){

    }

    public Comment(String commentUid, String comment_author_name, String comment) {
        this.commentUid = commentUid;
        this.comment_author_name = comment_author_name;
        this.comment = comment;
    }
}

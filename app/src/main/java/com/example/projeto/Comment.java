package com.example.projeto;

public class Comment {
    private String commentId;
    private String commenterName;
    private String commentContent;
    private String commenterId;

    public String getCommentId() {
        return commentId;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public String getCommentContent() {
        return commentContent;
    }
    public Comment(String commentId, String commenterName, String commentContent) {
        this.commentId = commentId;
        this.commenterName = commenterName;
        this.commentContent = commentContent;
    }
    public Comment(){

    }

    public Comment(String commentId, String commenterName, String commentContent, String commenterId) {
        this.commentId = commentId;
        this.commenterName = commenterName;
        this.commentContent = commentContent;
        this.commenterId = commenterId;
    }
}



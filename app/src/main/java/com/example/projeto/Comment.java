package com.example.projeto;

public class Comment {
    private String commentId;
    private String commenterName;
    private String commentContent;
    private String commenteDate;
    private String commenterId;

    public String getCommenteDate() {
        return commenteDate;
    }

    public void setCommenteDate(String commenteDate) {
        this.commenteDate = commenteDate;
    }

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

    public Comment(String commentId, String commenterName, String commentContent, String commenteDate, String commenterId) {
        this.commentId = commentId;
        this.commenterName = commenterName;
        this.commentContent = commentContent;
        this.commenteDate = commenteDate;
        this.commenterId = commenterId;
    }
}



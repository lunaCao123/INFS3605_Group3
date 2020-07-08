package com.example.grou3v2.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Author.class,
                parentColumns = "authorId",
                childColumns = "authorId",
                onDelete = ForeignKey.NO_ACTION),
        @ForeignKey(entity = Crisis.class,
                parentColumns = "crisisCode",
                childColumns = "crisisCode",
                onDelete = ForeignKey.NO_ACTION)})
public class Post {

    private int postNumber;
    private Author authorId;
    private int pubDate;
    private String title;
    private String messageContent;
    private Crisis crisisCode;
    private String urgency;

    public Post() {
    }

    public Post(int postNumber, Author authorId, int pubDate, String title, String messageContent, Crisis crisisCode, String urgency) {
        this.postNumber = postNumber;
        this.authorId = authorId;
        this.pubDate = pubDate;
        this.title = title;
        this.messageContent = messageContent;
        this.crisisCode = crisisCode;
        this.urgency = urgency;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public void setAuthorId(Author authorId) {
        this.authorId = authorId;
    }

    public void setPubDate(int pubDate) {
        this.pubDate = pubDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public void setCrisisCode(Crisis crisisCode) {
        this.crisisCode = crisisCode;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public Author getAuthorId() {
        return authorId;
    }

    public int getPubDate() {
        return pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public Crisis getCrisisCode() {
        return crisisCode;
    }

    public String getUrgency() {
        return urgency;
    }
}
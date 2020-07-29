package com.example.infs3605group3application.Model;



import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Post {
    private int postNumber;
    private String authorId;
    private String pubDate;
    private String title;
    private String messageContent;
    private String crisisCode;
    private String urgency;
    private String imageUrl;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public void setCrisisCode(String crisisCode) {
        this.crisisCode = crisisCode;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getCrisisCode() {
        return crisisCode;
    }

    public String getUrgency() {
        return urgency;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("postNumber", postNumber);
        result.put("authorId", authorId);
        result.put("title", title);
        result.put("pubDate", pubDate);
        result.put("messageContent", messageContent);
        result.put("crisisCode", crisisCode);
        result.put("urgency", urgency);
        return result;
    }
}
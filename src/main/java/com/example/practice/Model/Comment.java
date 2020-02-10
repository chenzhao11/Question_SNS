package com.example.practice.Model;

import java.util.Date;

public class Comment {
    /**
     *   `id` INT NOT NULL AUTO_INCREMENT,
     *   `content` LONGTEXT NULL,
     *   `user_id` INT NOT NULL,
     *   `created_date` DATETIME NOT NULL,
     *   `entity_id` INT NOT NULL,
     *   `entity_type` INT NOT NULL,
     */
    private int id;
    private String content;
    private  int userId;
    private Date createdDate;
    private int entityId;
    private int entityType;
    public Comment(){}
    public Comment(int id, String content, int userId, Date createdDate, int entityId, int entityType) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.createdDate = createdDate;
        this.entityId = entityId;
        this.entityType = entityType;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", createdDate=" + createdDate +
                ", entityId=" + entityId +
                ", entityType=" + entityType +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }
}

package com.amazonaws.mobile.api.idzt9jftjm4c.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBDocument;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.Date;

@DynamoDBDocument
public class TargetModel implements Serializable {

    public TargetModel() {
        super();
    }

    private String id;

    private String arn;

    private String name;

    private String resourceGroupArn;

    private Date createdAt;
//
//    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceGroupArn() {
        return resourceGroupArn;
    }

    public void setResourceGroupArn(String resourceGroupArn) {
        this.resourceGroupArn = resourceGroupArn;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
//    @JsonDeserialize
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
}

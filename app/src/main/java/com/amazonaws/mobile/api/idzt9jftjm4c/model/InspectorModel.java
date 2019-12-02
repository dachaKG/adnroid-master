package com.amazonaws.mobile.api.idzt9jftjm4c.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName = "InspectorId")
public class InspectorModel implements Serializable {


    public InspectorModel() {
        super();
    }

    private String userId;

    private String username;

    private String region;

    private List<TargetModel> targets;

    private List<TemplateModel> templates;

    private List<RunModel> runs;

    private List<FindingModel> findings;

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = id;
    }

    @DynamoDBAttribute(attributeName = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    @DynamoDBAttribute(attributeName = "region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    //    @DynamoDBTypeConverted(converter = TargetModelConverter.class)
    @DynamoDBAttribute(attributeName = "targets")
    public List<TargetModel> getTargets() {
        if(targets == null){
            targets = new ArrayList<>();
        }
        return targets;
    }

    public void setTargets(List<TargetModel> targets) {
        this.targets = targets;
    }

    @DynamoDBAttribute(attributeName = "templates")
    public List<TemplateModel> getTemplates() {
        return templates;
    }

    public void setTemplates(List<TemplateModel> templates) {
        this.templates = templates;
    }

    @DynamoDBAttribute(attributeName = "runs")
    public List<RunModel> getRuns() {
        return runs;
    }

    public void setRuns(List<RunModel> runs) {
        this.runs = runs;
    }

    @DynamoDBAttribute(attributeName = "findings")
    public List<FindingModel> getFindings() {
        return findings;
    }

    @DynamoDBAttribute(attributeName = "findings")
    public void setFindings(List<FindingModel> findings) {
        this.findings = findings;
    }
}

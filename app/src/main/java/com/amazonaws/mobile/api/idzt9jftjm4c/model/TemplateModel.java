package com.amazonaws.mobile.api.idzt9jftjm4c.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBDocument;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@DynamoDBDocument
public class TemplateModel implements Serializable {

    public TemplateModel() {
        super();
    }

    private String templateId;

    private String arn;

    private String name;

    private String assessmentTargetName;

    private String lastAssessmentRunArn;

    private Integer assessmentRunCount;

    private Date createdAt;

    private Integer durationInSeconds;

    private String targetName;

    private List<String> rulesPackagesArns;

//    private List<Attribute> userAttributesForFindings;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getAssessmentTargetName() {
        return assessmentTargetName;
    }

    public void setAssessmentTargetName(String assessmentTargetName) {
        this.assessmentTargetName = assessmentTargetName;
    }

    public String getLastAssessmentRunArn() {
        return lastAssessmentRunArn;
    }

    public void setLastAssessmentRunArn(String lastAssessmentRunArn) {
        this.lastAssessmentRunArn = lastAssessmentRunArn;
    }

    public Integer getAssessmentRunCount() {
        return assessmentRunCount;
    }

    public void setAssessmentRunCount(Integer assessmentRunCount) {
        this.assessmentRunCount = assessmentRunCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(Integer durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public List<String> getRulesPackagesArns() {
        return rulesPackagesArns;
    }

    public void setRulesPackagesArns(List<String> rulesPackagesArns) {
        this.rulesPackagesArns = rulesPackagesArns;
    }

//    public List<Attribute> getUserAttributesForFindings() {
//        return userAttributesForFindings;
//    }
//
//    public void setUserAttributesForFindings(List<Attribute> userAttributesForFindings) {
//        this.userAttributesForFindings = userAttributesForFindings;
//    }
//
}

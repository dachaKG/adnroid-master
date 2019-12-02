package com.amazonaws.mobile.api.idzt9jftjm4c.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBDocument;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@DynamoDBDocument
public class RunModel implements Serializable {



    public RunModel() {
        super();
    }

    private String arn;

    private String assessmentTemplateRun;

    private String name;

    private String state;

    private Date completedAt;

    private Boolean dataCollected;

    private Integer durationInSeconds;

    private Map<String, Integer> findingCounts;

    private List<String> rulesPackagesArns;

    private Date startedAt;

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getAssessmentTemplateRun() {
        return assessmentTemplateRun;
    }

    public void setAssessmentTemplateRun(String assessmentTemplateRun) {
        this.assessmentTemplateRun = assessmentTemplateRun;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

//    @JsonDeserialize
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }
////    @JsonDeserialize
////    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }

    public Boolean getDataCollected() {
        return dataCollected;
    }

    public void setDataCollected(Boolean dataCollected) {
        this.dataCollected = dataCollected;
    }

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(Integer durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public Map<String, Integer> getFindingCounts() {
        return findingCounts;
    }

    public void setFindingCounts(Map<String, Integer> findingCounts) {
        this.findingCounts = findingCounts;
    }

//    public List<AssessmentRunNotification> getNotifications() {
//        return notifications;
//    }
//
//    public void setNotifications(List<AssessmentRunNotification> notifications) {
//        this.notifications = notifications;
//    }

    public List<String> getRulesPackagesArns() {
        return rulesPackagesArns;
    }

    public void setRulesPackagesArns(List<String> rulesPackagesArns) {
        this.rulesPackagesArns = rulesPackagesArns;
    }
//    @JsonDeserialize
    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }
//    @JsonDeserialize
//    public Date getStateChangedAt() {
//        return stateChangedAt;
//    }
//
//    public void setStateChangedAt(Date stateChangedAt) {
//        this.stateChangedAt = stateChangedAt;
//    }

//    @DynamoDBTypeConvertedJson
//    public List<AssessmentRunStateChange> getStateChanges() {
//        return stateChanges;
//    }
//
//    @DynamoDBTypeConvertedJson
//    public void setStateChanges(List<AssessmentRunStateChange> stateChanges) {
//        this.stateChanges = stateChanges;
//    }
//
//    public List<Attribute> getUserAttributeForFindings() {
//        return userAttributeForFindings;
//    }
//
//    public void setUserAttributeForFindings(List<Attribute> userAttributeForFindings) {
//        this.userAttributeForFindings = userAttributeForFindings;
//    }
}

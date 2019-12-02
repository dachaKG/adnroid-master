package com.amazonaws.mobile.api.idzt9jftjm4c.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBDocument;

import java.io.Serializable;
import java.util.Date;

@DynamoDBDocument
public class FindingModel implements Serializable {

    public FindingModel() {
        super();
    }

    private String arn;

//    private AssetAttributes assetAttributes;

    private String assetType;

//    private List<Attribute> attributes;

    private Integer confidence;

    private Date createdAt;

    private String description;

    private String id;

    private Boolean indicatorOfCompromise;

    private Double numericSeverity;

    private String recommendation;

    private Integer schemaVersion;

    private String service;

//    private InspectorServiceAttributes serviceAttributes;

    private String severity;

    private String title;

    private Date updatedAt;

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }
//    @DynamoDBTyped
//    public AssetAttributes getAssetAttributes() {
//        return assetAttributes;
//    }
//    @DynamoDBTyped
//    public void setAssetAttributes(AssetAttributes assetAttributes) {
//        this.assetAttributes = assetAttributes;
//    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

//    public List<Attribute> getAttributes() {
//        return attributes;
//    }
//
//    public void setAttributes(List<Attribute> attributes) {
//        this.attributes = attributes;
//    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIndicatorOfCompromise() {
        return indicatorOfCompromise;
    }

    public void setIndicatorOfCompromise(Boolean indicatorOfCompromise) {
        this.indicatorOfCompromise = indicatorOfCompromise;
    }

    public Double getNumericSeverity() {
        return numericSeverity;
    }

    public void setNumericSeverity(Double numericSeverity) {
        this.numericSeverity = numericSeverity;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public Integer getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(Integer schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

//    public InspectorServiceAttributes getServiceAttributes() {
//        return serviceAttributes;
//    }
//
//    public void setServiceAttributes(InspectorServiceAttributes serviceAttributes) {
//        this.serviceAttributes = serviceAttributes;
//    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}

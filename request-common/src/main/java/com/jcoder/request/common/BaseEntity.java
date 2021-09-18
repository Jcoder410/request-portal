package com.jcoder.request.common;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

/**
 * @author Qiu
 */
public class BaseEntity {

    public static final String FIELD_CREATION_DATE = "creationDate";
    public static final String FIELD_CREATED_BY = "createdBy";
    public static final String FIELD_LAST_UPDATE_DATE = "lastUpdateDate";
    public static final String FIELD_LAST_UPDATED_BY = "lastUpdatedBy";
    public static final String FIELD_OBJECT_VERSION_NUMBER = "objectVersionNumber";
    public static final String FIELD_TABLE_ID = "tableId";

    @TableField
    private Date creationDate;

    @TableField
    private Long createdBy;

    @TableField
    private Date lastUpdateDate;

    @TableField
    private Long lastUpdatedBy;

    @TableField
    private Long objectVersionNumber;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }
}

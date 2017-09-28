package com.xhj.bms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2017/8/7.
 */
@Table("User_Level")
@ApiModel(value = "UserLevel",description = "用户等级")
public class UserLevel implements Serializable{
    private static final long serialVersionUID = 1729693798041026075L;

    @Id
    @ApiModelProperty(value = "id主键")
    @Column("ID")
    private Integer id;

    @ApiModelProperty(value = "等级")
    @Column("levelNum")
    private Integer levelNum;

    @ApiModelProperty(value = "经验值")
    @Column("experienceNum")
    private Integer experienceNum;

    @ApiModelProperty(value = "创建时间")
    @Column("createTime")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    @Column("updateTime")
    private Timestamp updateTime;
    @ApiModelProperty(value = "页码")
    private Integer pageIndex;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize;

    public UserLevel() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }

    public Integer getExperienceNum() {
        return experienceNum;
    }

    public void setExperienceNum(Integer experienceNum) {
        this.experienceNum = experienceNum;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

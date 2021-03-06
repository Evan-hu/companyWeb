package com.company.shop.entity;

import java.util.Date;

public class ArticleSort {
    private Integer articleSortId;

    private String code;

    private Integer pId;

    private String sortName;

    private Integer rankNum;

    private Integer clickCnt;

    private Integer opId;

    private Date createTime;

    private Short state;

    private String note;

    private String idPath;

    private String statePath;

    private Integer nlevel;

    private String showType;

    public Integer getArticleSortId() {
        return articleSortId;
    }

    public void setArticleSortId(Integer articleSortId) {
        this.articleSortId = articleSortId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public Integer getRankNum() {
        return rankNum;
    }

    public void setRankNum(Integer rankNum) {
        this.rankNum = rankNum;
    }

    public Integer getClickCnt() {
        return clickCnt;
    }

    public void setClickCnt(Integer clickCnt) {
        this.clickCnt = clickCnt;
    }

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIdPath() {
        return idPath;
    }

    public void setIdPath(String idPath) {
        this.idPath = idPath;
    }

    public String getStatePath() {
        return statePath;
    }

    public void setStatePath(String statePath) {
        this.statePath = statePath;
    }

    public Integer getNlevel() {
        return nlevel;
    }

    public void setNlevel(Integer nlevel) {
        this.nlevel = nlevel;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }
}
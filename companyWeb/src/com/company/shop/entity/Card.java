package com.company.shop.entity;

import java.util.Date;

public class Card {
    private Integer cardId;

    private String cardNum;

    private Integer cardBatchId;

    private Integer getType;

    private Integer getId;

    private Long orderFormId;

    private Integer memberId;

    private Integer state;

    private Date createTime;

    private Date getTime;

    private Date useTime;
    
    private CardBatch cardBatch;

  
    public CardBatch getCardBatch() {
      return cardBatch;
    }
     
    public void setCardBatch(CardBatch cardBatch) {
      this.cardBatch = cardBatch;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Integer getCardBatchId() {
        return cardBatchId;
    }

    public void setCardBatchId(Integer cardBatchId) {
        this.cardBatchId = cardBatchId;
    }

    public Integer getGetType() {
        return getType;
    }

    public void setGetType(Integer getType) {
        this.getType = getType;
    }

    public Integer getGetId() {
        return getId;
    }

    public void setGetId(Integer getId) {
        this.getId = getId;
    }

    public Long getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(Long orderFormId) {
        this.orderFormId = orderFormId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }
}
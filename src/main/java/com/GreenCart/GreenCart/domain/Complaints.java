package com.GreenCart.GreenCart.domain;

import java.sql.Timestamp;

public class Complaints {
    private Long idcomplaints;
    private String orderdate;
    private String claimreason;
    private String detail;
    private String claimstatus;
    private Timestamp claimdate;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getClaimdate() {
        return claimdate;
    }

    public void setClaimdate(Timestamp claimdate) {
        this.claimdate = claimdate;
    }

    public String getClaimstatus() {
        return claimstatus;
    }

    public void setClaimstatus(String claimstatus) {
        this.claimstatus = claimstatus;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getClaimreason() {
        return claimreason;
    }

    public void setClaimreason(String claimreason) {
        this.claimreason = claimreason;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public Long getIdcomplaints() {
        return idcomplaints;
    }

    public void setIdcomplaints(Long idcomplaints) {
        this.idcomplaints = idcomplaints;
    }
}

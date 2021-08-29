/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.customer_care;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

/**
 *
 * @author Mahmoud Shehab
 */
public class SubscriberAccountModel {

    @JsonIgnore
    private String Id;
    private String subscriberNumber;
    private String currentPlan;
    private Float balance;
    private String currrency;
    private String serviceClass;
    private String status;
    private String barringStatus;
    private String language;
    private String activationDate;
    
    @JsonIgnore
    private Date supervisionFeePeriodDate;
    private String supervisionFeePeriod;
    
    @JsonIgnore
    private Date serviceFeePeriodDate;
    private String serviceFeePeriod;
    private String creditClearance;
    private String serviceRemoval;
    private String refillBarredUntil;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getSubscriberNumber() {
        return subscriberNumber;
    }

    public void setSubscriberNumber(String subscriberNumber) {
        this.subscriberNumber = subscriberNumber;
    }

    public String getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(String currentPlan) {
        this.currentPlan = currentPlan;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getCurrrency() {
        return currrency;
    }

    public void setCurrrency(String currrency) {
        this.currrency = currrency;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBarringStatus() {
        return barringStatus;
    }

    public void setBarringStatus(String barringStatus) {
        this.barringStatus = barringStatus;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    public Date getSupervisionFeePeriodDate() {
        return supervisionFeePeriodDate;
    }

    public void setSupervisionFeePeriodDate(Date supervisionFeePeriodDate) {
        this.supervisionFeePeriodDate = supervisionFeePeriodDate;
    }

    public String getSupervisionFeePeriod() {
        return supervisionFeePeriod;
    }

    public void setSupervisionFeePeriod(String supervisionFeePeriod) {
        this.supervisionFeePeriod = supervisionFeePeriod;
    }

    public Date getServiceFeePeriodDate() {
        return serviceFeePeriodDate;
    }

    public void setServiceFeePeriodDate(Date serviceFeePeriodDate) {
        this.serviceFeePeriodDate = serviceFeePeriodDate;
    }

    public String getServiceFeePeriod() {
        return serviceFeePeriod;
    }

    public void setServiceFeePeriod(String serviceFeePeriod) {
        this.serviceFeePeriod = serviceFeePeriod;
    }

    public String getCreditClearance() {
        return creditClearance;
    }

    public void setCreditClearance(String creditClearance) {
        this.creditClearance = creditClearance;
    }

    public String getServiceRemoval() {
        return serviceRemoval;
    }

    public void setServiceRemoval(String serviceRemoval) {
        this.serviceRemoval = serviceRemoval;
    }

    public String getRefillBarredUntil() {
        return refillBarredUntil;
    }

    public void setRefillBarredUntil(String refillBarredUntil) {
        this.refillBarredUntil = refillBarredUntil;
    }

}

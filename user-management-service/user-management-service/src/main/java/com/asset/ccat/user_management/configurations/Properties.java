/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author mahmoud.shehab
 */
@Component
@ConfigurationProperties
public class Properties {

    private String accessTokenKey;
    private long accessTokenValidity;
    private String dateFormat;
    private Integer usersRefreshTask;

    public String getAccessTokenKey() {
        return accessTokenKey;
    }

    public void setAccessTokenKey(String accessTokenKey) {
        this.accessTokenKey = accessTokenKey;
    }

    public long getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(long accessTokenValidityHours) {
        this.accessTokenValidity = accessTokenValidityHours;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Integer getUsersRefreshTask() {
        return usersRefreshTask;
    }

    public void setUsersRefreshTask(Integer usersRefreshTask) {
        this.usersRefreshTask = usersRefreshTask;
    }

}

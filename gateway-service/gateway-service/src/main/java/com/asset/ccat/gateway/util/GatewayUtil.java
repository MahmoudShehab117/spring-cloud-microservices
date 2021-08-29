/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.util;

import com.asset.ccat.gateway.configurations.Properties;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class GatewayUtil {

    @Autowired
    Properties properties;

    public String dateToString(Date date) {
        SimpleDateFormat DateFor = new SimpleDateFormat(properties.getDateFormat());
        String stringDate = DateFor.format(date);
        return stringDate;
    }
}

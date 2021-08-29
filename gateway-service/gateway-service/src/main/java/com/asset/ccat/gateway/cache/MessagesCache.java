/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.cache;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
@ConfigurationProperties
public class MessagesCache {

    private final Map<String, Map<Integer, String>> MESSAGES_MAP = new HashMap<>();

    public Map<String, Map<Integer, String>> getExceptionMessages() {
        return MESSAGES_MAP;
    }

    public String getsuccessMsg(Integer code) {

        if (MESSAGES_MAP != null && MESSAGES_MAP.get("success") != null
                && MESSAGES_MAP.get("success").get(code) != null) {
            return MESSAGES_MAP.get("success").get(code);
        }
        return "";
    }

    public String getErrorMsg(Integer code) {

        if (MESSAGES_MAP != null && MESSAGES_MAP.get("error") != null
                && MESSAGES_MAP.get("error").get(code * -1) != null) {
            return MESSAGES_MAP.get("error").get(code * -1);
        }
        return "";
    }

    public String getWarningMsg(Integer code) {

        if (MESSAGES_MAP != null && MESSAGES_MAP.get("warn") != null
                && MESSAGES_MAP.get("warn").get(code) != null) {
            return MESSAGES_MAP.get("warn").get(code);
        }
        return "";
    }
}

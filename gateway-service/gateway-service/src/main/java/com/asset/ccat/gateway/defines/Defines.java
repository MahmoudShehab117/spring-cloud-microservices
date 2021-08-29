/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.defines;

/**
 *
 * @author Mahmoud Shehab
 */
public class Defines {

    public static class ContextPaths {

        public static final String BASE_CONTEXT_PATH = "/ccat";
        public static final String USER_MANAGEMENT_CONTEXT_PATH = "/user-management-service";
        public static final String CONFIGURATIONS = "/configurations";
        public static final String SUBSCRIBER_ACCOUNT = "/subscriber-account";
        public static final String SUBSCRIBER_MAIN_PRODUCT = "/main-product";
    }
    
    public static class WEB_ACTIONS {

        public static final String LOGIN = "/login";
        public static final String GET = "/get";
        public static final String GET_ALL = "/getAll";
        public static final String ADD = "/add";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String UPLOAD = "/upload";
        public static final String DOWNLOAD = "/download";
    }

    public static class SecurityKeywords {

        public static final String USERNAME = "username";
        public static final String PREFIX = "prefix";
    }

    public static class SEVERITY {

        public static final Integer CLEAR = 0;
        public static final Integer VALIDATION = 1;
        public static final Integer ERROR = 2;
        public static final Integer FATAL = 3;
    }
}

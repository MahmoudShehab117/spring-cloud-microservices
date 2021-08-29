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
public class ErrorCodes {

    public static class SUCCESS {

        public final static int SUCCESS = 0;
    }

    public static class ERROR {

        public final static int INVALID_USERNAME_OR_PASSWORD = -100;
        public final static int EXPIRED_TOKEN = -101;
        public final static int NOT_AUTHORIZED = -102;
        public final static int INVALID_TOKEN = -103;
        public final static int DATABASE_ERROR = -104;
        public final static int UNKNOWN_ERROR = -105;
    }

}

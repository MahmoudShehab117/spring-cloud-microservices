/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.defines;

/**
 *
 * @author Mahmoud Shehab
 */
public class DatabaseStructs {

    public static final class ADM_USERS {

        public static final String TABLE_NAME = "ADM_USERS";
        public static final String USER_ID = "USER_ID";
        public static final String NT_ACCOUNT = "NT_ACCOUNT";
        public static final String PROFILE_ID = "PROFILE_ID";
        public static final String IS_DELETED = "IS_DELETED";
        public static final String IS_ACTIVE = "IS_ACTIVE";
        public static final String SOURCE = "SOURCE";
        public static final String SESSION_COUNTER = "SESSION_COUNTER";
        public static final String THEME_ID = "THEME_ID";
        public static final String BILLING_ACCOUNT = "BILLING_ACCOUNT";
        public static final String CREATION_DATE = "CREATION_DATE";
        public static final String MODIFICATION_DATE = "MODIFICATION_DATE";
        public static final String LAST_LOGIN = "LAST_LOGIN";

    }

    public static final class ADM_PROFILES {

        public static final String TABLE_NAME = "ADM_PROFILES";
        public static final String PROFILE_ID = "PROFILE_ID";
        public static final String PROFILE_NAME = "PROFILE_NAME";
        public static final String IS_DELETED = "IS_DELETED";
        public static final String IS_FOOTPRINT_REQUIRED = "IS_FOOTPRINT_REQUIRED";
        public static final String SESSION_LIMIT = "SESSION_LIMIT";
        public static final String ADJUSTMENTS_LIMITED = "ADJUSTMENTS_LIMITED";
    }

    public static final class ADM_PROFILE_FEATURES {

        public static final String TABLE_NAME = "ADM_PROFILE_FEATURES";
        public static final String PROFILE_ID = "PROFILE_ID";
        public static final String FEATURE_ID = "FEATURE_ID";

    }

    public static final class LK_FEATURES {

        public static final String TABLE_NAME = "LK_FEATURES";
        public static final String ID = "ID";
        public static final String NAME = "NAME";
        public static final String PAGE_NAME = "PAGE_NAME";
        public static final String TYPE = "TYPE";

    }
}

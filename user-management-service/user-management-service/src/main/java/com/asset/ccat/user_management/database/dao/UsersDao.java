/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.database.dao;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.user_management.database.extractors.UsersExtractor;
import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.models.users.UserModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class UsersDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    UsersExtractor usersExtractor;

    private String retrieveAllUsersQuery;

    public List<UserModel> retrieveUsers() throws UserManagementException {
        try {
            if (retrieveAllUsersQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT *")
                        .append(" FROM ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                        .append(" INNER JOIN ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_USERS.PROFILE_ID)
                        .append(" INNER JOIN ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.PROFILE_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID)
                        .append(" INNER JOIN ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.LK_FEATURES.ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.FEATURE_ID)
                        .append(" where ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_USERS.IS_DELETED).append("= 0")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_USERS.IS_ACTIVE).append("= 1");
                retrieveAllUsersQuery = query.toString();
            }
            return jdbcTemplate.query(retrieveAllUsersQuery, usersExtractor);
        } catch (Exception ex) {
            CCATLogger.debugError("error while excecute " + retrieveAllUsersQuery);
            CCATLogger.error("error while excecute " + retrieveAllUsersQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

}

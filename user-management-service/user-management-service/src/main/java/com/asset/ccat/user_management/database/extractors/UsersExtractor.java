/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.database.extractors;

import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.models.shared.LkFeatureModel;
import com.asset.ccat.user_management.models.users.ProfileModel;
import com.asset.ccat.user_management.models.users.UserModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class UsersExtractor implements ResultSetExtractor<ArrayList<UserModel>> {

    @Override
    public ArrayList<UserModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        ArrayList result = new ArrayList();
        HashMap<Integer, UserModel> userMap = new HashMap();
        while (resultSet.next()) {
            Integer userId = resultSet.getInt(DatabaseStructs.ADM_USERS.USER_ID);
            if (userMap.get(userId) == null) {
                UserModel userModel = new UserModel();
                userModel.setUserId(userId);
                userModel.setBillingAccount(resultSet.getString(DatabaseStructs.ADM_USERS.BILLING_ACCOUNT));
                userModel.setIsActive(resultSet.getBoolean(DatabaseStructs.ADM_USERS.IS_ACTIVE));
                userModel.setNtAccount(resultSet.getString(DatabaseStructs.ADM_USERS.NT_ACCOUNT));
                userModel.setProfileId(resultSet.getInt(DatabaseStructs.ADM_USERS.PROFILE_ID));
                userModel.setThemeId(resultSet.getString(DatabaseStructs.ADM_USERS.THEME_ID));

                ProfileModel profileModel = new ProfileModel();
                profileModel.setProfileId(resultSet.getInt(DatabaseStructs.ADM_PROFILES.PROFILE_ID));
                profileModel.setIsAdjustmentsLimited(resultSet.getInt(DatabaseStructs.ADM_PROFILES.ADJUSTMENTS_LIMITED));
                profileModel.setProfileName(resultSet.getString(DatabaseStructs.ADM_PROFILES.PROFILE_NAME));
                profileModel.setSessionLimit(resultSet.getInt(DatabaseStructs.ADM_PROFILES.SESSION_LIMIT));

                LkFeatureModel lkFeatureModel = new LkFeatureModel();
                lkFeatureModel.setId(resultSet.getInt(DatabaseStructs.LK_FEATURES.ID));
                lkFeatureModel.setName(resultSet.getString(DatabaseStructs.LK_FEATURES.NAME));
                lkFeatureModel.setType(resultSet.getInt(DatabaseStructs.LK_FEATURES.TYPE));
                lkFeatureModel.setPageName(resultSet.getString(DatabaseStructs.LK_FEATURES.PAGE_NAME));

                ArrayList<LkFeatureModel> featureList = new ArrayList<>();
                featureList.add(lkFeatureModel);

                profileModel.setFeatures(featureList);
                userModel.setProfileModel(profileModel);
                userMap.put(userId, userModel);

            } else {
                UserModel userModel = userMap.get(userId);

                LkFeatureModel lkFeatureModel = new LkFeatureModel();
                lkFeatureModel.setId(resultSet.getInt(DatabaseStructs.LK_FEATURES.ID));
                lkFeatureModel.setName(resultSet.getString(DatabaseStructs.LK_FEATURES.NAME));
                lkFeatureModel.setType(resultSet.getInt(DatabaseStructs.LK_FEATURES.TYPE));
                lkFeatureModel.setPageName(resultSet.getString(DatabaseStructs.LK_FEATURES.PAGE_NAME));
                userModel.getProfileModel().getFeatures().add(lkFeatureModel);

            }

        }
        if (userMap.values() != null) {
            result.addAll(userMap.values());
        }
        return result;
    }

}

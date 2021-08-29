/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.services;

import com.asset.ccat.user_management.database.dao.UsersDao;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.manager.UsersManager;
import com.asset.ccat.user_management.models.users.UserModel;
import com.asset.ccat.user_management.security.JwtTokenUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class UserSerivce {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UsersDao usersDao;
    
    @Autowired
    UsersManager usersManager;

    public String login(UserModel userModel) throws Exception {
        final String token = jwtTokenUtil.generateToken(userModel);
        return token;
    }

    public List<UserModel> retrieveUsers() throws UserManagementException {
        return usersDao.retrieveUsers();
    }
    
    public UserModel retrieveUser(String name){
        return usersManager.getCachedUsers().get(name);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.controllers;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.models.requests.LoginRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.users.UserModel;
import com.asset.ccat.gateway.proxy.UserManagementServiceProxy;
import com.asset.ccat.gateway.services.UserSerivce;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mahmoud Shehab
 */
@RestController
public class LoginController {

    @Autowired
    UserSerivce userService;

    @Autowired
    UserManagementServiceProxy userManagementServiceProxy;

    @RequestMapping(value = Defines.WEB_ACTIONS.LOGIN, method = RequestMethod.POST)
    public BaseResponse<UserModel> userLogin(HttpServletRequest req, @RequestBody LoginRequest loginRequest) throws AuthenticationException, Exception {
        BaseResponse<UserModel> resultFromService = userManagementServiceProxy.userLogin(loginRequest);
        return resultFromService;
    }
}

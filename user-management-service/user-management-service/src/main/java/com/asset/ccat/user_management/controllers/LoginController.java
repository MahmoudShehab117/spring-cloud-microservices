/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.controllers;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.models.requests.LoginRequest;
import com.asset.ccat.user_management.models.responses.BaseResponse;
import com.asset.ccat.user_management.models.shared.ServiceInfo;
import com.asset.ccat.user_management.models.users.UserModel;
import com.asset.ccat.user_management.services.UserSerivce;
import java.net.InetAddress;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    Environment environment;

    @RequestMapping(value = Defines.ContextPaths.LOGIN, method = RequestMethod.POST)
    public BaseResponse<UserModel> userLogin(HttpServletRequest req, @RequestBody LoginRequest loginRequest) throws AuthenticationException, Exception {
        UserModel user = userService.retrieveUser(loginRequest.getUsername());
        if (user == null) {
            throw new UserManagementException(ErrorCodes.ERROR.INVALID_USERNAME_OR_PASSWORD);
        }
        String authToken = userService.login(user);
        user.setToken(authToken);
        CCATLogger.info("IP => " + InetAddress.getLocalHost().getHostName() + environment.getProperty("server.port"));
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,new ServiceInfo(InetAddress.getLocalHost().getHostName(), environment.getProperty("server.port")),
                user);
    }
}

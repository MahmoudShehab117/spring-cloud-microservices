/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.security;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.user_management.cache.MessagesCache;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.models.responses.BaseResponse;
import com.asset.ccat.user_management.models.security.HttpRequestWrapper;
import com.asset.ccat.user_management.models.users.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author mahmoud.shehab
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    MessagesCache messagesCache;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        try {
            CCATLogger.info("Internal Request Filtering Started");
            CCATLogger.debug("Internal Request Filtering Started " + req.getServletPath());

            boolean notPermit = !req.getServletPath().contains(Defines.ContextPaths.LOGIN);

            if (notPermit) {
                HttpRequestWrapper hrw = doAuthenticationFilter(req, res, chain);
                chain.doFilter(hrw, res);
                return;
            }
            chain.doFilter(req, res);

        } catch (UserManagementException ex) {
            CCATLogger.debugError(" Exception thrown with error code" + ex.getErrorCode());
            CCATLogger.info(" Exception thrown with error code" + ex.getErrorCode());
            CCATLogger.error(" Exception thrown with error code" + ex.getErrorCode(), ex);
            handleRejectionResponse(res, ex.getErrorCode());
        } catch (Exception ex) {
            CCATLogger.info("Unknown Error occured");
            CCATLogger.error("Unkown Error occured", ex);
            handleRejectionResponse(res, ErrorCodes.ERROR.NOT_AUTHORIZED);
        } finally {
            long end = System.currentTimeMillis() - start;
            CCATLogger.debug("Internal Request Filtering Finished in [" + end + "] ms");
        }
    }

    protected HttpRequestWrapper doAuthenticationFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException, UserManagementException {
        CCATLogger.debug("Start authenticate user");
        String username = null;
        HttpServletRequest requestToCache = (HttpServletRequest) req;
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(requestToCache);
        String authToken = jwtTokenUtil.getTokenFromBody(requestWrapper);
        if (authToken != null) {
            try {
                CCATLogger.debug("extracting data from token");
                HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(authToken);
                username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
                CCATLogger.debug("user name: " + username);
            } catch (UserManagementException e) {
                CCATLogger.debug("token not valid");
                throw e;
            }
        } else {
            CCATLogger.debug("no token found");
            throw new UserManagementException(ErrorCodes.ERROR.NOT_AUTHORIZED);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CCATLogger.debug("getting user details");
            UserModel userModel = new UserModel();
            userModel.setNtAccount("admin");
            CCATLogger.debug("starting validateToken ");
            if (jwtTokenUtil.validateToken(authToken, username, userModel)) {
                CCATLogger.debug("token is valid");
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userModel, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(requestWrapper));
                CCATLogger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                CCATLogger.debug("token not valid ");
                throw new UserManagementException(ErrorCodes.ERROR.NOT_AUTHORIZED);
            }
        }
        return requestWrapper;
    }

    private void handleRejectionResponse(HttpServletResponse response, Integer errorCode) {
        CCATLogger.info("Handle request rejection");
        try {
            ObjectMapper mapper = new ObjectMapper();
            BaseResponse<String> baseResponse = new BaseResponse();
            baseResponse.setStatusCode(errorCode);
            baseResponse.setStatusMessage(messagesCache.getErrorMsg(errorCode));
            baseResponse.setSeverity(Defines.SEVERITY.ERROR);

            String jsonInString = mapper.writeValueAsString(baseResponse);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(jsonInString);
            out.flush();

            CCATLogger.info("Rejected request handled successfully");
        } catch (IOException ex) {
            CCATLogger.info("Unknown Error occured in handling rejection response");
            CCATLogger.error("Unknown Error occured in handling rejection response", ex);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.security;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.user_management.configurations.Properties;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.models.requests.BaseRequest;
import com.asset.ccat.user_management.models.users.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


/**
 *
 * @author mahmoud.shehab
 */
@Component
public class JwtTokenUtil implements Serializable {

    @Autowired
    private Properties properties;

    public HashMap<String, Object> extractDataFromToken(String token) throws UserManagementException {
        try {
            HashMap<String, Object> tokenData = new HashMap<>();
            CCATLogger.debug("extracting data from token");
            final Claims claims = getAllClaimsFromToken(token);
            String username = claims.getSubject();
            String prefix = String.valueOf(claims.get(Defines.SecurityKeywords.PREFIX));
            tokenData.put(Defines.SecurityKeywords.USERNAME, username);
            tokenData.put(Defines.SecurityKeywords.PREFIX, prefix);
            return tokenData;
        } catch (IllegalArgumentException e) {
            CCATLogger.info("An error occured during getting username from token");
            CCATLogger.debugError("An error occured during getting username from token : " + e.getMessage());
            CCATLogger.error("An error occured during getting username from token : " + e.getMessage(), e);
            throw new UserManagementException(ErrorCodes.ERROR.INVALID_USERNAME_OR_PASSWORD);
        } catch (ExpiredJwtException e) {
            CCATLogger.info("an error occured ");
            CCATLogger.error("the token is expired and not valid anymore: " + e.getMessage(), e);
            CCATLogger.debugError("the token is expired and not valid anymore: " + e.getMessage());
            throw new UserManagementException(ErrorCodes.ERROR.EXPIRED_TOKEN);
        }  catch (SignatureException e) {
            CCATLogger.info("an error occured ");
            CCATLogger.error("the token is expired and not valid anymore: " + e.getMessage(), e);
            CCATLogger.debugError("the token is expired and not valid anymore: " + e.getMessage());
            throw new UserManagementException(ErrorCodes.ERROR.INVALID_TOKEN);
        } 
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        //Jwts.
        return Jwts.parser()
                .setSigningKey(properties.getAccessTokenKey().trim())
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserModel user) throws AuthenticationException, Exception {
        return doGenerateToken(user);
    }

    private String doGenerateToken(UserModel user) throws AuthenticationException, Exception {
        String token = "";
        try {
            CCATLogger.debug("start generating token for user : " + user.getNtAccount());
            Claims claims = Jwts.claims().setSubject(user.getNtAccount());
            claims.put(Defines.SecurityKeywords.PREFIX, Defines.SecurityKeywords.PREFIX);
            long accessTokenValidityMilli = properties.getAccessTokenValidity() * 60 * 1000;
            CCATLogger.debug("accessTokenValidityHour : " + properties.getAccessTokenValidity() + "  =  accessTokenValidityMilli : " + accessTokenValidityMilli);
            token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidityMilli))
                    .signWith(SignatureAlgorithm.HS256, properties.getAccessTokenKey().trim())
                    .compact();
        } catch (AuthenticationException ex) {
            CCATLogger.info("an error occured ");
            CCATLogger.debugError("an error occured : " + ex.getMessage());
            CCATLogger.error("an error occured  " + ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            CCATLogger.info("an error occured ");
            CCATLogger.debug("an error occured : " + ex.getMessage());
            CCATLogger.error("an error occured  " + ex.getMessage(), ex);
            throw ex;
        }
        CCATLogger.debug("token generated");
        return token;
    }

    public Boolean validateToken(String token, String username, UserModel userModel) {
        try {
            CCATLogger.debug("start validate Token for user : " + username);
            if (isTokenExpired(token)) {
                CCATLogger.debug("token expired");
                return false;
            }
            if (!username.equals(userModel.getNtAccount())) {
                CCATLogger.debug("wrong user name");
                return false;
            }
        } catch (Exception ex) {
            CCATLogger.debug("an error occured : " + ex.getMessage());
            CCATLogger.info("an error occured ");
            CCATLogger.error("Failed to validate Token " + ex.getMessage(), ex);
            return false;
        }
        return true;
    }

    public String getTokenFromBody(HttpServletRequest request) {
        String token = "";
        try {
            CCATLogger.debug("start get token from request body");
            InputStream inputStream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            final ObjectNode node = new ObjectMapper().readValue(result.toString(), ObjectNode.class);
            if (node.get("token") != null) {
                token = node.get("token").textValue();
            }

        } catch (IOException ex) {
            CCATLogger.debug("failed to generate baseRequest model, token not found ");
        }

        return token;
    }

    public String getTokenFromRequest(BaseRequest baseRequest) {
        return baseRequest.getToken().replace(Defines.SecurityKeywords.PREFIX, "");

    }
}

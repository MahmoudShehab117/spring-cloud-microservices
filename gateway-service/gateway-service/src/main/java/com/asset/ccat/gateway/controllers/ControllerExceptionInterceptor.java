/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.controllers;

import com.asset.ccat.gateway.cache.MessagesCache;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ControllerExceptionInterceptor extends ResponseEntityExceptionHandler {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MessagesCache messagesCache;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseResponse> handelAllExceptions(Exception ex, WebRequest req) {
        CCATLogger.debugError(" An error has occured ex : " + ex.getMessage());
        CCATLogger.error(" An error has occured  errorcode message : ", ex);
        BaseResponse<String> response = new BaseResponse();
        response.setStatusCode(ErrorCodes.ERROR.UNKNOWN_ERROR);
        response.setStatusMessage(messagesCache.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR));
        response.setSeverity(Defines.SEVERITY.FATAL);
        CCATLogger.debug("Api Response is " + response);
        ThreadContext.remove("transactionId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(GatewayException.class)
    public final ResponseEntity<BaseResponse> handelGatewayException(GatewayException ex, WebRequest req) {
        CCATLogger.debugError(" An error has occured ex : " + ex.getMessage());
        CCATLogger.error(" An error has occured  errorcode message : ", ex);
        CCATLogger.debug("create Api Response");
        BaseResponse<String> response = new BaseResponse();
        response.setStatusCode(ex.getErrorCode());
        response.setStatusMessage(messagesCache.getErrorMsg(ex.getErrorCode()));
        response.setSeverity(Defines.SEVERITY.ERROR);
        ThreadContext.remove("transactionId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

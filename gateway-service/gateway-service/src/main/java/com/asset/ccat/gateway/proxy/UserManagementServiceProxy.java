/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.models.requests.LoginRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.users.UserModel;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class UserManagementServiceProxy {

    private final WebClient.Builder loadBalancedWebClientBuilder;
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

    public UserManagementServiceProxy(WebClient.Builder webClientBuilder,
            ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.loadBalancedWebClientBuilder = webClientBuilder;
        this.lbFunction = lbFunction;
    }

    public BaseResponse<UserModel> userLogin(LoginRequest loginRequest) {
        BaseResponse<UserModel> result = null;
        Mono<BaseResponse<UserModel>> res = loadBalancedWebClientBuilder.build().post()
                .uri("http://user-management" + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH + Defines.WEB_ACTIONS.LOGIN)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(loginRequest))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseResponse<UserModel>>() {
                });

        result = res.block();
        return result;
    }

}

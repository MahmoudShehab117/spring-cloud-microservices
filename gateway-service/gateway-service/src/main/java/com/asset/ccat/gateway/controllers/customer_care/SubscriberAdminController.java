/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.models.customer_care.ProductQuotaModel;
import com.asset.ccat.gateway.models.customer_care.SubscriberAccountModel;
import com.asset.ccat.gateway.models.customer_care.SubscriberMainProductModel;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.util.GatewayUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping(Defines.ContextPaths.SUBSCRIBER_ACCOUNT)
public class SubscriberAdminController {

    @Autowired
    GatewayUtil gatewayUtil;

    @RequestMapping(value = Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public ResponseEntity<BaseResponse<SubscriberAccountModel>> getSubscriberAccount(HttpServletRequest req,
            @RequestBody SubscriberRequest subscriberRequest) throws AuthenticationException, Exception {
        SubscriberAccountModel subscriberModel = new SubscriberAccountModel();

        subscriberModel.setSubscriberNumber(subscriberRequest.getMsisdn());
        subscriberModel.setBalance(100f);
        subscriberModel.setCurrentPlan("Red Essential");
        subscriberModel.setCurrrency("EGP");
        subscriberModel.setActivationDate(gatewayUtil.dateToString(new Date()));
        subscriberModel.setStatus("Active");
        subscriberModel.setServiceClass("SD_DISCONNECT");
        subscriberModel.setBarringStatus("");
        subscriberModel.setLanguage("Arabic");
        subscriberModel.setSupervisionFeePeriod(gatewayUtil.dateToString(new Date()));
        subscriberModel.setServiceFeePeriod(gatewayUtil.dateToString(new Date()));
        subscriberModel.setCreditClearance(gatewayUtil.dateToString(new Date()));
        subscriberModel.setServiceRemoval(gatewayUtil.dateToString(new Date()));

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                subscriberModel), HttpStatus.OK);
    }

    @RequestMapping(value = Defines.ContextPaths.SUBSCRIBER_MAIN_PRODUCT + Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public ResponseEntity<BaseResponse<SubscriberMainProductModel>> getSubscriberMainProducts(HttpServletRequest req,
            @RequestBody SubscriberRequest subscriberRequest) throws AuthenticationException, Exception {
        SubscriberMainProductModel subscriberModel = new SubscriberMainProductModel();
        subscriberModel.setProductId(1002);
        subscriberModel.setProductName("Control Plus 30");
        subscriberModel.setProductType("Service Class");
        subscriberModel.setProductStatus("Active");
        subscriberModel.setProductRecurrence("OnDemand");
        subscriberModel.setProductStartDate(gatewayUtil.dateToString(new Date()));
        subscriberModel.setProductRenewalDate(gatewayUtil.dateToString(new Date()));
        subscriberModel.setProductExpiryDate(gatewayUtil.dateToString(new Date()));
        
        List<ProductQuotaModel> list = new ArrayList<>();
        ProductQuotaModel productQuotaModel1 = new ProductQuotaModel();
        productQuotaModel1.setQuotaName("2200");
        productQuotaModel1.setQuotaType("Unit");
        productQuotaModel1.setQuotaUnit("Unit");
        productQuotaModel1.setMaxQuota("1200.00");
        productQuotaModel1.setConsumedQuota("100.00");
        productQuotaModel1.setRemainingQuota("1100.00");
        list.add(productQuotaModel1);
        
        ProductQuotaModel productQuotaModel2 = new ProductQuotaModel();
        productQuotaModel2.setQuotaName("1400");
        productQuotaModel2.setQuotaType("SMS");
        productQuotaModel2.setQuotaUnit("SMS");
        productQuotaModel2.setMaxQuota("100");
        productQuotaModel2.setConsumedQuota("20");
        productQuotaModel2.setRemainingQuota("80");
        list.add(productQuotaModel2);
        
        ProductQuotaModel productQuotaModel3 = new ProductQuotaModel();
        productQuotaModel3.setQuotaName("1400");
        productQuotaModel3.setQuotaType("MIN");
        productQuotaModel3.setQuotaUnit("MIN");
        productQuotaModel3.setMaxQuota("100");
        productQuotaModel3.setConsumedQuota("20");
        productQuotaModel3.setRemainingQuota("80");
        list.add(productQuotaModel3);
        
        subscriberModel.setQuotas(list);

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                subscriberModel), HttpStatus.OK);
    }
}

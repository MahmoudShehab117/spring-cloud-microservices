/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.manager;

import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.user_management.configurations.Properties;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.factories.CustomThreadFactory;
import com.asset.ccat.user_management.models.users.UserModel;
import com.asset.ccat.user_management.services.UserSerivce;
import com.asset.ccat.user_management.tasks.RefreshUsersTask;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class UsersManager {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Properties properties;

    @Autowired
    UserSerivce userservice;

    private final ScheduledThreadPoolExecutor poolTaskScheduler;
    private HashMap<String, UserModel> cachedUsers;
    private final ReentrantLock reentrantLock;

    public UsersManager() {
        poolTaskScheduler = new ScheduledThreadPoolExecutor(1, new CustomThreadFactory("RefreshPool", "RefreshUsersTask"));
        cachedUsers = new HashMap<>();
        reentrantLock = new ReentrantLock();
    }

    public void init() {
        Runnable runnable = applicationContext.getBean(RefreshUsersTask.class, this);
        poolTaskScheduler.scheduleWithFixedDelay(runnable, 0, properties.getUsersRefreshTask(), TimeUnit.MILLISECONDS);
    }

    public void refreshUsers() {
        try {
            List<UserModel> list = userservice.retrieveUsers();
            HashMap<String, UserModel> hashMap = new HashMap<>();
            list.forEach(user -> hashMap.put(user.getNtAccount(), user));
            swapCachedUsers(hashMap);
        } catch (UserManagementException ex) {
            CCATLogger.debugError("Error while refreshing users");
            CCATLogger.error("Error while refreshing users", ex);
        }
    }

    private void swapCachedUsers(HashMap map) {
        try {
            reentrantLock.lock();
            cachedUsers = map;
        } catch (Exception ex) {
            CCATLogger.debugError("Error while refreshing users");
            CCATLogger.error("Error while refreshing users", ex);
        } finally {
            reentrantLock.unlock();
        }

    }

    public HashMap<String, UserModel> getCachedUsers() {
        return cachedUsers;
    }

    public void setCachedUsers(HashMap<String, UserModel> cachedUsers) {
        this.cachedUsers = cachedUsers;
    }

}

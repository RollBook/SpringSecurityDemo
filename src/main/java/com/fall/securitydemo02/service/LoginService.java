package com.fall.securitydemo02.service;

import com.fall.securitydemo02.domain.User;

public interface LoginService {
    String login(User user);

    void logout();
}

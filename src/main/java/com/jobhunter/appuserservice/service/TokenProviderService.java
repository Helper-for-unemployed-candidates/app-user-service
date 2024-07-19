package com.jobhunter.appuserservice.service;

public interface TokenProviderService {

    void authorize();

    void refresh();

    String getToken();
}

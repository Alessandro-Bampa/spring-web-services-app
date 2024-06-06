package com.springboot.webservicesapp.registration;

import org.jvnet.hk2.annotations.Service;

@Service
public class RegistrationService {
    public String register(RegistrationRequest request) {
        return "test";
    }

}

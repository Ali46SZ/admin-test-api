package com.santechture.api.controller;


import com.santechture.api.dto.GeneralResponse;
import com.santechture.api.exception.BusinessExceptions;
import com.santechture.api.service.AdminServiceDetails;
import com.santechture.api.validation.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping(path = "admin")
public class AdminController {

    private final AdminServiceDetails adminServiceDetails;

    public AdminController(AdminServiceDetails adminServiceDetails) {
        this.adminServiceDetails = adminServiceDetails;
    }

    @PostMapping
    public ResponseEntity<GeneralResponse> login(@RequestBody LoginRequest request) throws BusinessExceptions {
        return adminServiceDetails.login(request);
    }
}

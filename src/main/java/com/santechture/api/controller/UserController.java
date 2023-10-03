package com.santechture.api.controller;


import com.santechture.api.dto.AdminDetails;
import com.santechture.api.dto.GeneralResponse;
import com.santechture.api.exception.BusinessExceptions;
import com.santechture.api.service.AdminServiceDetails;
import com.santechture.api.service.UserService;
import com.santechture.api.validation.AddUserRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(path = "user")
public class UserController {

    private final UserService userService;

    private final AdminServiceDetails adminServiceDetails;
    public UserController(UserService userService, AdminServiceDetails adminServiceDetails) {
        this.userService = userService;
        this.adminServiceDetails = adminServiceDetails;
    }

    @GetMapping
    public ResponseEntity<GeneralResponse> list(Pageable pageable){
        return userService.list(pageable);
    }
    @PostMapping
    public ResponseEntity<GeneralResponse> addNewUser(@RequestBody AddUserRequest request, Authentication authentication) throws BusinessExceptions {
        ResponseEntity<GeneralResponse> response =  userService.addNewUser(request, authentication);
        Integer adminId = ((AdminDetails) authentication.getPrincipal()).getAdminId();
        adminServiceDetails.revokeAllUserTokens(adminId);
        return response;
    }
}

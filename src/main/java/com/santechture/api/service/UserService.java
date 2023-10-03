package com.santechture.api.service;

import com.santechture.api.dto.AdminDetails;
import com.santechture.api.dto.GeneralResponse;
import com.santechture.api.dto.user.UserDto;
import com.santechture.api.entity.User;
import com.santechture.api.exception.BusinessExceptions;
import com.santechture.api.repository.UserRepository;
import com.santechture.api.validation.AddUserRequest;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AdminServiceDetails adminServiceDetails;
    public UserService(UserRepository userRepository, AdminServiceDetails adminServiceDetails) {
        this.userRepository = userRepository;
        this.adminServiceDetails = adminServiceDetails;
    }

    public ResponseEntity<GeneralResponse> list(Pageable pageable){
        return new GeneralResponse().response(userRepository.findAll(pageable));
    }

    public ResponseEntity<GeneralResponse> addNewUser(AddUserRequest request, Authentication authentication) throws BusinessExceptions {

        if(userRepository.existsByUsernameIgnoreCase(request.getUsername())){
            throw new BusinessExceptions("username.exist");
        } else if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BusinessExceptions("email.exist");
        }

        User user = new User(request.getUsername(),request.getEmail());
        userRepository.save(user);

        Integer adminId = ((AdminDetails) authentication.getPrincipal()).getAdminId();
        adminServiceDetails.revokeAllUserTokens(adminId);
        return new GeneralResponse().response(new UserDto(user));
    }

}

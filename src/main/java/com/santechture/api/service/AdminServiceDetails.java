package com.santechture.api.service;

import com.santechture.api.entity.Token;
import com.santechture.api.jwtutils.JwtTokenUtil;
import com.santechture.api.dto.AdminDetails;
import com.santechture.api.dto.GeneralResponse;
import com.santechture.api.dto.JwtResponse;
import com.santechture.api.dto.admin.AdminDto;
import com.santechture.api.entity.Admin;
import com.santechture.api.exception.BusinessExceptions;
import com.santechture.api.repository.AdminRepository;
import com.santechture.api.repository.TokenRepository;
import com.santechture.api.validation.LoginRequest;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceDetails implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private TokenRepository tokenRepository;

    public ResponseEntity<GeneralResponse> login(LoginRequest request) throws BusinessExceptions {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            AdminDetails adminDetails = loadUserByUsername(request.getUsername());
            String token = jwtTokenUtil.generateJwtToken(adminDetails);

            revokeAllUserTokens(adminDetails.getAdminId());
            saveAdminToken(adminDetails.getAdminId(), token);

            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setToken(token);
            AdminDto adminDto = new AdminDto();
            adminDto.setUsername(adminDetails.getUsername());
            adminDto.setAdminId(adminDetails.getAdminId());
            jwtResponse.setAdminDto(adminDto);
            return new GeneralResponse().response(jwtResponse);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @Override
    public AdminDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> admin = Optional.ofNullable(adminRepository.findByUsernameIgnoreCase(username));
        // Converting userDetail to UserDetails
        return admin.map(AdminDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    private void saveAdminToken(Integer adminId, String jwtToken) {
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        Token token = Token.builder()
                .admin(admin)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(Integer adminId) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(adminId);
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}

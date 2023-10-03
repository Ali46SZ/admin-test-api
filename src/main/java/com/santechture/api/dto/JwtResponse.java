package com.santechture.api.dto;

import com.santechture.api.dto.admin.AdminDto;
import com.santechture.api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String token;
    private AdminDto adminDto;
}

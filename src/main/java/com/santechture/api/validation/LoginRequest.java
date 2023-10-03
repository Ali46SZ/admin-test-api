package com.atheletshub.mobile.api.validation.auth;

import com.atheletshub.mobile.api.validation.GeneralRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest extends GeneralRequest implements Serializable {
    private String email;
    private String password;
}

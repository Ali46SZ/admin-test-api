package com.atheletshub.mobile.api.validation.auth;

import com.atheletshub.mobile.api.validation.GeneralRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest extends GeneralRequest implements Serializable {

    @Email(message = "user.register.invalid.email")
    @NotBlank(message = "user.register.email.required")
    private String email;

    private String password;

    private String dateOfBirth;

    private String fullName;

    private String phoneNumber;

    private String profile;

    private Integer countryId;

    private List<String> lookingFor;


}

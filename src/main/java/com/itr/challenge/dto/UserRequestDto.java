package com.itr.challenge.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Getter
public class UserRequestDto {

    @NotBlank
    @Size(min = 5, max = 254)
    private String name;

    @Email(message = "Please provide a correct email")
    @Size(min = 5, max = 254)
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotNull
    private List<PhoneDto> phones;

    @Override
    public String toString() {
        return "UserRequestDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phones=" + phones +
                '}';
    }
}

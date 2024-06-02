package com.itr.challenge.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;


@Getter
@AllArgsConstructor
@ApiModel(description = "Datos de usarios para registrar o modificar")
public class UserRequestDto {

    @NotBlank
    @Size(min = 4, max = 255)
    @ApiModelProperty(notes = "Nombre Completo")
    private String name;

    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,34}", message = "Please provide a correct email")
    @Size(min = 5, max = 255)
    @ApiModelProperty(notes = "Correo electronico")
    private String email;

    @NotBlank
    @Size(min = 7, max = 255)
    @ApiModelProperty(notes = "Contrasena")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{7,}$",
            message = "Password must contain at least one digit and one special character")
    private String password;

    @NotNull
    @ApiModelProperty(notes = "Numeros de los telefonos del usario")
    private Set<PhoneDto> phones;

    @Override
    public String toString() {
        return "UserRequestDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phones=" + phones +
                '}';
    }
}

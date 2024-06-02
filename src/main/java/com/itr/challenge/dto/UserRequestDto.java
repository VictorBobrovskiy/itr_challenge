package com.itr.challenge.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;


@Getter
@AllArgsConstructor
@ApiModel(description = "Datos de usarios para registrar o modificar")
public class UserRequestDto {

    @NotBlank
    @Size(min = 5, max = 254)
    @ApiModelProperty(notes = "Nombre Completo")
    private String name;

    @Email(message = "Please provide a correct email")
    @Size(min = 5, max = 255)
    @ApiModelProperty(notes = "Correo electronico")
    private String email;

    @NotBlank
    @Size(min = 7, max = 255)
    @ApiModelProperty(notes = "Contrasena")
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

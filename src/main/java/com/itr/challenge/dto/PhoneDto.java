package com.itr.challenge.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(description = "Telefonos")
public class PhoneDto {

    @NotBlank
    @Size(min = 5, max = 15)
    @ApiModelProperty(notes = "Numero del telefono")
    private String number;

    @NotBlank
    @Size(min = 1, max = 5)
    @ApiModelProperty(notes = "El codigo del area")
    private String citycode;

    @NotBlank
    @Size(min = 1, max = 5)
    @ApiModelProperty(notes = "El codigo del pais")
    private String contrycode;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneDto phoneDto = (PhoneDto) o;
        return Objects.equals(getNumber(), phoneDto.getNumber()) && Objects.equals(getCitycode(), phoneDto.getCitycode()) && Objects.equals(getContrycode(), phoneDto.getContrycode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getCitycode(), getContrycode());
    }

    @Override
    public String toString() {
        return "PhoneDto{" +
                "number='" + number + '\'' +
                ", cityCode='" + citycode + '\'' +
                ", countryCode='" + contrycode + '\'' +
                '}';
    }
}


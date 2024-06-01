package com.itr.challenge.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PhoneDto {

    @NotBlank
    @Size(min = 5, max = 15)
    private String number;

    @NotBlank
    @Size(min = 1, max = 5)
    private String citycode;

    @NotBlank
    @Size(min = 1, max = 5)
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


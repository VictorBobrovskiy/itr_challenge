package com.itr.challenge.mapper;


import com.itr.challenge.dto.PhoneDto;
import com.itr.challenge.dto.UserDto;
import com.itr.challenge.dto.UserRequestDto;
import com.itr.challenge.model.Phone;
import com.itr.challenge.model.User;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setCreated(user.getCreated());
        userDto.setModified(user.getModified());
        userDto.setLastLogin(user.getLastLogin());
        userDto.setToken(user.getToken());
        userDto.setActive(user.isActive());
        return userDto;
    }

    public static User toUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setPhones(userRequestDto.getPhones().stream().map(UserMapper::toPhone).collect(Collectors.toSet()));
        return user;
    }

    private static PhoneDto toPhoneDto(Phone phone) {
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setNumber(phone.getNumber());
        phoneDto.setCityCode(phone.getCityCode());
        phoneDto.setCountryCode(phone.getCountryCode());
        return phoneDto;
    }

    private static Phone toPhone(PhoneDto phoneDto) {
        Phone phone = new Phone();
        phone.setNumber(phoneDto.getNumber());
        phone.setCityCode(phoneDto.getCityCode());
        phone.setCountryCode(phoneDto.getCountryCode());
        return phone;
    }

}
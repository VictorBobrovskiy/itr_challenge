package com.itr.challenge.mapper;


import com.itr.challenge.dto.PhoneDto;
import com.itr.challenge.dto.UserDto;
import com.itr.challenge.dto.UserRequestDto;
import com.itr.challenge.model.Phone;
import com.itr.challenge.model.User;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setCreated(FORMATTER.format(user.getCreated()));
        userDto.setModified(FORMATTER.format(user.getModified()));
        userDto.setLastLogin(FORMATTER.format(user.getLastLogin()));
        userDto.setToken(user.getToken());
        userDto.setActive(user.isActive());
        userDto.setPhones(user.getPhones().stream().map(UserMapper::toPhoneDto).collect(Collectors.toList()));
        return userDto;
    }

    public static User toUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPhones(userRequestDto.getPhones().stream().map(UserMapper::toPhone).collect(Collectors.toSet()));
        return user;
    }

    public static PhoneDto toPhoneDto(Phone phone) {
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setNumber(phone.getNumber());
        phoneDto.setCitycode(phone.getCityCode());
        phoneDto.setContrycode(phone.getCountryCode());
        return phoneDto;
    }

    public static Phone toPhone(PhoneDto phoneDto) {
        Phone phone = new Phone();
        phone.setNumber(phoneDto.getNumber());
        phone.setCityCode(phoneDto.getCitycode());
        phone.setCountryCode(phoneDto.getContrycode());
        return phone;
    }

}
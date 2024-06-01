package com.itr.challenge.dto;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class UserDto {

    private long id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;
    private List<PhoneDto> phones;


    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", created=" + created +
                ", modified=" + modified +
                ", lastLogin=" + lastLogin +
                ", isActive=" + isActive +
                ", phones=" + phones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return isActive == userDto.isActive && created.equals(userDto.created) && modified.equals(userDto.modified) && lastLogin.equals(userDto.lastLogin) && token.equals(userDto.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, modified, lastLogin, token, isActive);
    }
}


package com.itr.challenge.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    private long id;
    private String created;
    private String modified;
    private String lastLogin;
    private String token;
    private boolean isActive;
    private List<PhoneDto> phones;

    public UserDto(long id, String created, String modified,
                   String lastLogin, String token, boolean isActive) {
        this.id = id;
        this.created = created;
        this.modified = modified;
        this.lastLogin = lastLogin;
        this.token = token;
        this.isActive = isActive;
    }

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
        return isActive == userDto.isActive &&
                Objects.equals(created, userDto.created) &&
                Objects.equals(modified, userDto.modified) &&
                Objects.equals(lastLogin, userDto.lastLogin) &&
                Objects.equals(token, userDto.token) &&
                Objects.equals(phones, userDto.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, modified, lastLogin, token, isActive);
    }
}


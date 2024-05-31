package com.itr.challenge.dto;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
}

}
package com.seabattlespring.springseabattle.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @JsonProperty("userName")
    @NotNull(message = "This field must not be empty")
    private String userName;
    @JsonProperty("password")
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    private String password;

    @JsonCreator
    public UserDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}

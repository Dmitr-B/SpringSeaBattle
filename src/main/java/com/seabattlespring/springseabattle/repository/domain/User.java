package com.seabattlespring.springseabattle.repository.domain;

import com.seabattlespring.springseabattle.player.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String userName;
    private String password;

    private Set<Role> roles;
}
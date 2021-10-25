package com.seabattlespring.springseabattle.dto;

import lombok.Data;

import java.net.URI;

@Data
public class IdLocationDto {

    private String id;
    private URI location;
}

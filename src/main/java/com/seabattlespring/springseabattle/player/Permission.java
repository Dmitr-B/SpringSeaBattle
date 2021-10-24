package com.seabattlespring.springseabattle.player;

public enum Permission {
    PLAYERS_READ("players:read"),
    PLAYERS_WRITE("players:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

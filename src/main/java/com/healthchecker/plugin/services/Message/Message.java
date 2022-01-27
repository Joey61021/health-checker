package com.healthchecker.plugin.services.Message;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Message {

    GENERAL_NO_PERMISSION("Messages.No-Permission"),
    GENERAL_ENTER_PLAYER("Messages.Enter-Player"),
    GENERAL_NO_PLAYER("Messages.No-Player"),
    CMD_HEALTH("Commands.Health.Message"),
    CMD_HUNGER("Commands.Hunger.Message");

    @NonNull
    @Getter
    private final String path;
}

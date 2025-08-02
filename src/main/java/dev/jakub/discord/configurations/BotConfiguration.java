package dev.jakub.discord.configurations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BotConfiguration {

    private final String token;

    // These are the API keys for the translation services
    private final String deepLAuthKey;
    private final String googleAuthKey;
    private final String microsoftAuthKey;
    private final String openAiAuthKey;


}
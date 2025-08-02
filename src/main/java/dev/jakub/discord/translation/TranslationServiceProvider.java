package dev.jakub.discord.translation;

import dev.jakub.discord.configurations.BotConfiguration;
import dev.jakub.discord.translation.service.deepl.DeepLTranslationService;
import dev.jakub.discord.translation.service.google.GoogleTranslationServiceProvider;
import lombok.Getter;

@Getter
public class TranslationServiceProvider {

    private final BotConfiguration botConfiguration;

    private DeepLTranslationService deepLTranslationService;
    private GoogleTranslationServiceProvider googleTranslationService;

    public TranslationServiceProvider(BotConfiguration botConfiguration) {
        this.botConfiguration = botConfiguration;

        if (botConfiguration.getDeepLAuthKey() != null && !botConfiguration.getDeepLAuthKey().isEmpty()) {
            deepLTranslationService = new DeepLTranslationService(botConfiguration.getDeepLAuthKey());
        }
        if (botConfiguration.getGoogleAuthKey() != null && !botConfiguration.getGoogleAuthKey().isEmpty()) {
            this.googleTranslationService = new GoogleTranslationServiceProvider(botConfiguration.getGoogleAuthKey());
        }
    }
}

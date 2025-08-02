package dev.jakub.discord.translation;

import dev.jakub.discord.configurations.BotConfiguration;
import dev.jakub.discord.translation.service.deepl.DeepLTranslationService;
import lombok.Getter;

@Getter
public class TranslationServiceProvider {

    private final BotConfiguration botConfiguration;

    private DeepLTranslationService deepLTranslationService;

    public TranslationServiceProvider(BotConfiguration botConfiguration) {
        this.botConfiguration = botConfiguration;

        if (botConfiguration.getDeepLAuthKey() != null && !botConfiguration.getDeepLAuthKey().isEmpty()) {
            deepLTranslationService = new DeepLTranslationService(botConfiguration.getDeepLAuthKey());
        }
    }
}

package dev.jakub.discord.translation.service;

import dev.jakub.discord.bot.TranslatorBot;
import lombok.Getter;

import java.util.List;

@Getter
public enum ServiceType {

    GOOGLE(0, "", ""),
    MICROSOFT(0, "", ""),
    OPENAI(0, "", ""),
    DEEPL(0, "", "");

    private final int supportedLanguages;
    private final String description;
    private final String emoji;

    ServiceType(int supportedLanguages, String description, String emoji) {
        this.supportedLanguages = supportedLanguages;
        this.description = description;
        this.emoji = emoji;
    }

    public static ServiceType fromString(String string) {
        for (ServiceType serviceType : values()) {
            if (serviceType.name().equalsIgnoreCase(string)) {
                return serviceType;
            }
        }
        return null;
    }

    public static List<String> getServices() {
        StringBuilder services = new StringBuilder();
        for (ServiceType serviceType : values()) {
            if (serviceType.getSupportedLanguages() > 0)
                services.append(serviceType.name()).append(", ");
        }
        return List.of(services.substring(0, services.toString().length() - 2));
    }
}
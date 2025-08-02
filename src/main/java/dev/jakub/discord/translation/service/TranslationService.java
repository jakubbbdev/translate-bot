package dev.jakub.discord.translation.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface TranslationService {

    /**
     * @param text            - the text to translate
     * @param sourceLanguage  - the language to translate from, can be null
     * @param targetLanguages - the language to translate to
     * @return the translated text
     **/
    String translate(@NotNull String text, @Nullable String sourceLanguage, @NotNull List<String> targetLanguages);

    /**
     * @param text - the text to translate
     * @return the language code of the source language
     */
    String getSourceLanguage(@NotNull String text);

}
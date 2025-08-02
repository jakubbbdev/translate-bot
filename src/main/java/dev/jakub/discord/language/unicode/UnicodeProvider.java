package dev.jakub.discord.language.unicode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
public class UnicodeProvider {

    private final HashMap<String, String> unicode;

}
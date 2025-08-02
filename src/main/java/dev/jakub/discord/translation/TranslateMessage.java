package dev.jakub.discord.translation;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.User;

@Getter
@Setter
public class TranslateMessage {

    private final User user;
    private final String guildId;
    private final String channelId;

    private String text;

    public TranslateMessage(User user, String guildId, String channelId, String text) {
        this.user = user;
        this.guildId = guildId;
        this.channelId = channelId;
        this.text = text;
    }
}

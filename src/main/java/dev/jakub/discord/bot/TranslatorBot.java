package dev.jakub.discord.bot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.io.File;
import java.util.logging.Logger;

@Getter
public class TranslatorBot {

    @Getter
    private static TranslatorBot instance;
    public static final Logger LOGGER = Logger.getLogger("TranslatorBot");

    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
    private final File file = new File("bot");

    private ShardManager shardManager;


    public TranslatorBot() {
        instance = this;
        onEnable();
    }

    public void onEnable() {

    }

    public void onDisable() {

    }
}

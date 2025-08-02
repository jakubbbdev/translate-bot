package dev.jakub.discord.bot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.jakub.discord.configurations.BotConfiguration;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.apache.commons.collections4.iterators.EnumerationIterator;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
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
        BotConfiguration botConfiguration = loadConfiguration();

        DefaultShardManagerBuilder shardManagerBuilder = DefaultShardManagerBuilder.createDefault(botConfiguration.getToken());

        this.shardManager = shardManagerBuilder.build();
        this.shardManager.setActivity(Activity.customStatus("Use /translate"));
    }

    public void onDisable() {

    }

    @SneakyThrows
    private BotConfiguration loadConfiguration() {
        if (file.mkdirs()) {
            LOGGER.info("Created bot folder");
        }

        final Path path = Paths.get("bot/config.json");
        if (!Files.exists(path)) {
            Files.copy(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("config.json")), path);
            LOGGER.info("Created config.json");
        }

        final BotConfiguration botConfiguration = gson.fromJson(Files.newBufferedReader(path), BotConfiguration.class);
        if (botConfiguration == null) {
            LOGGER.severe("Failed to load config.json");
            System.exit(0);
        }
        return botConfiguration;
    }
}

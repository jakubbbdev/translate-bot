package dev.jakub.discord.bot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jdi.request.StepRequest;
import dev.jakub.discord.configurations.BotConfiguration;
import dev.jakub.discord.language.UnicodeLoader;
import dev.jakub.discord.language.unicode.UnicodeProvider;
import dev.jakub.discord.translation.TranslationServiceProvider;
import dev.jakub.discord.translation.service.TranslationService;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.apache.commons.collections4.iterators.EnumerationIterator;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

@Getter
public class TranslatorBot {

    @Getter
    private static TranslatorBot instance;
    public static final Logger LOGGER = Logger.getLogger("TranslatorBot");

    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
    private final File file = new File("bot");

    private ShardManager shardManager;

    private UnicodeLoader unicodeLoader;
    private TranslationServiceProvider translationService;


    public static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(10);

    public TranslatorBot() {
        instance = this;
        onEnable();
    }

    public void onEnable() {
        BotConfiguration botConfiguration = loadConfiguration();

        DefaultShardManagerBuilder shardManagerBuilder = DefaultShardManagerBuilder.createDefault(botConfiguration.getToken());

        configureMemoryUsage(shardManagerBuilder);
        this.shardManager = shardManagerBuilder.build();
        this.shardManager.setActivity(Activity.customStatus("Use /translate"));

        Runtime.getRuntime().addShutdownHook(new Thread(this::onDisable));
    }

    public void onDisable() {
        LOGGER.info("Shutting down...");
        shardManager.shutdown();
        EXECUTOR_SERVICE.shutdown();
        LOGGER.info("Shut down!");
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

    public String getEmoji(String countryCode) {
        if (unicodeLoader.getUnicodeProvider().getUnicode().isEmpty()) {
            System.out.println("UnicodeProvider is empty!");
        }
        if (unicodeLoader.getUnicodeProvider().getUnicode().containsKey(countryCode.toUpperCase())) {
            return unicodeLoader.getUnicodeProvider().getUnicode().get(countryCode.toUpperCase());
        }
        return null;
    }

    private void configureMemoryUsage(DefaultShardManagerBuilder shardManagerBuilder) {
        shardManagerBuilder.disableCache(
                CacheFlag.ACTIVITY,
                CacheFlag.VOICE_STATE,
                CacheFlag.CLIENT_STATUS,
                CacheFlag.SCHEDULED_EVENTS,
                CacheFlag.STICKER);
        shardManagerBuilder.enableIntents(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MESSAGE_TYPING);
    }
}

package com.elypia.jdac.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.regex.Pattern;

/**
 * A logger for Discord which will redirect some of the logs
 * to a Discord channel.
 */
public final class DiscordAppender extends AppenderBase<ILoggingEvent> {

    /**
     * Pattern to split the class from the field required.
     */
    private static final Pattern SPLIT = Pattern.compile("(?<class>.+)\\.(?<member>.+)");

    /**
     * Configurable property on configuration to define
     * where to obtain JDA.
     */
    private String path;

    /**
     * The ID of the channel to log to, this can be
     * private or in a guild so long as the {@link JDA}
     * instance at {@link #path} can see it.
     */
    private long id;

    /**
     * Loaded by the {@link #init()} method.
     * A reference to the JDA instance at {@link #path}.
     */
    private JDA jda;

    /**
     * If this logger has been initalized, this does
     * not mean the logger is in a valid state.
     */
    private boolean initialized;

    private boolean init() {
        initialized = true;

        String[] split = SPLIT.split(path);
        Class<?> clazz;

        try {
            clazz = Class.forName(split[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try {
            jda = (JDA)clazz.getField(split[1]).get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void append(ILoggingEvent eventObject) {

        if (jda == null) {
            if (initialized)
                return;

            if (!init())
                return;
        }

        String message = eventObject.getFormattedMessage();
        MessageChannel channel = jda.getPrivateChannelById(id);

        if (channel != null)
            channel.sendMessage(message).queue();
        else
            jda.getTextChannelById(id).sendMessage(message).queue();

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}


package com.elypia.commandler.discord.interfaces;

import com.elypia.commandler.CommandlerEvent;
import com.elypia.commandler.interfaces.ResponseProvider;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

import java.util.Objects;

public interface DiscordProvider<O> extends ResponseProvider<O, Message> {

    @Override
    default Message provide(CommandlerEvent<?> event, O output) {
        Objects.requireNonNull(output);
        GenericMessageEvent source = (GenericMessageEvent)event.getSource();

        if (canSendEmbed(source)) {
            Message embed =  buildEmbed(event, output);

            if (embed != null)
                return embed;
        }

        return buildMessage(event, output);
    }

    Message buildMessage(CommandlerEvent<?> event, O output);

    default Message buildEmbed(CommandlerEvent<?> event, O output) {
        return null;
    }

    private boolean canSendEmbed(GenericMessageEvent source) {
        if (!source.getChannelType().isGuild())
            return true;

        Member self = source.getGuild().getSelfMember();
        TextChannel channel = source.getTextChannel();
        return self.hasPermission(channel, Permission.MESSAGE_EMBED_LINKS);
    }
}

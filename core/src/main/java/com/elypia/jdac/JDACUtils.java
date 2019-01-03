package com.elypia.jdac;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;

public final class JDACUtils {

    private JDACUtils() {
        // Can't instantiate this class.
    }

    public static boolean canSendEmbeds(GenericMessageEvent source) {
        if (source.getChannelType().isGuild()) {
            Member self = source.getGuild().getSelfMember();
            TextChannel channel = source.getTextChannel();
            return self.hasPermission(channel, Permission.MESSAGE_EMBED_LINKS);
        }

        return true;
    }
}

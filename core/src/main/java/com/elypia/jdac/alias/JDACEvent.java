package com.elypia.jdac.alias;

import com.elypia.commandler.impl.CommandEvent;
import com.elypia.commandler.interfaces.ICommandEvent;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.*;

public class JDACEvent extends CommandEvent<GenericMessageEvent, Message> {

    public JDACEvent(ICommandEvent<GenericMessageEvent, Message> event) {
        super(event.getCommandler(), event.getSource(), event.getInput());
    }

    public MessageReceivedEvent asMessageRecieved() {
        return (MessageReceivedEvent)source;
    }

    public boolean delete() {
        MessageReceivedEvent source = asMessageRecieved();

        if (!source.getChannelType().isGuild())
            return false;

        Member member = source.getGuild().getSelfMember();

        if (source.getAuthor().getIdLong() == member.getUser().getIdLong()) {
            source.getMessage().delete().queue();
            return true;
        }

        TextChannel channel = source.getTextChannel();

        if (!member.hasPermission(channel, Permission.MANAGE_PERMISSIONS))
            return false;

        source.getMessage().delete().queue();
        return true;
    }

    @Override
    public <T> Message send(T output) {
        if (output instanceof String)
            return send((String)output);

        Message message = builder.build(this, output);
        source.getChannel().sendMessage(message).queue();
        return message;
    }
}

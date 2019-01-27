package com.elypia.jdac;

import com.elypia.commandler.CommandInput;
import com.elypia.commandler.Commandler;
import com.elypia.commandler.impl.CommandEvent;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Map;

public class JDACEvent extends CommandEvent<GenericMessageEvent, Message> {

    public JDACEvent(Commandler<GenericMessageEvent, Message> commandler, GenericMessageEvent source, CommandInput input) {
        super(commandler, source, input);
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
        Message message = builder.build(this, output);
        source.getChannel().sendMessage(message).queue();
        return message;
    }

    @Override
    public <T> Message send(String body, Map<String, T> params) {
        String content = scripts.get(source, body, params);
        Message message = new MessageBuilder(content).build();
        source.getChannel().sendMessage(content).queue();
        return message;
    }
}

package com.elypia.jdac.test;

import com.elypia.commandler.ModulesContext;
import com.elypia.jdac.JDAC;
import com.elypia.jdac.test.impl.module.ValidationModule;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JDACTest {

    private static JDAC jdac;

    /**
     * A mock event with mock Discord data.
     */
    private static MessageReceivedEvent event;

    @BeforeAll
    public static void beforeAll() {
        ModulesContext context = new ModulesContext();
        context.addModules(ValidationModule.class);

        jdac = new JDAC.Builder()
            .setPrefix(">")
            .setContext(context)
            .build();

        TextChannel channel = mock(TextChannel.class);
        when(channel.getName()).thenReturn("MyChannel");
        when(channel.canTalk()).thenReturn(false);
        when(channel.getType()).thenReturn(ChannelType.TEXT);

        Member member = mock(Member.class);
        when(member.hasPermission(channel, Permission.MESSAGE_EMBED_LINKS)).thenReturn(false);

        Guild guild = mock(Guild.class);
        when(guild.getTextChannels()).thenReturn(List.of(channel));
        when(guild.getSelfMember()).thenReturn(member);

        Message message = mock(Message.class);

        event = mock(MessageReceivedEvent.class);
        when(event.getGuild()).thenReturn(guild);
        when(event.getTextChannel()).thenReturn(channel);
        when(event.getMessage()).thenReturn(message);
        when(event.getChannelType()).thenReturn(ChannelType.TEXT);
    }

    @Test
    public void testInvalidTalkableChannel() {
        String expected =
            "Command failed; a parameter was invalidated.\n" +
            "Module: Validation\n" +
            "Command: Talkable\n" +
            "\n" +
            "channel: Must be a channel where I have read & send message permission.";

        String command = ">valid talkable MyChannel";
        when(event.getMessage().getContentRaw()).thenReturn(command);

        String actual = jdac.getProcessor().dispatch(event, command, false)
            .getContentRaw();

        assertEquals(expected, actual);
    }
}

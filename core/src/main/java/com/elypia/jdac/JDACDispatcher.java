package com.elypia.jdac;

import com.elypia.jdac.alias.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JDACDispatcher extends ListenerAdapter {

    private JDAC jdac;
    private JDACProcessor processor;

    public JDACDispatcher(JDAC jdac) {
        this.jdac = jdac;
        processor = new JDACProcessor(jdac);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;

        processor.dispatch(event, event.getMessage().getContentRaw(), true);
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
        boolean command = processor.isCommand(event, event.getMessage().getContentRaw());

        if (command || event.getAuthor().isBot())
            return;

        Message updatedMessage = event.getMessage();

        event.getChannel().getHistoryAfter(updatedMessage.getIdLong(), 1).queue(history -> {
            if (history.isEmpty())
                processor.dispatch(event, event.getMessage().getContentRaw(), true);

            else {
                Message nextMessage = history.getRetrievedHistory().get(0);
                if (nextMessage.getAuthor().getIdLong() == event.getJDA().getSelfUser().getIdLong()) {
                    Message message = processor.dispatch(event, event.getMessage().getContentRaw(), false);
                    nextMessage.editMessage(message).queue();
                }
            }
        });
    }
}

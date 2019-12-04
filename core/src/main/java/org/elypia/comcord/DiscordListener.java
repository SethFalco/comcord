/*
 * Copyright 2019-2019 Elypia CIC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.elypia.comcord;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.*;

/**
 * @author seth@elypia.org (Seth Falco)
 */
public class DiscordListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(DiscordListener.class);

    private final DiscordIntegration integration;
    private final boolean listeningToBots;

    public DiscordListener(final DiscordIntegration integration, final DiscordConfig config) {
        this.integration = integration;
        this.listeningToBots = config.isListeningToBots();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() && !listeningToBots)
            return;

        String content = event.getMessage().getContentRaw();

        Message message = integration.process(event, event.getMessage(), content);

        if (message != null)
            event.getChannel().sendMessage(message).queue();
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
        String content = event.getMessage().getContentRaw();

        if (event.getAuthor().isBot() && !listeningToBots)
            return;

        Message updatedMessage = event.getMessage();
        MessageChannel channel = event.getChannel();

        channel.getHistoryAfter(updatedMessage.getIdLong(), 1).queue(history -> {
            if (history.isEmpty()) {
                Message message = integration.process(event, event.getMessage(), content);

                if (message != null)
                    channel.sendMessage(message).queue();
            }
            else {
                Message nextMessage = history.getRetrievedHistory().get(0);

                if (nextMessage.getAuthor().getIdLong() == event.getJDA().getSelfUser().getIdLong()) {
                    Message message = integration.process(event, event.getMessage(), content);

                    if (message != null)
                        nextMessage.editMessage(message).queue();
                }
            }
        });
    }
}

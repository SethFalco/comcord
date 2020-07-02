/*
 * Copyright 2019-2020 Elypia CIC
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
import org.slf4j.*;

/**
 * @author seth@elypia.org (Seth Falco)
 */
public class DiscordListener extends ActivatedListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(DiscordListener.class);

    private final DiscordIntegration integration;
    private final boolean listeningToBots;

    public DiscordListener(final DiscordIntegration integration) {
        this.integration = integration;
        this.listeningToBots = integration.getComcordConfig().isListeningToBots();
    }

    /**
     * When receiving a message, handle it normally.
     *
     * @param event All text message events, in DM or a guild text channel.
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (shouldIgnoreMesage(event.getAuthor()))
            return;

        Message message = event.getMessage();
        String content = message.getContentRaw();

        Message response = integration.getListener().onAction(integration, event, message, content);
        respond(event, response);
    }

    /**
     * When receiving an edit event, we'll still perform if it's
     * the last message, or a message from us is the message after it.
     *
     * @param event All text message edit events, in DM or a guild text channel.
     */
    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
        if (shouldIgnoreMesage(event.getAuthor()) || !integration.getComcordConfig().listenToEditEvents())
            return;

        MessageChannel channel = event.getChannel();
        Message message = event.getMessage();
        String content = message.getContentRaw();

        channel.getHistoryAfter(message.getIdLong(), 1).queue((history) -> {
            if (history.isEmpty()) {
                Message response = integration.getListener().onAction(integration, event, message, content);
                respond(event, response);
            } else {
                Message nextMessage = history.getRetrievedHistory().get(0);

                if (nextMessage.getAuthor() == event.getJDA().getSelfUser()) {
                    Message response = integration.getListener().onAction(integration, event, message, content);

                    if (response != null)
                        nextMessage.editMessage(response).override(true).queue();
                }
            }
        });
    }

    /**
     * @param user The user that performed an action.
     * @return If we are interested in responding to this user.
     */
    private boolean shouldIgnoreMesage(User user) {
        return user.isBot() && !listeningToBots;
    }

    /**
     * @param event The event that represents the user action.
     * @param message The message to respond with, nothing will happen
     * if there is no permission to write in the channel the event took place in.
     */
    private void respond(GenericMessageEvent event, Message message) {
        if (message == null)
            return;

        if (event.isFromGuild() && !event.getTextChannel().canTalk())
            logger.warn("A command was performed however we don't have write permission in the channel it was performed in.");
        else
            event.getChannel().sendMessage(message).queue();
    }
}

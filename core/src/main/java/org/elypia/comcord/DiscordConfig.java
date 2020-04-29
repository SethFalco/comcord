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

import org.apache.deltaspike.core.api.config.*;

/**
 * @author seth@elypia.org (Seth Falco)
 */
@Configuration(prefix = "comcord.")
public interface DiscordConfig {

    /**
     * The Discord bot token.
     * This can be obtained on the Discord Developer Portal.
     *
     * @see <a href="https://discordapp.com/developers/applications/">Discord Developer Portal</a>
     */
    @ConfigProperty(name = "bot-token")
    String getBotToken();

    /**
     * The support guild to get help with the bot.
     * ComCord does not specifically use this, but it's
     * deemed as common metadata for any ComCord application.
     */
    @ConfigProperty(name ="support-guild-id")
    long getSupportGuildId();

    /**
     * If the {@link DiscordListener} implementation should
     * listen to bots or ignore them.
     *
     * It's <strong>strongly</strong> recommended that this is
     * false, to avoid bots from triggering eachothers commands.
     */
    @ConfigProperty(name = "listen-to-bots")
    boolean isListeningToBots();
}

/*
 * Copyright 2019-2025 Seth Falco and Comcord Contributors
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

package org.elypia.comcord.configuration;

import org.apache.deltaspike.core.api.config.*;

/**
 * @author seth@falco.fun (Seth Falco)
 */
@Configuration(prefix = "discord.")
public interface DiscordConfig {

    /**
     * The Discord bot token.
     * This can be obtained on the Discord Developer Portal.
     *
     * @see <a href="https://discord.com/developers/applications/">Discord Developer Portal</a>
     */
    @ConfigProperty(name = "bot-token")
    String getBotToken();
}

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

package fun.falco.comcord.configuration;

import net.dv8tion.jda.api.MessageBuilder;
import org.apache.deltaspike.core.api.config.*;
import fun.falco.comcord.DiscordListener;

/**
 * @author seth@falco.fun (Seth Falco)
 */
@Configuration(prefix = "comcord.")
public interface ComcordConfig {

    /**
     * It's <strong>strongly</strong> recommended that this is
     * false, to avoid bots from triggering eachothers commands.
     *
     * @return If the {@link DiscordListener} implementation should
     * listen to bots or ignore them.
     */
    @ConfigProperty(name = "listen-to-bots", defaultValue = "false")
    boolean isListeningToBots();

    /**
     * @return If edits of messages should count as requests.
     */
    @ConfigProperty(name = "listen-to-edit-events", defaultValue = "true")
    boolean listenToEditEvents();

    /**
     * @return If when making calls to "build", should
     * {@link net.dv8tion.jda.api.MessageBuilder#buildAll(MessageBuilder.SplitPolicy...)}
     * or just the regular {@link MessageBuilder#build()}.
     */
    @ConfigProperty(name = "should-build-all", defaultValue = "false")
    boolean shouldBuildAll();
}

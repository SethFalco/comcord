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

package fun.falco.comcord.adapters;

import net.dv8tion.jda.api.entities.Activity;
import org.elypia.commandler.annotation.stereotypes.ParamAdapter;
import org.elypia.commandler.api.Adapter;
import org.elypia.commandler.event.ActionEvent;
import org.elypia.commandler.metadata.MetaParam;

import java.util.Objects;

/**
 * {@link Adapter} that adapts the {@link net.dv8tion.jda.api.entities.Activity.ActivityType}
 * provided by JDA.
 *
 * @author seth@falco.fun (Seth Falco)
 * @since 2.1.0
 */
@ParamAdapter(Activity.ActivityType.class)
public class ActivityTypeAdapter implements Adapter<Activity.ActivityType> {

    @Override
    public Activity.ActivityType adapt(String input, Class<? extends Activity.ActivityType> type, MetaParam metaParam, ActionEvent<?, ?> event) {
        Objects.requireNonNull(input);
        input = input.toLowerCase().replace(" ", "");

        for (Activity.ActivityType activityType : Activity.ActivityType.values()) {
            String name = activityType.name().toLowerCase().replace("_", "");

            if (name.equalsIgnoreCase(input) || String.valueOf(activityType.getKey()).equals(input))
                return activityType;
        }

        switch (input) {
            case "default":
            case "playing":
            case "play":
                return Activity.ActivityType.DEFAULT;
            case "streaming":
            case "stream":
            case "twitch":
                return Activity.ActivityType.STREAMING;
            case "listening":
            case "listen":
                return Activity.ActivityType.LISTENING;
            case "watching":
            case "watch":
                return Activity.ActivityType.WATCHING;
            case "customstatus":
            case "custom":
                return Activity.ActivityType.CUSTOM_STATUS;
            default:
                return null;
        }
    }
}

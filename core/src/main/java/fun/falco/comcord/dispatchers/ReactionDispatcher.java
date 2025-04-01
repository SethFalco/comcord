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

package fun.falco.comcord.dispatchers;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.elypia.commandler.CommandlerExtension;
import org.elypia.commandler.api.*;
import org.elypia.commandler.event.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * @author seth@falco.fun (Seth Falco)
 */
@ApplicationScoped
public class ReactionDispatcher implements Dispatcher {

    private CommandlerExtension commandlerExtension;
    private ActionCache cache;

    @Inject
    public ReactionDispatcher(CommandlerExtension commandlerExtension, ActionCache cache) {
        this.commandlerExtension = commandlerExtension;
        this.cache = cache;
    }

    /**
     * Check if this is a valid reaction event.
     * It's deemed valid if type of event is
     * for adding a reaction, the reaction added
     * is a unicode emoji, and message that received
     * the reaction is known in the {@link ActionCache}.
     *
     * @param request
     * @param <S>
     * @param <M>
     * @return
     */
    @Override
    public <S, M> boolean isValid(Request<S, M> request) {
        S source = request.getSource();

        if (source instanceof MessageReactionAddEvent) {
            MessageReactionAddEvent event = (MessageReactionAddEvent)source;

            if (event.getReactionEmote().isEmoji()) {
                Serializable id = request.getIntegration().getActionId(source);
                Action action = cache.get(id);
                return action != null;
            }
        }

        return false;
    }

    @Override
    public <S, M> ActionEvent<S, M> parse(Request<S, M> request) {
//        MetaController selectedMetaController = null;
//        MetaCommand selectedMetaCommand = null;
//        List<List<String>> parameters = null;
//
//        for (MetaController metaController : config.getControllers()) {
//            for (MetaCommand metaCommand : metaController.getMetaCommands()) {
//                String patternString = metaCommand.getProperty(this.getClass(), "pattern");
//
//                if (patternString == null)
//                    continue;
//
//                final Pattern pattern;
//
//                if (!PATTERNS.containsKey(patternString)) {
//                    pattern = Pattern.compile(patternString);
//                    PATTERNS.put(patternString, pattern);
//                } else {
//                    pattern = PATTERNS.get(patternString);
//                }
//
//                Matcher matcher = pattern.matcher(request.getContent());
//
//                if (!matcher.find())
//                    return null;
//
//                selectedMetaController = metaController;
//                selectedMetaCommand = metaCommand;
//                parameters = new ArrayList<>();
//
//                for (int i = 0; i < matcher.groupCount(); i++) {
//                    String group = matcher.group(i + 1);
//                    parameters.add(List.of(group));
//                }
//
//                break;
//            }
//        }
//
//        if (selectedMetaController == null)
//            return null;
//
//        Serializable id = request.getIntegration().getActionId(request.getSource());
//
//        if (id == null)
//            throw new IllegalStateException("All user interactions must be associated with a serializable ID.");
//
//        Action action = new Action(id, request.getContent(), selectedMetaController.getName(), selectedMetaCommand.getName(), parameters);
//        ActionEvent<S, M> e = new ActionEvent<>(request, action, selectedMetaController, selectedMetaCommand);
//
//        if (!selectedMetaCommand.isValidParamCount(parameters.size()))
//            throw new ParamCountMismatchException(e);
//
        return null;
    }
}

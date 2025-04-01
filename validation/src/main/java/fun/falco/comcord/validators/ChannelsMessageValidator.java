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

package fun.falco.comcord.validators;

import net.dv8tion.jda.api.entities.*;
import fun.falco.comcord.constraints.Channels;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.*;

/**
 * @author seth@falco.fun (Seth Falco)
 */
@ApplicationScoped
public class ChannelsMessageValidator implements ConstraintValidator<Channels, Message> {

    private ChannelType[] types;

    @Override
    public void initialize(Channels constraintAnnotation) {
        types = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Message message, ConstraintValidatorContext context) {
        ChannelType type = message.getChannelType();

        for (ChannelType channelType : types) {
            if (channelType == type)
                return true;
        }

        return false;
    }
}

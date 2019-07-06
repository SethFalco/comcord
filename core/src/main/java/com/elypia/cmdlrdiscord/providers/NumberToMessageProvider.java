package com.elypia.cmdlrdiscord.providers;

import com.elypia.commandler.CommandlerEvent;
import com.elypia.commandler.annotations.Provider;
import com.elypia.commandler.interfaces.ResponseProvider;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

import javax.inject.Inject;
import java.text.NumberFormat;

@Provider(provides = String.class, value = {Number.class, double.class, float.class, long.class, int.class, short.class, byte.class})
public class NumberToMessageProvider implements ResponseProvider<Number, Message> {

    private NumberFormat format;

    public NumberToMessageProvider() {
        this(NumberFormat.getInstance());
    }

    @Inject
    public NumberToMessageProvider(NumberFormat format) {
        this.format = format;
    }

    @Override
    public Message provide(CommandlerEvent<?> event, Number output) {
        return new MessageBuilder(format.format(output)).build();
    }
}

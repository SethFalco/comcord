package com.elypia.jdac.alias;

import com.elypia.commandler.interfaces.IParser;

/**
 * This is just a short cut rather than specifying JDACEvent
 * on every IParser.
 *
 * @param <O> The output type of this Parser.
 */
public interface IJDACParser<O> extends IParser<JDACEvent, O> {

}

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

package org.elypia.comcord.adapters;

import net.dv8tion.jda.api.entities.Activity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author seth@elypia.org (Seth Falco)
 * @since 2.1.0
 */
public class ActivityTypeAdapterTest {

    @Test
    public void testAdaptingWatching() {
        ActivityTypeAdapter adapter = new ActivityTypeAdapter();

        final Activity.ActivityType expected = Activity.ActivityType.WATCHING;
        final Activity.ActivityType actual = adapter.adapt("watching");

        assertEquals(expected, actual);
    }

    @Test
    public void testAdaptingStream() {
        ActivityTypeAdapter adapter = new ActivityTypeAdapter();

        final Activity.ActivityType expected = Activity.ActivityType.STREAMING;
        final Activity.ActivityType actual = adapter.adapt("stream");

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "random", "can't be done"})
    public void testNull(String value) {
        ActivityTypeAdapter adapter = new ActivityTypeAdapter();
        assertNull(adapter.adapt(value));
    }
}

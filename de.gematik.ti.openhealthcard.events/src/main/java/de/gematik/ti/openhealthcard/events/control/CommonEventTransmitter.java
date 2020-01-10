/*
 * Copyright (c) 2020 gematik GmbH
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

package de.gematik.ti.openhealthcard.events.control;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.greenrobot.eventbus.EventBus;

import de.gematik.ti.openhealthcard.events.message.ErrorEvent;
import de.gematik.ti.openhealthcard.events.message.InfoEvent;
import de.gematik.ti.openhealthcard.events.message.WarnEvent;

/**
 * 
 * include::{userguide}/OHCCOM_Structure.adoc[tag=CommonEventTransmitter]
 *
 * @author christian.lange
 * @version 1.0
 */
public final class CommonEventTransmitter {

    private static String packageFilter = "de.gematik.";

    private CommonEventTransmitter() {
        // Empty
    }

    /**
     * Sends an Error-Event to the EventBus
     * 
     * @param exception
     */
    public static void postError(final Exception exception) {
        EventBus.getDefault().post(new ErrorEvent(findClassName(), exception));
    }

    private static String findClassName() {
        Optional<StackTraceElement> stackTraceElement = Stream.of(Thread.currentThread().getStackTrace())
                .filter(checkStackTraceElement())
                .findFirst();
        return stackTraceElement.isPresent() ? stackTraceElement.get().getClassName() : "CallerClassNotFound";
    }

    private static Predicate<StackTraceElement> checkStackTraceElement() {
        return element -> element.getClassName().startsWith(packageFilter) && //
                !element.getClassName().equals(CommonEventTransmitter.class.getCanonicalName());
    }

    /**
     * Sends an Error-Event to the EventBus
     * 
     * @param exception
     * @param message
     *            custom message
     */
    public static void postError(final Exception exception, final String message) {
        EventBus.getDefault().post(new ErrorEvent(findClassName(), exception, message));
    }

    /**
     * Sends an Info-Event to the EventBus
     *
     * @param message
     *            custom message
     */
    public static void postInfo(final String message) {
        EventBus.getDefault().post(new InfoEvent(findClassName(), message));
    }

    /**
     * Sends an Warning-Event to the EventBus
     * 
     * @param message
     *            custom message
     */
    public static void postWarn(final String message) {
        EventBus.getDefault().post(new WarnEvent(findClassName(), message));
    }

    /**
     * Set the Package Filter for the Stacktrace analysis for send Message Events
     * default Value is "de.gematik."
     * @param packageFilter
     */
    public static void setPackageFilter(final String packageFilter) {
        CommonEventTransmitter.packageFilter = packageFilter;
    }
}

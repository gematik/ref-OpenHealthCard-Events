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

package de.gematik.ti.openhealthcard.events.message;

/**
 * Event type to inform subscriber over EventBus about failure
 *
 * @author christian.lange
 * @version 1.0
 */
public class ErrorEvent extends AbstractOpenHealthCardEvent {
    private Throwable throwable;

    public ErrorEvent(final Object source, final String message) {
        super(source, message);
    }

    public ErrorEvent(final Object source, final Throwable throwable, final String message) {
        super(source, message);
        this.throwable = throwable;
    }

    public ErrorEvent(final Object source, final Throwable throwable) {
        super(source, throwable.getMessage());
        this.throwable = throwable;
    }

    public ErrorEvent(final String sourceClass, final String message) {
        super(sourceClass, message);
    }

    public ErrorEvent(final String sourceClass, final Throwable throwable, final String message) {
        super(sourceClass, message);
        this.throwable = throwable;
    }

    public ErrorEvent(final String sourceClass, final Throwable throwable) {
        super(sourceClass, throwable.getMessage());
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public String toString() {
        return "ErrorEvent{" + "throwable=" + throwable + "} " + super.toString();
    }
}

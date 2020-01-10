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

import org.junit.Assert;
import org.junit.Test;

import de.gematik.ti.openhealthcard.events.message.ErrorEvent;

public class ErrorEventTest {

    public static final String MSG = "MyExceptionMessage";
    public static final String THROW_MSG = "Throwable Messsage";
    public static final String SRCCLASS = "SourceClassString";

    @Test
    public void createErrorEventSourceMessage() {
        ErrorEvent event = new ErrorEvent(this, MSG);
        Assert.assertEquals(MSG, event.getMessage());
        Assert.assertEquals(this.getClass().getCanonicalName(), event.getSourceClass());
    }

    @Test
    public void createErrorEventNullSourceMessage() {
        ErrorEvent event = new ErrorEvent(null, MSG);
        Assert.assertEquals(MSG, event.getMessage());
        Assert.assertEquals("", event.getSourceClass());
    }

    @Test
    public void createErrorEventSourceStringMessage() {
        ErrorEvent event = new ErrorEvent(SRCCLASS, MSG);
        Assert.assertEquals(MSG, event.getMessage());
        Assert.assertEquals(SRCCLASS, event.getSourceClass());

    }

    @Test
    public void createErrorEventSourceThrowableMessage() {
        Exception throwable = new Exception(THROW_MSG);
        ErrorEvent event = new ErrorEvent(this, throwable, MSG);
        Assert.assertEquals(MSG, event.getMessage());
        Assert.assertEquals(this.getClass().getCanonicalName(), event.getSourceClass());
        Assert.assertEquals(throwable, event.getThrowable());
    }

    @Test
    public void createErrorEventSourceClassThrowableMessage() {
        Exception throwable = new Exception(THROW_MSG);
        ErrorEvent event = new ErrorEvent(SRCCLASS, throwable);
        Assert.assertEquals(THROW_MSG, event.getMessage());
        Assert.assertEquals(SRCCLASS, event.getSourceClass());
        Assert.assertEquals(throwable, event.getThrowable());
    }

    @Test
    public void createErrorEventSourceThrowable() {
        Exception throwable = new Exception(THROW_MSG);
        ErrorEvent event = new ErrorEvent(this, throwable, MSG);
        Assert.assertEquals(MSG, event.getMessage());
        Assert.assertEquals(this.getClass().getCanonicalName(), event.getSourceClass());
        Assert.assertEquals(throwable, event.getThrowable());
    }

    @Test
    public void createErrorEventSourceClassThrowable() {
        Exception throwable = new Exception(THROW_MSG);
        ErrorEvent event = new ErrorEvent(this, throwable);
        Assert.assertEquals(THROW_MSG, event.getMessage());
        Assert.assertEquals(this.getClass().getCanonicalName(), event.getSourceClass());
        Assert.assertEquals(throwable, event.getThrowable());
    }
}

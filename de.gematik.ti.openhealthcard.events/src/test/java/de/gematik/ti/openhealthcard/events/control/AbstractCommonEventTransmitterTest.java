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

import java.util.concurrent.CountDownLatch;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import de.gematik.ti.openhealthcard.events.message.AbstractOpenHealthCardEvent;

public abstract class AbstractCommonEventTransmitterTest {
    protected AbstractEventTransferCallback eventCallback;
    protected CountDownLatch eventLatch;

    public abstract class AbstractEventTransferCallback {
        abstract void onSuccess(AbstractOpenHealthCardEvent event);
    }

    @Before
    public void setup() {
        eventLatch = new CountDownLatch(1);
        EventBus.getDefault().register(this);
    }

    @After
    public void end() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void handle(final AbstractOpenHealthCardEvent event) {
        Assert.assertNotNull(eventCallback);
        eventCallback.onSuccess(event);
        eventLatch.countDown();
    }

    protected void checkEvent(final String msg, final String className, final AbstractOpenHealthCardEvent event) {
        System.out.println("JUNIT Event " + event + " empfangen!");
        Assert.assertNotNull(event);
        if (msg != null) {
            Assert.assertNotNull(event.getMessage());
        } else {
            Assert.assertNull(event.getMessage());
        }
        Assert.assertEquals(msg, event.getMessage());
        Assert.assertNotNull(event.getSourceClass());
        Assert.assertEquals(className, event.getSourceClass());
    }
}

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

import java.util.concurrent.TimeUnit;

import org.greenrobot.eventbus.EventBus;
import org.junit.Assert;
import org.junit.Test;

import de.gematik.ti.openhealthcard.events.message.AbstractOpenHealthCardEvent;
import de.gematik.ti.openhealthcard.events.message.InfoEvent;

public class CommonEventTransmitterInfoTest extends AbstractCommonEventTransmitterTest {
    public static final String MY_MESSAGE = "MyInfoMessage";
    public static final String TEST_CALLER_CLASS = "de.gematik.ti.openhealthcard.events.control.CommonEventTransmitterInfoTest";

    @Test
    public void postInfo() throws InterruptedException {
        final boolean[] testPassed = new boolean[] { false };
        eventCallback = new AbstractEventTransferCallback() {

            @Override
            public void onSuccess(AbstractOpenHealthCardEvent event) {
                checkEvent(MY_MESSAGE, TEST_CALLER_CLASS, event);
                Assert.assertTrue(event instanceof InfoEvent);
                testPassed[0] = true;
            }
        };

        CommonEventTransmitter.postInfo(MY_MESSAGE);

        eventLatch.await(5000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(testPassed[0]);
    }

    @Test
    public void postInfoWithObject() throws InterruptedException {
        final boolean[] testPassed = new boolean[] { false };
        eventCallback = new AbstractEventTransferCallback() {

            @Override
            public void onSuccess(AbstractOpenHealthCardEvent event) {
                checkEvent(MY_MESSAGE, TEST_CALLER_CLASS, event);
                Assert.assertTrue(event instanceof InfoEvent);
                testPassed[0] = true;
            }
        };

        EventBus.getDefault().post(new InfoEvent(this, MY_MESSAGE));

        eventLatch.await(5000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(testPassed[0]);
    }
}

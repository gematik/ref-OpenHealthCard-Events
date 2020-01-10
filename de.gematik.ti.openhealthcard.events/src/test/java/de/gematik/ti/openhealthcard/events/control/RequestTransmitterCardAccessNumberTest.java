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
import java.util.concurrent.TimeUnit;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gematik.ti.openhealthcard.events.request.RequestCardAccessNumberEvent;
import de.gematik.ti.openhealthcard.events.response.callbacks.ICardAccessNumberResponseListener;
import de.gematik.ti.openhealthcard.events.response.entities.CardAccessNumber;

public class RequestTransmitterCardAccessNumberTest {
    private static final Logger LOG = LoggerFactory.getLogger(RequestTransmitterCardAccessNumberTest.class);
    private static final String CAN = "ValueForCAN";
    private static final String ERROR_MESAGE_FOR_TEST = "ErrorMesageForTest";
    private static final String ABORT = "abort";

    private CountDownLatch countDownLatch;
    private AbstractEventTransferCallback eventTransferCallback;

    @Before
    public void setup() {
        countDownLatch = new CountDownLatch(1);
        EventBus.getDefault().register(this);
    }

    @Test
    @Deprecated
    public void requestCardAccessNumberOld() throws InterruptedException {
        final boolean[] testPassed = new boolean[] { false };
        final CardAccessNumber[] accessNumberResponse = { null };
        ICardAccessNumberResponseListener cardAccessNumberResponseListener = new ICardAccessNumberResponseListener() {
            @Override
            public void handleCan(final CardAccessNumber cardAccessNumber) {
                accessNumberResponse[0] = cardAccessNumber;

            }
        };

        eventTransferCallback = new AbstractEventTransferCallback() {
            @Override
            public void onSuccess(RequestCardAccessNumberEvent event) {
                LOG.debug("JUNIT Event " + event + " empfangen!");
                Assert.assertNotNull(event);
                Assert.assertNotNull(event.getResponseListener());
                event.getResponseListener().handleCan(new CardAccessNumber(CAN));
                Assert.assertNotNull(eventTransferCallback);
                testPassed[0] = true;
                countDownLatch.countDown();

            }
        };

        Assert.assertNotNull(eventTransferCallback);
        new RequestTransmitter().requestCardAccessNumber(cardAccessNumberResponseListener);
        countDownLatch.await(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(testPassed[0]);
        Assert.assertNotNull(accessNumberResponse[0]);
        Assert.assertEquals(CAN, accessNumberResponse[0].getValue());
    }

    @Test
    public void requestCardAccessNumber() throws InterruptedException {
        final boolean[] testPassed = new boolean[] { false };
        final CardAccessNumber[] accessNumberResponse = { null };

        eventTransferCallback = new AbstractEventTransferCallback() {
            @Override
            public void onSuccess(RequestCardAccessNumberEvent event) {
                LOG.debug("JUNIT Event " + event + " empfangen!");
                Assert.assertNotNull(event);
                Assert.assertNotNull(event.getResponseListener());
                event.getResponseListener().handleCan(new CardAccessNumber(CAN));
                Assert.assertNotNull(eventTransferCallback);
                testPassed[0] = true;
                countDownLatch.countDown();

            }
        };

        Assert.assertNotNull(eventTransferCallback);
        new RequestTransmitter().cardAccessNumber().request(getCardAccessNumberResponseListener(accessNumberResponse));
        countDownLatch.await(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(testPassed[0]);
        Assert.assertNotNull(accessNumberResponse[0]);
        Assert.assertEquals(CAN, accessNumberResponse[0].getValue());
    }

    @Test
    public void requestCardAccessNumberWithMessage() throws InterruptedException {
        final boolean[] testPassed = new boolean[] { false };
        final CardAccessNumber[] accessNumberResponse = { null };

        eventTransferCallback = new AbstractEventTransferCallback() {
            @Override
            public void onSuccess(RequestCardAccessNumberEvent event) {
                LOG.debug("JUNIT Event " + event + " empfangen!");
                Assert.assertNotNull(event);
                Assert.assertNotNull(event.getResponseListener());
                Assert.assertEquals(ERROR_MESAGE_FOR_TEST, event.getErrorMessage().get());
                event.getResponseListener().handleCan(new CardAccessNumber(CAN));
                Assert.assertNotNull(eventTransferCallback);
                testPassed[0] = true;
                countDownLatch.countDown();

            }
        };

        Assert.assertNotNull(eventTransferCallback);
        new RequestTransmitter().cardAccessNumber().request(getCardAccessNumberResponseListener(accessNumberResponse), ERROR_MESAGE_FOR_TEST);
        countDownLatch.await(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(testPassed[0]);
        Assert.assertNotNull(accessNumberResponse[0]);
        Assert.assertEquals(CAN, accessNumberResponse[0].getValue());
    }

    @Test
    public void requestCardAccessNumberAbort() throws InterruptedException {
        final boolean[] testPassed = new boolean[] { false };
        final CardAccessNumber[] accessNumberResponse = { null };

        eventTransferCallback = new AbstractEventTransferCallback() {
            @Override
            public void onSuccess(RequestCardAccessNumberEvent event) {
                LOG.debug("JUNIT Event " + event + " empfangen!");
                Assert.assertNotNull(event);
                Assert.assertNotNull(event.getResponseListener());
                event.getResponseListener().abortRequest();
                Assert.assertNotNull(eventTransferCallback);
                testPassed[0] = true;
                countDownLatch.countDown();

            }
        };

        Assert.assertNotNull(eventTransferCallback);
        new RequestTransmitter().cardAccessNumber().request(getCardAccessNumberResponseListener(accessNumberResponse));
        countDownLatch.await(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(testPassed[0]);
        Assert.assertNotNull(accessNumberResponse[0]);
        Assert.assertEquals(ABORT, accessNumberResponse[0].getValue());
    }

    private ICardAccessNumberResponseListener getCardAccessNumberResponseListener(final CardAccessNumber[] accessNumberResponse) {
        return new ICardAccessNumberResponseListener() {
            @Override
            public void handleCan(final CardAccessNumber cardAccessNumber) {
                accessNumberResponse[0] = cardAccessNumber;
            }

            @Override
            public void abortRequest() {
                accessNumberResponse[0] = new CardAccessNumber(ABORT);
            }
        };
    }

    // EventBus Messages Receive
    @Subscribe
    public void onReceiveCardAccessNumberEvent(RequestCardAccessNumberEvent event) {
        eventTransferCallback.onSuccess(event);
    }

    public abstract class AbstractEventTransferCallback {
        abstract void onSuccess(RequestCardAccessNumberEvent event);
    }
}

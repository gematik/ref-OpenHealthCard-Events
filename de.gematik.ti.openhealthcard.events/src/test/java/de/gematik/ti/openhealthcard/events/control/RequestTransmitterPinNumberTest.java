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

import de.gematik.ti.openhealthcard.events.request.RequestPinNumberEvent;
import de.gematik.ti.openhealthcard.events.response.callbacks.IPinNumberResponseListener;
import de.gematik.ti.openhealthcard.events.response.entities.PinNumber;

public class RequestTransmitterPinNumberTest {
    private static final Logger LOG = LoggerFactory.getLogger(RequestTransmitterPinNumberTest.class);
    private static final String VALUE = "myPin";
    private static final String ERROR_MESAGE_FOR_TEST = "ErrorMesageForTest";
    private static final String ABORT = "abort";

    private CountDownLatch paceKeyLatch;
    private AbstractEventTransferCallback pinNumberEventCallback;

    @Before
    public void setup() {
        paceKeyLatch = new CountDownLatch(1);
        EventBus.getDefault().register(this);
    }

    @Test
    public void requestPinNumber() throws InterruptedException {
        final boolean[] testPassed = new boolean[] { false };
        final PinNumber[] pinNumberResponse = { null };

        pinNumberEventCallback = new AbstractEventTransferCallback() {
            @Override
            public void onSuccess(RequestPinNumberEvent event) {
                LOG.debug("JUNIT Event " + event + " empfangen!");
                Assert.assertNotNull(event);
                Assert.assertNotNull(event.getPinType());
                Assert.assertNotNull(event.getResponseListener());
                event.getResponseListener().handlePinNumber(new PinNumber(event.getPinType(), VALUE));
                Assert.assertNotNull(pinNumberEventCallback);
                testPassed[0] = true;
                paceKeyLatch.countDown();

            }
        };

        Assert.assertNotNull(pinNumberEventCallback);
        new RequestTransmitter().pinNumber().request(getPinNumberResponseListener(pinNumberResponse), "PIN_CH");
        paceKeyLatch.await(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(testPassed[0]);
        Assert.assertNotNull(pinNumberResponse[0]);
        Assert.assertEquals(VALUE, pinNumberResponse[0].getValue());
        Assert.assertEquals("PIN_CH", pinNumberResponse[0].getPinType());
    }

    @Test
    public void requestPinNumberWithMessage() throws InterruptedException {
        final boolean[] testPassed = new boolean[] { false };
        final PinNumber[] pinNumberResponse = { null };

        pinNumberEventCallback = new AbstractEventTransferCallback() {
            @Override
            public void onSuccess(RequestPinNumberEvent event) {
                LOG.debug("JUNIT Event " + event + " empfangen!");
                Assert.assertNotNull(event);
                Assert.assertNotNull(event.getPinType());
                Assert.assertNotNull(event.getResponseListener());
                Assert.assertEquals(ERROR_MESAGE_FOR_TEST, event.getErrorMessage().get());
                event.getResponseListener().handlePinNumber(new PinNumber(event.getPinType(), VALUE));
                Assert.assertNotNull(pinNumberEventCallback);
                testPassed[0] = true;
                paceKeyLatch.countDown();

            }
        };

        Assert.assertNotNull(pinNumberEventCallback);
        new RequestTransmitter().pinNumber().request(getPinNumberResponseListener(pinNumberResponse), "PIN_CH", ERROR_MESAGE_FOR_TEST);
        paceKeyLatch.await(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(testPassed[0]);
        Assert.assertNotNull(pinNumberResponse[0]);
        Assert.assertEquals(VALUE, pinNumberResponse[0].getValue());
        Assert.assertEquals("PIN_CH", pinNumberResponse[0].getPinType());
    }

    @Test
    public void requestPinNumberAbort() throws InterruptedException {
        final boolean[] testPassed = new boolean[] { false };
        final PinNumber[] pinNumberResponse = { null };

        pinNumberEventCallback = new AbstractEventTransferCallback() {
            @Override
            public void onSuccess(RequestPinNumberEvent event) {
                LOG.debug("JUNIT Event " + event + " empfangen!");
                Assert.assertNotNull(event);
                Assert.assertNotNull(event.getPinType());
                Assert.assertNotNull(event.getResponseListener());
                event.getResponseListener().abortRequest();
                Assert.assertNotNull(pinNumberEventCallback);
                testPassed[0] = true;
                paceKeyLatch.countDown();
            }
        };

        Assert.assertNotNull(pinNumberEventCallback);
        new RequestTransmitter().pinNumber().request(getPinNumberResponseListener(pinNumberResponse), "PIN_CH");
        paceKeyLatch.await(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(testPassed[0]);
        Assert.assertNotNull(pinNumberResponse[0]);
        Assert.assertEquals(ABORT, pinNumberResponse[0].getValue());
    }

    private IPinNumberResponseListener getPinNumberResponseListener(final PinNumber[] pinNumberResponse) {
        return new IPinNumberResponseListener() {
            @Override
            public void handlePinNumber(final PinNumber pinNumber) {
                pinNumberResponse[0] = pinNumber;
            }

            @Override
            public void abortRequest() {
                pinNumberResponse[0] = new PinNumber("PIN_CH", ABORT);
            }
        };
    }

    // EventBus Messages Receive
    @Subscribe
    public void onReceivePinNumberEvent(RequestPinNumberEvent event) {
        pinNumberEventCallback.onSuccess(event);
    }

    public abstract class AbstractEventTransferCallback {
        abstract void onSuccess(RequestPinNumberEvent event);
    }
}

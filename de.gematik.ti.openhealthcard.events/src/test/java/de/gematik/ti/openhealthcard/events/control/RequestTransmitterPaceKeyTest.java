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
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gematik.ti.cardreader.provider.api.card.ICard;
import de.gematik.ti.openhealthcard.events.request.RequestPaceKeyEvent;
import de.gematik.ti.openhealthcard.events.response.callbacks.IPaceKeyResponseListener;
import de.gematik.ti.openhealthcard.events.response.entities.PaceKey;

public class RequestTransmitterPaceKeyTest {
    private static final Logger LOG = LoggerFactory.getLogger(RequestTransmitterPaceKeyTest.class);
    private static final byte[] ENC = { 0, 2, 3 };
    private static final byte[] MAC = { 5, 4, 3 };
    private static final String ERROR_MESAGE_FOR_TEST = "ErrorMesageForTest";
    private static final byte[] ABORT = { 0, 0, 0 };

    private CountDownLatch paceKeyLatch;
    private AbstractEventTransferCallback paceKeyEventCallback;

    @Before
    public void setup() {
        paceKeyLatch = new CountDownLatch(1);
        EventBus.getDefault().register(this);
    }

    @Test
    @Deprecated
    public void requestPaceKeyOld() throws InterruptedException {
        final boolean[] testPassed = new boolean[] { false };
        final PaceKey[] paceKeyResponse = { null };

        paceKeyEventCallback = new AbstractEventTransferCallback() {
            @Override
            public void onSuccess(RequestPaceKeyEvent event) {
                LOG.debug("JUNIT Event " + event + " empfangen!");
                Assert.assertNotNull(event);
                Assert.assertNotNull(event.getCard());
                Assert.assertNotNull(event.getResponseListener());
                event.getResponseListener().handlePaceKey(new PaceKey(ENC, MAC));
                Assert.assertNotNull(paceKeyEventCallback);
                testPassed[0] = true;
                paceKeyLatch.countDown();

            }
        };

        Assert.assertNotNull(paceKeyEventCallback);
        new RequestTransmitter().requestPaceKey(getPaceKeyResponseListener(paceKeyResponse), Mockito.mock(ICard.class));
        paceKeyLatch.await(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(testPassed[0]);
        Assert.assertNotNull(paceKeyResponse[0]);
        Assert.assertEquals(ENC, paceKeyResponse[0].getEnc());
        Assert.assertEquals(MAC, paceKeyResponse[0].getMac());
    }

    @Test
    public void requestPaceKey() throws InterruptedException {
        final boolean[] testPassed = new boolean[] { false };
        final PaceKey[] paceKeyResponse = { null };

        paceKeyEventCallback = new AbstractEventTransferCallback() {
            @Override
            public void onSuccess(RequestPaceKeyEvent event) {
                LOG.debug("JUNIT Event " + event + " empfangen!");
                Assert.assertNotNull(event);
                Assert.assertNotNull(event.getCard());
                Assert.assertNotNull(event.getResponseListener());
                event.getResponseListener().handlePaceKey(new PaceKey(ENC, MAC));
                Assert.assertNotNull(paceKeyEventCallback);
                testPassed[0] = true;
                paceKeyLatch.countDown();

            }
        };

        Assert.assertNotNull(paceKeyEventCallback);
        new RequestTransmitter().paceKey().request(getPaceKeyResponseListener(paceKeyResponse), Mockito.mock(ICard.class));
        paceKeyLatch.await(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(testPassed[0]);
        Assert.assertNotNull(paceKeyResponse[0]);
        Assert.assertEquals(ENC, paceKeyResponse[0].getEnc());
        Assert.assertEquals(MAC, paceKeyResponse[0].getMac());
    }

    @Test
    public void requestPaceKeyWithMessage() throws InterruptedException {
        final boolean[] testPassed = new boolean[] { false };
        final PaceKey[] paceKeyResponse = { null };

        paceKeyEventCallback = new AbstractEventTransferCallback() {
            @Override
            public void onSuccess(RequestPaceKeyEvent event) {
                LOG.debug("JUNIT Event " + event + " empfangen!");
                Assert.assertNotNull(event);
                Assert.assertNotNull(event.getCard());
                Assert.assertNotNull(event.getResponseListener());
                Assert.assertEquals(ERROR_MESAGE_FOR_TEST, event.getErrorMessage().get());
                event.getResponseListener().handlePaceKey(new PaceKey(ENC, MAC));
                Assert.assertNotNull(paceKeyEventCallback);
                testPassed[0] = true;
                paceKeyLatch.countDown();

            }
        };

        Assert.assertNotNull(paceKeyEventCallback);
        new RequestTransmitter().paceKey().request(getPaceKeyResponseListener(paceKeyResponse), Mockito.mock(ICard.class), ERROR_MESAGE_FOR_TEST);
        paceKeyLatch.await(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(testPassed[0]);
        Assert.assertNotNull(paceKeyResponse[0]);
        Assert.assertEquals(ENC, paceKeyResponse[0].getEnc());
        Assert.assertEquals(MAC, paceKeyResponse[0].getMac());
    }

    @Test
    public void requestPaceKeyAbort() throws InterruptedException {
        final boolean[] testPassed = new boolean[] { false };
        final PaceKey[] paceKeyResponse = { null };

        paceKeyEventCallback = new AbstractEventTransferCallback() {
            @Override
            public void onSuccess(RequestPaceKeyEvent event) {
                LOG.debug("JUNIT Event " + event + " empfangen!");
                Assert.assertNotNull(event);
                Assert.assertNotNull(event.getCard());
                Assert.assertNotNull(event.getResponseListener());
                event.getResponseListener().abortRequest();
                Assert.assertNotNull(paceKeyEventCallback);
                testPassed[0] = true;
                paceKeyLatch.countDown();

            }
        };

        Assert.assertNotNull(paceKeyEventCallback);
        new RequestTransmitter().paceKey().request(getPaceKeyResponseListener(paceKeyResponse), Mockito.mock(ICard.class));
        paceKeyLatch.await(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(testPassed[0]);
        Assert.assertNotNull(paceKeyResponse[0]);
        Assert.assertEquals(ABORT, paceKeyResponse[0].getEnc());
        Assert.assertEquals(ABORT, paceKeyResponse[0].getMac());
    }

    private IPaceKeyResponseListener getPaceKeyResponseListener(final PaceKey[] paceKeyResponse) {
        return new IPaceKeyResponseListener() {
            @Override
            public void handlePaceKey(final PaceKey paceKey) {
                paceKeyResponse[0] = paceKey;
            }

            @Override
            public void abortRequest() {
                paceKeyResponse[0] = new PaceKey(ABORT, ABORT);
            }
        };
    }

    // EventBus Messages Receive
    @Subscribe
    public void onReceivePaceKeyEvent(RequestPaceKeyEvent event) {
        paceKeyEventCallback.onSuccess(event);
    }

    public abstract class AbstractEventTransferCallback {
        abstract void onSuccess(RequestPaceKeyEvent event);
    }
}

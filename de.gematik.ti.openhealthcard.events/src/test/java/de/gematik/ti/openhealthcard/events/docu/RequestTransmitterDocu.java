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

package de.gematik.ti.openhealthcard.events.docu;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.junit.Test;
import org.mockito.Mockito;

import de.gematik.ti.cardreader.provider.api.card.ICard;
import de.gematik.ti.openhealthcard.events.control.CommonEventTransmitter;
import de.gematik.ti.openhealthcard.events.control.RequestTransmitter;
import de.gematik.ti.openhealthcard.events.message.AbstractOpenHealthCardEvent;
import de.gematik.ti.openhealthcard.events.message.ErrorEvent;
import de.gematik.ti.openhealthcard.events.request.RequestCardAccessNumberEvent;
import de.gematik.ti.openhealthcard.events.request.RequestPaceKeyEvent;
import de.gematik.ti.openhealthcard.events.request.RequestPinNumberEvent;
import de.gematik.ti.openhealthcard.events.response.callbacks.ICardAccessNumberResponseListener;
import de.gematik.ti.openhealthcard.events.response.callbacks.IPaceKeyResponseListener;
import de.gematik.ti.openhealthcard.events.response.callbacks.IPinNumberResponseListener;
import de.gematik.ti.openhealthcard.events.response.entities.CardAccessNumber;
import de.gematik.ti.openhealthcard.events.response.entities.PaceKey;
import de.gematik.ti.openhealthcard.events.response.entities.PinNumber;

public class RequestTransmitterDocu {

    // tag::HandleErrorEvent[]
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleMessagesFromEventBus(final AbstractOpenHealthCardEvent event) {
        if (event instanceof ErrorEvent) {
            LOG.error(event.getSourceClass() + ": " + event.getMessage());
        } else {
            LOG.info(event.getSourceClass() + ": " + event.getMessage());
        }
        Toast.makeText(this, event.getSourceClass() + ": " + event.getMessage(), Toast.LENGTH_LONG).show();
    }
    // end::HandleErrorEvent[]

    @Test
    public void testSendError() {
        RuntimeException someException = new RuntimeException();
        String myMessage = "MeineNachricht";
        // tag::ErrorEvent[]
        CommonEventTransmitter.postError(someException, myMessage);
        // end::ErrorEvent[]
    }

    @Test
    public void testRequestTransmitterCardAccessNumber() {
        // tag::RequestTransmitterCardAccessNumber[]
        ICardAccessNumberResponseListener cardAccessNumberResponseListener = new ICardAccessNumberResponseListener() {
            @Override
            public void handleCan(final CardAccessNumber cardAccessNumber) {
                // Response if the User Input is finished
            }
        };

        new RequestTransmitter().cardAccessNumber().request(cardAccessNumberResponseListener);
        // end::RequestTransmitterCardAccessNumber[]

        final String someMessageToUser = "";
        // tag::RequestTransmitterCardAccessNumberMsg[]
        new RequestTransmitter().cardAccessNumber().request(cardAccessNumberResponseListener, someMessageToUser);
        // end::RequestTransmitterCardAccessNumberMsg[]
    }

    // tag::ReceiveTransmitterCardAccessNumber[]
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestCan(final RequestCardAccessNumberEvent event) {
        CardAccessNumber can = null;
        // Do some user interaction to get the card access number
        event.getResponseListener().handleCan(can);
    }
    // end::ReceiveTransmitterCardAccessNumber[]

    // PACE KEY

    @Test
    public void testRequestTransmitterPaceKey() {
        ICard cardFromCardReader = Mockito.mock(ICard.class);
        // tag::RequestTransmitterPaceKey[]
        IPaceKeyResponseListener paceKeyResponseListener = new IPaceKeyResponseListener() {
            @Override
            public void handlePaceKey(final PaceKey paceKey) {
                // Do something with paceKey
            }
        };

        new RequestTransmitter().paceKey().request(paceKeyResponseListener, cardFromCardReader);
        // end::RequestTransmitterPaceKey[]

        final String someMessageToUser = "";
        // tag::RequestTransmitterPaceKeyMsg[]
        new RequestTransmitter().paceKey().request(paceKeyResponseListener, cardFromCardReader, someMessageToUser);
        // end::RequestTransmitterPaceKeyMsg[]
    }

    // tag::ReceiveTransmitterPaceKey[]
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestPaceKey(final RequestPaceKeyEvent event) {
        PaceKey paceKey = null;
        // Do some user interaction to get the card access number
        event.getResponseListener().handlePaceKey(paceKey);
    }
    // end::ReceiveTransmitterPaceKey[]

    // Pin Number

    @Test
    public void testRequestTransmitterPinNumber() {
        String pinType = "PIN_CH";
        // tag::RequestTransmitterPinNumber[]
        IPinNumberResponseListener pinNumberResponseListener = new IPinNumberResponseListener() {
            @Override
            public void handlePinNumber(final PinNumber pinNumber) {
                // Response if the User Input is finished
            }
        };

        new RequestTransmitter().pinNumber().request(pinNumberResponseListener, pinType);
        // end::RequestTransmitterPinNumber[]

        final String someMessageToUser = "";

        // tag::RequestTransmitterPinNumberMsg[]
        new RequestTransmitter().pinNumber().request(pinNumberResponseListener, pinType, someMessageToUser);
        // end::RequestTransmitterPinNumberMsg[]
    }

    // tag::ReceiveTransmitterPinNumber[]
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestPin(final RequestPinNumberEvent event) {
        PinNumber pinNumber = null;
        // Do some user interaction to get the card access number
        event.getResponseListener().handlePinNumber(pinNumber);
    }
    // end::ReceiveTransmitterPinNumber[]

    // HELPER

    private static class Toast {
        public static final Object LENGTH_LONG = 0;

        public static IDummy makeText(final RequestTransmitterDocu requestTransmitterDocu, final String s, final Object lengthLong) {
            return null;
        }

        public static interface IDummy {
            void show();
        }
    }

    private static class LOG {
        public static void error(final String s) {
        }

        public static void info(final String s) {
        }
    }
}

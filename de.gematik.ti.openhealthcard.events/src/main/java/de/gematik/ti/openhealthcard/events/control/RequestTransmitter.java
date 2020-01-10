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

import org.greenrobot.eventbus.EventBus;

import de.gematik.ti.cardreader.provider.api.card.ICard;
import de.gematik.ti.openhealthcard.events.request.RequestCardAccessNumberEvent;
import de.gematik.ti.openhealthcard.events.request.RequestPaceKeyEvent;
import de.gematik.ti.openhealthcard.events.response.callbacks.ICardAccessNumberResponseListener;
import de.gematik.ti.openhealthcard.events.response.callbacks.IPaceKeyResponseListener;

/**
 * Transmitter to capsule message transmitting
 * 
 * @author christian.lange
 */
public class RequestTransmitter {

    /**
     * Send Message to get a card access number with callback
     * @param canResponseListener response callback object
     * @deprecated use cardAccessNumber().request(...)
     */
    @Deprecated
    public void requestCardAccessNumber(final ICardAccessNumberResponseListener canResponseListener) {
        EventBus.getDefault().post(new RequestCardAccessNumberEvent(canResponseListener));
    }

    /**
     * Send Message to get a card access number
     * @return transmitter for cardAccessNumbers
     */
    public RequestTransmitterCardAccessNumber cardAccessNumber() {
        return new RequestTransmitterCardAccessNumber();
    }

    /**
     * Send Message to get a pace key with callback
     * @param paceKeyResponseListener response callback object
     * @param card card for key negotiation
     * @deprecated  use paceKey().request(...)
     */
    @Deprecated
    public void requestPaceKey(final IPaceKeyResponseListener paceKeyResponseListener, final ICard card) {
        EventBus.getDefault().post(new RequestPaceKeyEvent(paceKeyResponseListener, card));
    }

    /**
     * Send Message to get a pace key
     * @return transmitter for cardAccessNumbers
     */
    public RequestTransmitterPaceKey paceKey() {
        return new RequestTransmitterPaceKey();
    }

    /**
     * Send Message to get a pin number
     * @return transmitter for cardAccessNumbers
     */
    public RequestTransmitterPinNumber pinNumber() {
        return new RequestTransmitterPinNumber();
    }

}

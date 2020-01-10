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
import de.gematik.ti.openhealthcard.events.request.RequestPaceKeyEvent;
import de.gematik.ti.openhealthcard.events.response.callbacks.IPaceKeyResponseListener;

/**
 * Transmitter to capsule message transmitting
 * 
 * @author christian.lange
 */
public class RequestTransmitterPaceKey {

    RequestTransmitterPaceKey() {
        // Nothing
    }

    /**
     * Send Message to get a pace key with callback
     * @param paceKeyResponseListener response callback object
     * @param card card for key negotiation
     */
    public void request(final IPaceKeyResponseListener paceKeyResponseListener, final ICard card) {
        EventBus.getDefault().post(new RequestPaceKeyEvent(paceKeyResponseListener, card));
    }

    /**
     * Send Message to get a pace key with callback
     * @param paceKeyResponseListener response callback object
     * @param card card for key negotiation
     * @param errorMessage message to the receiver if last request fails
     */
    public void request(final IPaceKeyResponseListener paceKeyResponseListener, final ICard card, final String errorMessage) {
        EventBus.getDefault().post(new RequestPaceKeyEvent(paceKeyResponseListener, card, errorMessage));
    }

}

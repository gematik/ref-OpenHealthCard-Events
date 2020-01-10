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

import de.gematik.ti.openhealthcard.events.request.RequestPinNumberEvent;
import de.gematik.ti.openhealthcard.events.response.callbacks.IPinNumberResponseListener;

/**
 * Transmitter to capsule message transmitting for Pin requests
 * 
 * @author christian.lange
 */
public class RequestTransmitterPinNumber {

    public RequestTransmitterPinNumber() {
        // Nothing
    }

    /**
     * Send Message to get a pace key with callback
     * @param pinNumberResponseListener response callback object
     * @param pinType type of requested Pin
     */
    public void request(final IPinNumberResponseListener pinNumberResponseListener, final String pinType) {
        EventBus.getDefault().post(new RequestPinNumberEvent(pinNumberResponseListener, pinType));
    }

    /**
     * Send Message to get a pace key with callback
     * @param pinNumberResponseListener response callback object
     * @param pinType type of requested Pin
     * @param errorMessage message to the receiver if last request fails
     */
    public void request(final IPinNumberResponseListener pinNumberResponseListener, final String pinType, final String errorMessage) {
        EventBus.getDefault().post(new RequestPinNumberEvent(pinNumberResponseListener, pinType, errorMessage));
    }

}

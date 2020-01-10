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

package de.gematik.ti.openhealthcard.events.request;

import de.gematik.ti.openhealthcard.events.response.callbacks.IPinNumberResponseListener;

/**
 * Event to request pin
 *
 * @author christian.lange
 */
public class RequestPinNumberEvent extends AbstractRequestEvent {

    private final String pinType;

    /**
     * Request a specific Pin from receiver
     * @param pinResponseListener - response after pin call
     * @param pinType - type of requested Pin
     */
    public RequestPinNumberEvent(final IPinNumberResponseListener pinResponseListener, final String pinType) {
        super(pinResponseListener);
        this.pinType = pinType;
    }

    /**
     * Request a specific Pin from receiver
     * @param pinResponseListener - response after pin call
     * @param pinType - type of requested Pin
     * qparam errorMessage - message for the receiver about previous request fails
     */
    public RequestPinNumberEvent(final IPinNumberResponseListener pinResponseListener, final String pinType, String errorMessage) {
        super(pinResponseListener);
        this.pinType = pinType;
        setErrorMessage(errorMessage);
    }

    /**
     * Type of requested pin
     * @return pinType
     */
    public String getPinType() {
        return pinType;
    }

    /**
     * Callback for the response
     * @return callback listener
     */
    @Override
    public IPinNumberResponseListener getResponseListener() {
        return (IPinNumberResponseListener) super.getResponseListener();
    }
}

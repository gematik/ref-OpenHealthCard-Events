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

import java.util.Optional;

import de.gematik.ti.openhealthcard.events.response.callbacks.IGeneralResponseListener;

/**
 * General Request Information's
 */
public abstract class AbstractRequestEvent {

    private Optional<String> errorMessage = Optional.empty();
    private final IGeneralResponseListener responseListener;

    protected AbstractRequestEvent(final IGeneralResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    /**
     * set an optional error message if last request failed
     * @param msg
     */
    public void setErrorMessage(final String msg) {
        errorMessage = Optional.of(msg);
    }

    /**
     * Optional Message if the last request failed
     *
     * @return optional message
     */
    public Optional<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * Callback for the response
     * @return callback listener
     */
    public IGeneralResponseListener getResponseListener() {
        return responseListener;
    }
}

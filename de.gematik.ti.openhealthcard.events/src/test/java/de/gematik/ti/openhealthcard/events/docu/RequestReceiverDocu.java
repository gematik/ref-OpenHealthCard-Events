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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import de.gematik.ti.openhealthcard.events.request.RequestCardAccessNumberEvent;
import de.gematik.ti.openhealthcard.events.request.RequestPaceKeyEvent;
import de.gematik.ti.openhealthcard.events.response.entities.CardAccessNumber;
import de.gematik.ti.openhealthcard.events.response.entities.PaceKey;

public class RequestReceiverDocu {

    // tag::Register[]
    public void registerThisObjectAsReveicer() {
        EventBus.getDefault().register(this);
    }

    // end::Register[]

    // tag::onReceiveCardAccessNumberEvent[]
    // EventBus Messages Receive
    @Subscribe
    public void onReceiveCardAccessNumberEvent(final RequestCardAccessNumberEvent event) {

        CardAccessNumber cardAccessNumber = null;
        // Do something to get the CardAccessNumber
        // ...

        // Send Response
        event.getResponseListener().handleCan(cardAccessNumber);
    }
    // end::onReceiveCardAccessNumberEvent[]

    // tag::onReceivePaceKeyEvent[]
    // EventBus Messages Receive
    @Subscribe
    public void onReceivePaceKeyEvent(final RequestPaceKeyEvent event) {

        PaceKey paceKey = null;
        // Do something to get the PaceKey
        // ...

        // Send Response
        event.getResponseListener().handlePaceKey(paceKey);
    }
    // end::onReceivePaceKeyEvent[]

}

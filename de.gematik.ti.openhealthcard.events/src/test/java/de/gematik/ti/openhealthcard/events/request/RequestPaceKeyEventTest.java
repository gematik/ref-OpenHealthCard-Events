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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import de.gematik.ti.cardreader.provider.api.card.ICard;
import de.gematik.ti.openhealthcard.events.response.callbacks.IPaceKeyResponseListener;

public class RequestPaceKeyEventTest {

    @Test
    public void testEventObject() {
        IPaceKeyResponseListener listener = Mockito.mock(IPaceKeyResponseListener.class);
        ICard card = Mockito.mock(ICard.class);
        RequestPaceKeyEvent event = new RequestPaceKeyEvent(listener, card);
        Assert.assertEquals(card, event.getCard());
        Assert.assertEquals(listener, event.getResponseListener());
    }
}

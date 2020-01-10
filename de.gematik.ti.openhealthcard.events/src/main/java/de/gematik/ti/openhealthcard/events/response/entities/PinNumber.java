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

package de.gematik.ti.openhealthcard.events.response.entities;

/**
 * Pin for a specific card and Type
 *
 * @author christian.lange
 * @version 1.0
 */
public class PinNumber {
    private final String value;
    private final String pinType;

    /**
     * create a instance with CardAccessNumber
     * @param value CardAccessNumber
     */
    public PinNumber(final String pinType, final String value) {
        this.value = value;
        this.pinType = pinType;
    }

    /**
     * get CardAccessNumber as String
     * @return CardAccessNumber
     */
    public String getValue() {
        return value;
    }

    /**
     * get PinType
     * @return PinType
     */
    public String getPinType() {
        return pinType;
    }
}

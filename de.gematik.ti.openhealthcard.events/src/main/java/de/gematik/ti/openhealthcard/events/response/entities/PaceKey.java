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
 * Pace Key for TrustedChannel with Session key for encoding and Session key for message authentication
 *
 * @author christian.lange
 * @version 1.0
 */
public class PaceKey {

    private final byte[] enc;
    private final byte[] mac;

    /**
     * Create a instance of PaceKey
     * @param enc - byte array for Session key for encoding
     * @param mac - byte array for Session key for message authentication
     */
    public PaceKey(final byte[] enc, final byte[] mac) {
        this.enc = enc;
        this.mac = mac;
    }

    /**
     * get Value of Session key for encoding
     * @return byte array of enc
     */
    public byte[] getEnc() {
        return enc;
    }

    /**
     * get Value of Session key for message authentication
     * @return byte array of mac
     */
    public byte[] getMac() {
        return mac;
    }

}

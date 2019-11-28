/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.highmobility.autoapi;

import com.highmobility.autoapi.HomeChargerState.AuthenticationState;
import com.highmobility.autoapi.property.Property;

/**
 * Authenticate expire
 */
public class AuthenticateExpire extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.HOME_CHARGER;

    public static final byte IDENTIFIER_AUTHENTICATION_STATE = 0x0d;

    Property<AuthenticationState> authenticationState = new Property(AuthenticationState.class, IDENTIFIER_AUTHENTICATION_STATE);

    /**
     * @return The authentication state
     */
    public Property<AuthenticationState> getAuthenticationState() {
        return authenticationState;
    }
    
    /**
     * Authenticate expire
     *
     * @param authenticationState The authentication state
     */
    public AuthenticateExpire(AuthenticationState authenticationState) {
        super(IDENTIFIER);
    
        addProperty(this.authenticationState.update(authenticationState));
        createBytes();
    }

    AuthenticateExpire(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_AUTHENTICATION_STATE: return authenticationState.update(p);
                }
                return null;
            });
        }
        if (this.authenticationState.getValue() == null) 
            throw new NoPropertiesException();
    }
}
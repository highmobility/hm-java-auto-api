/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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

import com.highmobility.autoapi.property.PropertyInteger;

/**
 * Action
 */
public class Action extends SetCommand {
    public static final Identifier identifier = Identifier.NOTIFICATIONS;

    PropertyInteger activatedAction = new PropertyInteger(0x03, false);

    /**
     * @return The activated action
     */
    public PropertyInteger getActivatedAction() {
        return activatedAction;
    }
    
    /**
     * Action
     *
     * @param activatedAction Identifier of the activated action
     */
    public Action(Integer activatedAction) {
        super(identifier);
    
        addProperty(this.activatedAction.update(false, 1, activatedAction), true);
    }

    Action(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x03: return activatedAction.update(p);
                }
                return null;
            });
        }
        if (this.activatedAction.getValue() == null) 
            throw new NoPropertiesException();
    }
}
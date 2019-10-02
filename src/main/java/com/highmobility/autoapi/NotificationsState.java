// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.value.ActionItem;
import com.highmobility.autoapi.property.PropertyInteger;
import java.util.ArrayList;
import java.util.List;

public class NotificationsState extends SetCommand {
    Property<String> text = new Property(String.class, 0x01);
    Property<ActionItem>[] actionItems;
    PropertyInteger activatedAction = new PropertyInteger(0x03, false);
    Property<Clear> clear = new Property(Clear.class, 0x04);

    /**
     * @return Text for the notification
     */
    public Property<String> getText() {
        return text;
    }

    /**
     * @return The action items
     */
    public Property<ActionItem>[] getActionItems() {
        return actionItems;
    }

    /**
     * @return Identifier of the activated action
     */
    public PropertyInteger getActivatedAction() {
        return activatedAction;
    }

    /**
     * @return The clear
     */
    public Property<Clear> getClear() {
        return clear;
    }

    NotificationsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> actionItemsBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return text.update(p);
                    case 0x02:
                        Property<ActionItem> actionItem = new Property(ActionItem.class, p);
                        actionItemsBuilder.add(actionItem);
                        return actionItem;
                    case 0x03: return activatedAction.update(p);
                    case 0x04: return clear.update(p);
                }

                return null;
            });
        }

        actionItems = actionItemsBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private NotificationsState(Builder builder) {
        super(builder);

        text = builder.text;
        actionItems = builder.actionItems.toArray(new Property[0]);
        activatedAction = builder.activatedAction;
        clear = builder.clear;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<String> text;
        private List<Property> actionItems = new ArrayList<>();
        private PropertyInteger activatedAction;
        private Property<Clear> clear;

        public Builder() {
            super(Identifier.NOTIFICATIONS);
        }

        public NotificationsState build() {
            return new NotificationsState(this);
        }

        /**
         * @param text Text for the notification
         * @return The builder
         */
        public Builder setText(Property<String> text) {
            this.text = text.setIdentifier(0x01);
            addProperty(this.text);
            return this;
        }
        
        /**
         * Add an array of action items.
         * 
         * @param actionItems The action items
         * @return The builder
         */
        public Builder setActionItems(Property<ActionItem>[] actionItems) {
            this.actionItems.clear();
            for (int i = 0; i < actionItems.length; i++) {
                addActionItem(actionItems[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single action item.
         * 
         * @param actionItem The action item
         * @return The builder
         */
        public Builder addActionItem(Property<ActionItem> actionItem) {
            actionItem.setIdentifier(0x02);
            addProperty(actionItem);
            actionItems.add(actionItem);
            return this;
        }
        
        /**
         * @param activatedAction Identifier of the activated action
         * @return The builder
         */
        public Builder setActivatedAction(Property<Integer> activatedAction) {
            this.activatedAction = new PropertyInteger(0x03, false, 1, activatedAction);
            addProperty(this.activatedAction);
            return this;
        }
        
        /**
         * @param clear The clear
         * @return The builder
         */
        public Builder setClear(Property<Clear> clear) {
            this.clear = clear.setIdentifier(0x04);
            addProperty(this.clear);
            return this;
        }
    }

    public enum Clear implements ByteEnum {
        CLEAR((byte) 0x00);
    
        public static Clear fromByte(byte byteValue) throws CommandParseException {
            Clear[] values = Clear.values();
    
            for (int i = 0; i < values.length; i++) {
                Clear state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Clear(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}
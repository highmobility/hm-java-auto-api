// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

public class LightConditionsState extends SetCommand {
    Property<Float> outsideLight = new Property(Float.class, 0x01);
    Property<Float> insideLight = new Property(Float.class, 0x02);

    /**
     * @return Measured outside illuminance in lux
     */
    public Property<Float> getOutsideLight() {
        return outsideLight;
    }

    /**
     * @return Measured inside illuminance in lux
     */
    public Property<Float> getInsideLight() {
        return insideLight;
    }

    LightConditionsState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return outsideLight.update(p);
                    case 0x02: return insideLight.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private LightConditionsState(Builder builder) {
        super(builder);

        outsideLight = builder.outsideLight;
        insideLight = builder.insideLight;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<Float> outsideLight;
        private Property<Float> insideLight;

        public Builder() {
            super(Identifier.LIGHT_CONDITIONS);
        }

        public LightConditionsState build() {
            return new LightConditionsState(this);
        }

        /**
         * @param outsideLight Measured outside illuminance in lux
         * @return The builder
         */
        public Builder setOutsideLight(Property<Float> outsideLight) {
            this.outsideLight = outsideLight.setIdentifier(0x01);
            addProperty(this.outsideLight);
            return this;
        }
        
        /**
         * @param insideLight Measured inside illuminance in lux
         * @return The builder
         */
        public Builder setInsideLight(Property<Float> insideLight) {
            this.insideLight = insideLight.setIdentifier(0x02);
            addProperty(this.insideLight);
            return this;
        }
    }
}
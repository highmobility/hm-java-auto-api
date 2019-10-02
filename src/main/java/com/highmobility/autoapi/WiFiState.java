// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.EnabledState;
import com.highmobility.autoapi.value.ConnectionState;
import com.highmobility.autoapi.value.NetworkSecurity;

public class WiFiState extends SetCommand {
    Property<EnabledState> status = new Property(EnabledState.class, 0x01);
    Property<ConnectionState> networkConnection = new Property(ConnectionState.class, 0x02);
    Property<String> networkSSID = new Property(String.class, 0x03);
    Property<NetworkSecurity> networkSecurity = new Property(NetworkSecurity.class, 0x04);

    /**
     * @return The status
     */
    public Property<EnabledState> getStatus() {
        return status;
    }

    /**
     * @return The network connected
     */
    public Property<ConnectionState> getNetworkConnection() {
        return networkConnection;
    }

    /**
     * @return The network SSID
     */
    public Property<String> getNetworkSSID() {
        return networkSSID;
    }

    /**
     * @return The network security
     */
    public Property<NetworkSecurity> getNetworkSecurity() {
        return networkSecurity;
    }

    WiFiState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return status.update(p);
                    case 0x02: return networkConnection.update(p);
                    case 0x03: return networkSSID.update(p);
                    case 0x04: return networkSecurity.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private WiFiState(Builder builder) {
        super(builder);

        status = builder.status;
        networkConnection = builder.networkConnection;
        networkSSID = builder.networkSSID;
        networkSecurity = builder.networkSecurity;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<EnabledState> status;
        private Property<ConnectionState> networkConnection;
        private Property<String> networkSSID;
        private Property<NetworkSecurity> networkSecurity;

        public Builder() {
            super(Identifier.WI_FI);
        }

        public WiFiState build() {
            return new WiFiState(this);
        }

        /**
         * @param status The status
         * @return The builder
         */
        public Builder setStatus(Property<EnabledState> status) {
            this.status = status.setIdentifier(0x01);
            addProperty(this.status);
            return this;
        }
        
        /**
         * @param networkConnection The network connected
         * @return The builder
         */
        public Builder setNetworkConnection(Property<ConnectionState> networkConnection) {
            this.networkConnection = networkConnection.setIdentifier(0x02);
            addProperty(this.networkConnection);
            return this;
        }
        
        /**
         * @param networkSSID The network SSID
         * @return The builder
         */
        public Builder setNetworkSSID(Property<String> networkSSID) {
            this.networkSSID = networkSSID.setIdentifier(0x03);
            addProperty(this.networkSSID);
            return this;
        }
        
        /**
         * @param networkSecurity The network security
         * @return The builder
         */
        public Builder setNetworkSecurity(Property<NetworkSecurity> networkSecurity) {
            this.networkSecurity = networkSecurity.setIdentifier(0x04);
            addProperty(this.networkSecurity);
            return this;
        }
    }
}
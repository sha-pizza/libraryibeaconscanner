package com.example.bleindoorpositioning.ble.advertising;

public class IndoorPositioningAdvertisingPacketFactory extends IBeaconAdvertisingPacketFactory {

    public IndoorPositioningAdvertisingPacketFactory() {
        this(IndoorPositioningAdvertisingPacket.class);
    }

    <AP extends AdvertisingPacket> IndoorPositioningAdvertisingPacketFactory(Class<AP> packetClass) {
        super(packetClass);
    }

    @Override
    public boolean canCreateAdvertisingPacket(byte[] advertisingData) {
        return IndoorPositioningAdvertisingPacket.meetsSpecification(advertisingData);
    }

    @Override
    public AdvertisingPacket createAdvertisingPacket(byte[] advertisingData) {
        return new IndoorPositioningAdvertisingPacket(advertisingData);
    }

}
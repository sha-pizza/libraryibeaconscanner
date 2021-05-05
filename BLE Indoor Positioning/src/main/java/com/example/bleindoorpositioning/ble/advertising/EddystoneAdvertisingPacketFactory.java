package com.example.bleindoorpositioning.ble.advertising;

public class EddystoneAdvertisingPacketFactory extends AdvertisingPacketFactory {

    public EddystoneAdvertisingPacketFactory() {
        this(EddystoneAdvertisingPacket.class);
    }

    public <AP extends AdvertisingPacket> EddystoneAdvertisingPacketFactory(Class<AP> packetClass) {
        super(packetClass);
    }

    @Override
    public boolean canCreateAdvertisingPacket(byte[] advertisingData) {
        return EddystoneAdvertisingPacket.meetsSpecification(advertisingData);
    }

    @Override
    public AdvertisingPacket createAdvertisingPacket(byte[] advertisingData) {
        return new EddystoneAdvertisingPacket(advertisingData);
    }

}

package com.example.bleindoorpositioning.ble.beacon.signal;

import com.example.bleindoorpositioning.ble.advertising.AdvertisingPacket;
import com.example.bleindoorpositioning.ble.advertising.AdvertisingPacketUtil;
import com.example.bleindoorpositioning.ble.beacon.Beacon;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MeanFilter extends WindowFilter {

    public MeanFilter() {
    }

    public MeanFilter(long duration, TimeUnit timeUnit) {
        super(duration, timeUnit);
    }

    public MeanFilter(long maximumTimestamp) {
        super(maximumTimestamp);
    }

    public MeanFilter(long duration, TimeUnit timeUnit, long maximumTimestamp) {
        super(duration, timeUnit, maximumTimestamp);
    }

    @Override
    public float filter(Beacon beacon) {
        List<AdvertisingPacket> advertisingPackets = getRecentAdvertisingPackets(beacon);
        int[] rssiArray = AdvertisingPacketUtil.getRssisFromAdvertisingPackets(advertisingPackets);
        return AdvertisingPacketUtil.calculateMean(rssiArray);
    }

}
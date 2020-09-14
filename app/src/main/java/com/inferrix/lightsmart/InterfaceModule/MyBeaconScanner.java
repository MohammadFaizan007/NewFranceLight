package com.inferrix.lightsmart.InterfaceModule;


import com.inferrix.lightsmart.PogoClasses.BeconDeviceClass;

import java.util.ArrayList;

public interface MyBeaconScanner {
    void onBeaconFound(ArrayList<BeconDeviceClass> byteQueue);
    void noBeaconFound();
}

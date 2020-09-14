package com.inferrix.lightsmart.InterfaceModule;


import com.inferrix.lightsmart.EncodeDecodeModule.ByteQueue;

public interface ReceiverResultInterface {

    void onScanSuccess(int successCode, ByteQueue byteQueue);
    void onScanFailed(int errorCode);


}

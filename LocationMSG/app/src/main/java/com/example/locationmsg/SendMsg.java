package com.example.locationmsg;

import android.telephony.SmsManager;
import android.widget.TextView;

/**
 * Created by ÐÇ³Û on 2015/7/19.
 */
public class SendMsg {
    String number;
    SmsManager smsManager;
    public SendMsg(String number){
        this.number=number;
        smsManager = SmsManager.getDefault();
    }
    public void Send(String msg){

        smsManager.sendTextMessage(number, null, msg, null, null);

    }
}

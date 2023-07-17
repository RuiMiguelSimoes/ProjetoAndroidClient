package com.example.projeto;

import android.nfc.Tag;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.nio.charset.StandardCharsets;


public class HostCardEmulatorService extends HostApduService {

    FirebaseUser user;
    FirebaseAuth auth;

    private static final String TAG = "HCE";
    private static final String STATUS_SUCCESS = "9000";
    private static final String STATUS_FAILED = "6F00";
    private static final String CLA_NOT_SUPPORTED = "6E00";
    private static final String INS_NOT_SUPPORTED = "6D00";
    private static final String AID = "A0000002471001";
    private static final String SELECT_INS = "A4";
    private static final String DEFAULT_CLA = "00";
    private static final int MIN_APDU_LENGTH = 12;
    private static final String P1 = "04";
    private static final String P2 = "00";

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        if (commandApdu == null) {
            return Utils.hexStringToByteArray(STATUS_FAILED);
        }

        String hexCommandApdu = Utils.toHex(commandApdu);

        Log.i(TAG, hexCommandApdu + "   AID of this app" + AID);

        if (hexCommandApdu.length() < MIN_APDU_LENGTH) {
            return Utils.hexStringToByteArray(STATUS_FAILED);
        }

        if (!hexCommandApdu.substring(0, 2).equals(DEFAULT_CLA)) {
            Log.i(TAG, "Cla Not Supported");
            return Utils.hexStringToByteArray(CLA_NOT_SUPPORTED);
        }

        if (!hexCommandApdu.substring(2, 4).equals(SELECT_INS)) {
            Log.i(TAG, "Not Supported");
            return Utils.hexStringToByteArray(INS_NOT_SUPPORTED);
        }
/*
        if (hexCommandApdu.substring(4, 6).equals(P1) && hexCommandApdu.substring(6, 8).equals(P2)) {

            Log.i(TAG, "data");
            String ret = "123";
            ret += STATUS_SUCCESS;
            byte[] b = Utils.hexStringToByteArray(ret);
            Log.i(TAG, "Sucessinho Data" + b.length);
            return b;


        }
        */


        if (hexCommandApdu.substring(10, 24).equals(AID)) {

            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();

            String userid = user.getUid();
            String ret = userid + STATUS_SUCCESS;
            byte[] byteArray = ret.getBytes(StandardCharsets.US_ASCII);

            Log.i(TAG, "Sucessinho " + userid);
            return byteArray;
        } else {
            Log.i(TAG, "OOPS");
            return Utils.hexStringToByteArray(STATUS_FAILED);
        }
    }

    @Override
    public void onDeactivated(int reason) {
        Log.d(TAG, "Deactivated: " + reason);
    }

}

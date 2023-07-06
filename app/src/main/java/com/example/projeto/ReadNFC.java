package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class ReadNFC extends AppCompatActivity implements NfcAdapter.ReaderCallback {
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        setContentView(R.layout.activity_read_nfc);


    }

    @Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableReaderMode(this, this,
                NfcAdapter.FLAG_READER_NFC_A |
                        NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
                null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        nfcAdapter.disableReaderMode(this);
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        byte[] hexString = UtilsRead.hexStringToByteArray("00A4040007A0000002471001");

        new Thread(new Runnable() {
            @Override
            public void run() {
                IsoDep isoDep = IsoDep.get(tag);
                Log.i("XR", "tag" + tag.toString());
                try {
                    isoDep.connect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // after connecting we send the select applet aid
                byte[] response = new byte[0];
                try {
                    response = isoDep.transceive(hexString);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String theResponse = UtilsRead.toHex(response);
                System.out.println(theResponse);

                Log.i("XR", theResponse);
                try {
                    isoDep.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                TextView textView = findViewById(R.id.textView);
                // try to touch View of UI thread
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        textView.append("\n" + "resposta: " + theResponse);
                        /*
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intent);

                         */
                    }

                });
            }
        }).start();
    }
}
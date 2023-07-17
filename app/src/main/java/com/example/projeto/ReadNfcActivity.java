package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ReadNfcActivity extends AppCompatActivity implements NfcAdapter.ReaderCallback {
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        setContentView(R.layout.activity_read_nfc);
        TextView textView = findViewById(R.id.textView);
        textView.append("\nCard Responses here\n");
    }
    /*
    @Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableReaderMode(this, this,
                NfcAdapter.FLAG_READER_NFC_A |
                        NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK, null);
    }
*/
    @Override
    protected void onPause() {
        super.onPause();
        nfcAdapter.disableReaderMode(this);
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        final byte[] hexString = Utils.hexStringToByteArray("00A4040007A0000002471001");

        new Thread(new Runnable() {
            @Override
            public void run() {
                IsoDep isoDep = IsoDep.get(tag);
                Log.i("XR", "tag" + tag.toString());
                try {
                    isoDep.connect();
                    // After connecting, we send the select applet AID
                    byte[] response = isoDep.transceive(hexString);
                    String theResponse = new String(response, "US-ASCII");
                    Log.i("XR", theResponse);

                    String status = theResponse.substring(theResponse.length() - 4);
                    String data = theResponse.substring(0, theResponse.length() - 4);
                    boolean isOk = status.equals("9000");
                    String show;
                    if (isOk) {
                        show = "status perfect: userid" + data;
                    } else {
                        show = "status error" + status;
                    }
                    isoDep.close();
                    TextView textView = findViewById(R.id.textView);
                    // Try to touch View of UI thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.append("\n" + show);

                            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                            startActivity(intent);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
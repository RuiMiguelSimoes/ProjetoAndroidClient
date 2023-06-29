package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

public class TestNFCreader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_nfcreader);

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // NFC is not supported on this device
        } else if (!nfcAdapter.isEnabled()) {
            // NFC is disabled on this device, prompt the user to enable it
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            // Process the tag data or read the tag information as needed
            // You can use the `Tag` object to interact with the NFC tag
            // For example, you can read the tag's ID or NDEF data
            Toast.makeText(this, "CONTENT: "+tag.toString(), Toast.LENGTH_SHORT).show();
        }
    }


}
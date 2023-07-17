package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class AddFriend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            // Process the tag data or emulate tag behavior as needed.
            String message = "Your message to be transmitted";

            // Create an NdefRecord with the message content
            NdefRecord record = NdefRecord.createTextRecord(null, message);
            NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{record});

            // Emulate the tag and transmit the NdefMessage
            Ndef ndef = Ndef.get(tag);
            try {
                ndef.connect();
                ndef.writeNdefMessage(ndefMessage);
                ndef.close();
            } catch (IOException | FormatException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter != null && nfcAdapter.isEnabled()) {
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_IMMUTABLE);
            IntentFilter intentFilter = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
            IntentFilter[] intentFiltersArray = new IntentFilter[] { intentFilter };
            String[][] techLists = new String[][] { new String[] { NfcA.class.getName() } };
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techLists);
        } else {
            Toast.makeText(this, "Capacidades NFC não não suportadas", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }


}
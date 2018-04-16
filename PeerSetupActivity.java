package edu.utep.cs.cs4330.sudoku;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.PopupMenu;
import android.view.MenuItem;
import android.os.Bundle;
import android.widget.RadioButton;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration;

import java.net.UnknownHostException;
import java.util.List;
import java.net.InetAddress;

import android.text.format.Formatter;
import android.widget.TextView;

public class PeerSetupActivity extends AppCompatActivity {
    private static String PORT = "8000";
    private ActionBar actionBar;
    private Button connect, disconnect;
    private EditText peerIp, peerPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peer_setup);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        peerIp = findViewById(R.id.peerIP);
        peerPort = findViewById(R.id.peerPort);
        disconnect = findViewById(R.id.disconnect);
        connect = findViewById(R.id.connect);
        connect.setOnClickListener((view) -> {
            Intent result = new Intent();
            result.putExtra("address", peerIp.getText().toString());
            result.putExtra("port", peerPort.getText().toString());
            result.putExtra("space", "open");
            setResult(RESULT_OK, result);
            finish();
        });
        disconnect.setOnClickListener((view -> {
            Intent result = new Intent();
            result.putExtra("address", peerIp.getText().toString());
            result.putExtra("port", peerPort.getText().toString());
            result.putExtra("space", "close");
            setResult(RESULT_OK, result);
            finish();
        }));

    }

    private boolean menuListener(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_lan: break;
            case R.id.action_bt: {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(intent);
                break;
            }
            case R.id.action_wd: break;
        }
        return true;
    }
}

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

public class HostSetupActivity extends AppCompatActivity {
    private static String PORT = "8000";
    private ActionBar actionBar;
    private Button share, close;
    private RadioButton lan, bt, wifidirect;
    private PopupMenu popupMenu;
    private EditText hostName, hostIp, hostPort, peerIp, peerPort;
    private TextView player, peer;
    private GridLayout l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_setup);
        Bundle extras = getIntent().getExtras();
        String session = extras.getString("session");
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        hostIp = findViewById(R.id.ip);
        hostName = findViewById(R.id.hostName);
        hostPort = findViewById(R.id.port);
        peerIp = findViewById(R.id.peerIP);
        peerPort = findViewById(R.id.peerPort);
        player = findViewById(R.id.player);
        share = findViewById(R.id.share);
        close = findViewById(R.id.close);
        //check for empty
        share.setOnClickListener((view)->{
            Intent result = new Intent();
            result.putExtra("address", peerIp.getText().toString());
            result.putExtra("port", peerPort.getText().toString());
            result.putExtra("space", "open");
            setResult(RESULT_OK, result);
            finish();
        });
        close.setOnClickListener((view) -> {
            Intent result = new Intent();
            result.putExtra("address", peerIp.getText().toString());
            result.putExtra("port", peerPort.getText().toString());
            result.putExtra("space", "close");
            setResult(RESULT_OK, result);
            finish();
        });
        close = findViewById(R.id.close);
        peer = findViewById(R.id.peer);
        WifiManager wim= (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        List<WifiConfiguration> l =  wim.getConfiguredNetworks();
        WifiConfiguration wc = l.get(0);
        String address = Formatter.formatIpAddress(wim.getConnectionInfo().getIpAddress());
        hostIp.setText(address);
        new Thread(()->{
            try {
                InetAddress hostTitleName = InetAddress.getLocalHost();
                String host = hostTitleName.getHostName();
                runOnUiThread(()->{
                    hostName.setText(host);
                    Log.d("hostname", host);
                });
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }).start();
        hostPort.setText(PORT);
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

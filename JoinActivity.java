package edu.utep.cs.cs4330.sudoku;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.view.MenuItem;
import android.os.Bundle;
import android.widget.RadioButton;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicReference;

import android.text.format.Formatter;

import edu.utep.cs.cs4330.sudoku.p2p.NetworkAdapter;

public class JoinActivity extends AppCompatActivity {
    private static String PORT = "8000";
    private ActionBar actionBar;
    private Button protocol, host, connect;
    private RadioButton lan, bt, wifidirect;
    private PopupMenu popupMenu;
    private EditText hostName,ipNumb, ipPort, peerIp, peerPort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        protocol = findViewById(R.id.protocol);
        popupMenu = new PopupMenu(this, protocol );
        popupMenu.inflate(R.menu.protocol);
        popupMenu.setOnMenuItemClickListener(this::menuListener);
        protocol.setOnClickListener(view -> popupMenu.show());
        ipNumb = findViewById(R.id.ip);
        hostName = findViewById(R.id.hostName);
        ipPort = findViewById(R.id.port);
        peerIp = findViewById(R.id.peerIP);
        peerPort = findViewById(R.id.peerPort);
        WifiManager wim= (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        List<WifiConfiguration> l =  wim.getConfiguredNetworks();
        WifiConfiguration wc = l.get(0);
        String address = Formatter.formatIpAddress(wim.getConnectionInfo().getIpAddress());
        ipNumb.setText(address);
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
        ipPort.setText(PORT);
        host = findViewById(R.id.host);
        connect = findViewById(R.id.connect);
        host.setOnClickListener((view)->{
            //should start a new progress bar while waiting for player
            // can this broadast?????
            //may be a thread that can be returned?
            int port = Integer.parseInt(PORT);
            final Socket t = createSocket(String.valueOf(ipNumb), port);
            final NetworkAdapter adapter = new NetworkAdapter(t);
            adapter.setMessageListener(new NetworkAdapter.MessageListener() {
                public void messageReceived(NetworkAdapter.MessageType type, int x, int y, int z, int[] others) {
                    switch (type) {
                    case JOIN: ;
                    case JOIN_ACK: ; // x (response), y (size), others (board)
                    case NEW: ;      // x (size), others (board)
                    case NEW_ACK: ;  // x (response)
                    case FILL: ;     // x (x), y (y), z (number
                    case FILL_ACK: ; // x (x), y (y), z (number)
                    case QUIT: ;

                    }
                }
            });
        });
        connect.setOnClickListener((view -> {
            String peerAddress = String.valueOf(peerIp.getText());
            int port = Integer.parseInt(String.valueOf(peerPort.getText()));
            final Socket[] t = new Socket[1];
            final NetworkAdapter[] adapter = new NetworkAdapter[1];
            new Thread(){
                public void run(){
                    t[0] = createSocket(peerAddress, port);
                    Log.d("thread", "connecting "+t[0].isConnected());
                    adapter[0] = new NetworkAdapter(t[0]);
                    adapter[0].setMessageListener(new NetworkAdapter.MessageListener() {
                        public void messageReceived(NetworkAdapter.MessageType type, int x, int y, int z, int[] others) {
                            switch (type) {
                                case JOIN: ;
                                case JOIN_ACK: Log.d("wow", others.toString()+""); // x (response), y (size), others (board)
                                case NEW: ;      // x (size), others (board)
                                case NEW_ACK: ;  // x (response)
                                case FILL: ;     // x (x), y (y), z (number
                                case FILL_ACK: ; // x (x), y (y), z (number)
                                case QUIT: ;
                            }
                        }
                    });
                    adapter[0].writeJoin();
                    adapter[0].receiveMessagesAsync();
                }
            }.start();

            //
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
    private Socket createSocket(String host, int port) {
        Socket s = new Socket();
        try {
            s.connect(new InetSocketAddress(host, port));
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return s;
    }

}


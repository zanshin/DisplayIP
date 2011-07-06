package net.zanshin.displayip;

import android.app.Activity;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DisplayIP extends Activity implements OnClickListener{
	
	private static final String LOG_TAG = "DisplayIP";
	
	// dhcpInfo elements in order - comment shows string identifier in DhcpInfo
	public String ipAddress; // ipAddr
	public String gateway; // gateway
	public String netmask; // netmask
	public String dns1; // dns1
	public String dns2; // dns2 
	public String serverAddress; //DHCP server
	public String leaseDuration; // lease
	
	public String macAddress;
	public String ssid;
	public String linkSpeed;
	
	public String dhcpInfos[];
	
    private TextView txtIpAddress;
    private TextView txtGateway;
    private TextView txtDns1;
    private TextView txtDns2;
    private TextView txtNetmask;
    private TextView txtServer;
    private TextView txtLease;
    private TextView txtMacAddress;
    private TextView txtSSID;
    private TextView txtLinkSpeed;
    
    private WifiManager wifiManager;
    
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d(LOG_TAG, "onCreate");
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // get the WiFiManager instance
        wifiManager  =  (WifiManager)getSystemService(WIFI_SERVICE);
        
        // Setup click listeners for the button
        View aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        
        // Get the DHCP and Connection information and map to the view
        getDhcpInfo();
        getConnectionInfo();
        mapToLayout();
        
    }
        
    /**
     * Get network information from DHCPInfo object using our wrapper class, DhcpWrapper.
     */
    private void getDhcpInfo() {
    	Log.d(LOG_TAG, "entered getDhcpInfo");
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        
        dhcpInfos = dhcpInfo.toString().split(" ");
        
        int i = 0;
        while (i < dhcpInfos.length) {
        	if (dhcpInfos[i].equals("ipaddr")) {
        		ipAddress = dhcpInfos[i+1];
        	}
        	if (dhcpInfos[i].equals("gateway")) {
        		gateway = dhcpInfos[i+1];
        	}
        	if (dhcpInfos[i].equals("netmask")) {
        		netmask = dhcpInfos[i+1];
        	}
        	if (dhcpInfos[i].equals("dns1")) {
        		dns1 = dhcpInfos[i+1];
        	}
        	if (dhcpInfos[i].equals("dns2")) {
        		dns2 = dhcpInfos[i+1];
        	}
        	if (dhcpInfos[i].equals("server")) {
        		serverAddress = dhcpInfos[i+1];
        	}
        	if (dhcpInfos[i].equals("lease")) {
        		leaseDuration = dhcpInfos[i+1];
        	}
        
        	i++;
        }

    }
    
    /**
     * Get MAC address and SSID from WifiInfo.
     */
    private void getConnectionInfo() {
    	Log.d(LOG_TAG, "getWifiInto");
    	WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    	
    	macAddress = wifiInfo.getMacAddress();
    	ssid = wifiInfo.getSSID();
    	linkSpeed = Integer.toString(wifiInfo.getLinkSpeed());
    }
    
    /**
     * Map the data to the textViews on our layout.
     */
   private void mapToLayout() {
	// Get a handle to all user interface elements
       txtIpAddress = (TextView) findViewById(R.id.txtIpAddress);
       txtGateway = (TextView) findViewById(R.id.txtGateway);
       txtDns1 = (TextView) findViewById(R.id.txtDns1);
       txtDns2 = (TextView) findViewById(R.id.txtDns2);
       txtNetmask = (TextView) findViewById(R.id.txtNetmask);
       txtServer = (TextView) findViewById(R.id.txtServer);
       txtLease = (TextView) findViewById(R.id.txtLease);
       txtMacAddress = (TextView) findViewById(R.id.txtMacAddress);
       txtSSID = (TextView) findViewById(R.id.txtSsid);
       txtLinkSpeed = (TextView) findViewById(R.id.txtLinkSpeed);
       
       // Set the interface elements
       txtIpAddress.setText(ipAddress);
       txtGateway.setText(gateway);
       txtNetmask.setText(netmask);
       txtDns1.setText(dns1);
       txtDns2.setText(dns2);
       txtServer.setText(serverAddress);
       txtLease.setText(leaseDuration + " seconds");
       
       txtMacAddress.setText(macAddress);
       txtSSID.setText(ssid);
       txtLinkSpeed.setText(linkSpeed + " Mbps");
   }
   
   /**
    * Implement the onClick() required by the OnClickListener interface.
    */
   public void onClick(View v) {
	   switch (v.getId()) {
	   case R.id.about_button:
		   Intent i = new Intent(this, About.class);
		   startActivity(i);
		   break;
	   }
   }
}
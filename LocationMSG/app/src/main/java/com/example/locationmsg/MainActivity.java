package com.example.locationmsg;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    LocationManager service = null;
    Location _currentLocation;
    TextView locationText = null;
    TextView addressText = null;
    String provider=LocationManager.NETWORK_PROVIDER;
    String provider1 = LocationManager.GPS_PROVIDER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationText = (TextView) findViewById(R.id.location);
        addressText = (TextView) findViewById(R.id.address);

        service = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = service.getBestProvider(criteria, false);

        LocationListener loclistener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                _currentLocation = location;
                if (_currentLocation == null) {
                    locationText.setText("Unable to determine your location. updated");
                } else {
                    locationText.setText(String.format("%s data: {%f},{%f}",provider, _currentLocation.getLatitude(), _currentLocation.getLongitude()));
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                _currentLocation = service.getLastKnownLocation(provider);
                if (_currentLocation == null) {
                    locationText.setText("Unable to determine your location. enabled");
                } else
                    locationText.setText(String.format("%s data: {%f},{%f}",provider, _currentLocation.getLatitude(), _currentLocation.getLongitude()));
            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
//        if(service.getAllProviders().contains(provider)) {
//            service.requestLocationUpdates(provider, 1, 1, loclistener);
//        }
        if (service.getAllProviders().contains(provider1)) {
            service.requestLocationUpdates(provider1, 1, 1, loclistener);
        }
 //      boolean enabled = service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//// check if enabled and if not send user to the GSP settings
//// Better solution would be to display a dialog and suggesting to
//// go to the settings
//        if (!enabled) {
//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent);
//        }else{

//        if(enabled)
//            showLocation();
//        else locationText.setText("Network not enabled");
        addOnClick();
}

    private String showLocation(){
        _currentLocation = service.getLastKnownLocation(provider1);
        if (_currentLocation == null) {
            locationText.setText("Unable to determine your location. on create "+ provider);
            return null;
        } else{
            String res= String.format("%s data: {%f},{%f}",provider, _currentLocation.getLatitude(), _currentLocation.getLongitude());
            locationText.setText(res);
            return res;
        }

    }
    public void addOnClick(){
        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationText.setText(showLocation());

                EditText say = (EditText) findViewById(R.id.msg);
                String txt= say.getText() == null ? null: say.getText().toString();
                EditText call = (EditText) findViewById(R.id.call);
                String number = call.getText()== null? null: call.getText().toString();
                try {
                    SendMsg sender= new SendMsg(number);
                    sender.Send(txt);

                    TextView address = (TextView) findViewById(R.id.address);
                    address.setText(String.format("Sending text: %s to %s", txt, number));
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                }

                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

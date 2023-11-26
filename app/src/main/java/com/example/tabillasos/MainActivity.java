package com.example.tabillasos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.widget.Toast;
import android.telephony.SmsManager;
public class MainActivity extends AppCompatActivity {
    GPSReceiver receiver;
    LocationManager manager;
    double latitude = 0;
    double longitude = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button onSendSOSButton = findViewById(R.id.sendSOS);
        receiver = new GPSReceiver(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager = (LocationManager)
                    this.getSystemService(Context.LOCATION_SERVICE);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1.0F, receiver);

        onSendSOSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager sms = SmsManager.getDefault();
                String phoneNumber = "09510055677";
                String messageBody = "Please take me from CHAPTER 10. ANDROID APP #6: S.O.S. MESSAGE SENDER longitude: " + Double.toString(longitude) + " and latitude: " + Double.toString(latitude);
                try {
                    sms.sendTextMessage(phoneNumber, null, messageBody ,null, null);
                    Toast.makeText(getApplicationContext(),
                            "S.O.S. message sent!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "Message sending failed!!!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
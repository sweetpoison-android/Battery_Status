package com.example.battery_status;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView level,voltage,health,type,charging_source,temperature,charging_Status;

    BroadcastReceiver battery_broadcast;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        level = findViewById(R.id.Battery_level);
        voltage = findViewById(R.id.Battery_Voltage);
        health = findViewById(R.id.Battery_Health);
        type = findViewById(R.id.Battery_type);
        charging_source = findViewById(R.id.Battery_chargingsource);
        temperature = findViewById(R.id.Battery_temperature);
        charging_Status = findViewById(R.id.Battery_chargingstatus);

        intentFilterAndBroadcast();
    }


    public  void intentFilterAndBroadcast()
    {
        BatteryManager batteryManager = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);

        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        battery_broadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction()))
               {
                   level.setText(String.valueOf("Battery Level : "+intent.getIntExtra("level", 0))+"%");

                   float volttemp = (float) (intent.getIntExtra("voltage", 0)*0.001);
                   voltage.setText("Battery Voltage : "+String.valueOf(volttemp)+"  v ");
                   batterHealth(intent);

                   type.setText("Battery Type : "+intent.getStringExtra("technology"));

                   batteryChargingSource(intent);

                   temperature.setText("Battery Temperature : "+(float)intent.getIntExtra("temperature", -1)/10+"°c");

                   batteryChargingStatus(intent);

               }
            }
        };

    }

    private void batterHealth(Intent intent)
    {
        int val = intent.getIntExtra("health", 0);

        switch (val)
        {
            case BatteryManager.BATTERY_HEALTH_UNKNOWN :
                health.setText("Batterh Health"+"unknown");
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                health.setText("Battery Health : "+"Good");
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                health.setText("Battery Health : "+"Overheat");
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                health.setText("Battery Health : "+"Overvoltage");
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                health.setText("Battery Health : "+"Dead");
                break;
            case BatteryManager.BATTERY_HEALTH_COLD:
                health.setText("Battery Health : "+"Cold");
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                health.setText("Battery Health : "+"Unspecified Fail");
                break;
            default:health.setText("Battery Health : "+"null");
        }
    }

    private  void batteryChargingSource(Intent intent)
    {
        int batterysource = intent.getIntExtra("plugged", -1);

        switch (batterysource)
        {
            case BatteryManager.BATTERY_PLUGGED_AC:
                charging_source.setText("Battert_charging Source : "+"AC");
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                charging_source.setText("Battert_charging Source : "+"USB");
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                charging_source.setText("Battert_charging Source : "+"Wireless");
                break;
            default:charging_source.setText("Battery Charging Source : "+"null");

               }
    }

    private  void batteryChargingStatus(Intent intent)
    {
        int chargingstatus = intent.getIntExtra("status", -1);

        switch (chargingstatus)
        {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                charging_Status.setText("Battery charging status : " + "Charging");
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                charging_Status.setText("Battery charging status : " + "Not Charging");
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                charging_Status.setText("Battery charging status : " + "Discharge");
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                charging_Status.setText("Battery charging status : " + "Battery full");
                break;
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                charging_Status.setText("Battery charging status : " + "Unknown");
                break;
            default:charging_Status.setText("Battery Charging Status"+"null");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(battery_broadcast, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(battery_broadcast);
    }
}
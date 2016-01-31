package mwteck.com.fillingoilhistory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import bean.FuelUse;

public class FuelInfosActivity extends AppCompatActivity {

    private TextView textViewDate;
    private TextView textViewStationName;
    private TextView textViewOdometer;
    private TextView textViewGrade;
    private TextView textViewFuelAmount;
    private TextView textViewFuelUnitCost;
    private TextView textViewFuelAllAmount;
    private FuelUse fuel;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_infos);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Fuel Infos");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        initData();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                FuelUse fuel = (FuelUse) intent.getExtras().getSerializable("fuel");
                textViewDate.setText("Fuel Use Date:" + fuel.getDate());
                textViewStationName.setText("Station Name:" + fuel.getStationName());
                textViewOdometer.setText("Odometer Reading(KM):" + fuel.getOdometer());
                textViewGrade.setText("Fuel Grade:" + fuel.getFuelGrade());
                textViewFuelAmount.setText("Fuel Amount(L):" + fuel.getFuelAmount());
                textViewFuelUnitCost.setText("Fuel Unit Cost(cents/L):" + fuel.getFuelUnitCost());
                textViewFuelAllAmount.setText("Fuel Cost:" + fuel.getFuelCost());
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("update_ui");
        registerReceiver(receiver, intentFilter);

    }


    private void initView() {

        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewStationName = (TextView) findViewById(R.id.textViewStationName);
        textViewOdometer = (TextView) findViewById(R.id.textViewOdometer);
        textViewGrade = (TextView) findViewById(R.id.textViewGrade);
        textViewFuelAmount = (TextView) findViewById(R.id.textViewFuelAmount);
        textViewFuelUnitCost = (TextView) findViewById(R.id.textViewFuelUnitCost);
        textViewFuelAllAmount = (TextView) findViewById(R.id.textViewFuelAllAmount);
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        fuel = (FuelUse) bundle.getSerializable("fuel");
        textViewDate.setText("Fuel Use Date:" + fuel.getDate());
        textViewStationName.setText("Station Name:" + fuel.getStationName());
        textViewOdometer.setText("Odometer Reading(KM):" + fuel.getOdometer());
        textViewGrade.setText("Fuel Grade:" + fuel.getFuelGrade());
        textViewFuelAmount.setText("Fuel Amount(L):" + fuel.getFuelAmount());
        textViewFuelUnitCost.setText("Fuel Unit Cost(cents/L):" + fuel.getFuelUnitCost());
        textViewFuelAllAmount.setText("Fuel Cost:" + fuel.getFuelCost());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_edit_fuel:
                Intent in = new Intent(FuelInfosActivity.this, EditFuelActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("fuel", fuel);
                in.putExtras(bundle);
                startActivity(in);
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fuel_infos, menu);
        return true;
    }

    @Override
    protected void onDestroy() {

        unregisterReceiver(receiver);
        super.onDestroy();
    }
}

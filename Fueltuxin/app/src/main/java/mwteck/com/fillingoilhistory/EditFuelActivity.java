package mwteck.com.fillingoilhistory;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.text.DecimalFormat;
import java.util.Calendar;
import bean.FuelUse;
import utils.FileUtils;

public class EditFuelActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextChooseDate;
    private EditText editTextFuelAmount;
    private EditText editTextFuelUnitCost;
    private EditText editTextStation;
    private EditText editTextOdometer;
    private FuelUse fuel;
    private FuelUse restoreFuel;
    private int position = -1;
    private TextView textViewId;
    private EditText editextGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fuel);
        if (savedInstanceState != null) {
            restoreFuel = (FuelUse) savedInstanceState.getSerializable("fuel");
        }
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Add Fuel");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
        initView();
    }

    private void initView() {
        editTextChooseDate = (EditText) findViewById(R.id.editTextChooseDate);
        editTextFuelAmount = (EditText) findViewById(R.id.editTextFuelAmount);
        editTextFuelAmount.addTextChangedListener(setDecimalDigits(3));
        editTextFuelUnitCost = (EditText) findViewById(R.id.editTextFuelUnitCost);
        editTextStation = (EditText) findViewById(R.id.editTextStation);
        editTextOdometer = (EditText) findViewById(R.id.editTextOdometer);
        editextGrade = (EditText) findViewById(R.id.editextGrade);
        textViewId = (TextView) findViewById(R.id.textViewId);
        editTextOdometer.addTextChangedListener(setDecimalDigits(1));
        editTextChooseDate.setKeyListener(null);
        editTextChooseDate.setOnClickListener(this);
        editTextFuelUnitCost.addTextChangedListener(setDecimalDigits(1));
        editTextFuelUnitCost.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                Toast.makeText(EditFuelActivity.this, "input", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        if (null != fuel) {
            editTextChooseDate.setText(fuel.getDate());
            editTextFuelAmount.setText(String.valueOf(fuel.getFuelAmount()));
            editTextFuelUnitCost.setText(String.valueOf(fuel.getFuelUnitCost()));
            editTextStation.setText(fuel.getStationName());
            editextGrade.setText(fuel.getFuelGrade());
            editTextOdometer.setText(String.valueOf(fuel.getOdometer()));
            textViewId.setText(String.valueOf(fuel.getId()));
            String fuelGrade = fuel.getFuelGrade();
        }
        if (null != restoreFuel) {
            editTextChooseDate.setText(restoreFuel.getDate());
            editTextFuelAmount.setText(String.valueOf(restoreFuel.getFuelAmount()));
            editTextFuelUnitCost.setText(String.valueOf(restoreFuel.getFuelUnitCost()));
            editTextStation.setText(restoreFuel.getStationName());
            editTextOdometer.setText(String.valueOf(restoreFuel.getOdometer()));
            textViewId.setText(String.valueOf(restoreFuel.getId()));
            editextGrade.setText(restoreFuel.getFuelGrade());
        }
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            fuel = (FuelUse) bundle.getSerializable("fuel");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editTextChooseDate:
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(EditFuelActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editTextChooseDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {
            case android.R.id.home:
                showDialog();
                break;
            case R.id.menu_edit_fuel_submit:
                SaveFuelInfos();
                break;
        }

        return true;
    }

    /**
     * save infos
     */
    private void SaveFuelInfos() {
        String date = editTextChooseDate.getText().toString();
        String station = editTextStation.getText().toString();
        String odometer = editTextOdometer.getText().toString();
        String fuelGrade = editextGrade.getText().toString();
        String fuelAmout = editTextFuelAmount.getText().toString();
        String fuelUnitCost = editTextFuelUnitCost.getText().toString();
        if (date.isEmpty()) {
            Toast.makeText(EditFuelActivity.this, "please choose date", Toast.LENGTH_SHORT).show();
            return;
        } else if (station.isEmpty()) {
            Toast.makeText(EditFuelActivity.this, "please input station", Toast.LENGTH_SHORT).show();
            return;
        } else if (odometer.isEmpty()) {
            Toast.makeText(EditFuelActivity.this, "please input odometer", Toast.LENGTH_SHORT).show();
            return;
        } else if (fuelGrade.isEmpty()) {
            Toast.makeText(EditFuelActivity.this, "please input fuel grade", Toast.LENGTH_SHORT).show();
            return;
        } else if (fuelAmout.isEmpty()) {
            Toast.makeText(EditFuelActivity.this, "please input fuel amout", Toast.LENGTH_SHORT).show();
            return;
        } else if (fuelUnitCost.isEmpty()) {
            Toast.makeText(EditFuelActivity.this, "please input fuel unit cost", Toast.LENGTH_SHORT).show();
            return;
        }


        // data not empty ,save data
        FuelUse fuelUse = new FuelUse();
        fuelUse.setDate(date);
        fuelUse.setFuelAmount(Float.parseFloat(fuelAmout));
        fuelUse.setFuelUnitCost(Float.parseFloat(fuelUnitCost));
        fuelUse.setStationName(station);
        fuelUse.setFuelGrade(fuelGrade);
        fuelUse.setOdometer(Float.parseFloat(odometer));
        fuelUse.setFuelAmount(Float.parseFloat(fuelAmout));
        float fuelCost = Float.parseFloat(fuelAmout) * Float.parseFloat(fuelUnitCost);
        String format = new DecimalFormat("#.00").format(fuelCost);
        fuelUse.setFuelCost(format);
//        FuelDao fuelDao = FuelDao.getInstence(EditFuelActivity.this);
        if(null != fuel){
//            fuelUse.setId(fuel.getId());
//            fuelDao.update(fuelUse);
            Intent in = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("fuel", fuelUse);
            in.putExtras(bundle);
            in.setAction("update_ui");
            sendBroadcast(in);
            Gson gson = new Gson();
            String jsonString = gson.toJson(fuelUse);
            // write file
            String fileName = fuel.getFileName();
            FileUtils.save(EditFuelActivity.this,fileName,jsonString);
            Toast.makeText(EditFuelActivity.this, "updata success!", Toast.LENGTH_SHORT).show();
        }else {
            Intent in = new Intent();
            in.setAction("update_ui");
            sendBroadcast(in);
            Gson gson = new Gson();
            String jsonString = gson.toJson(fuelUse);
            // write file
            Log.e("Tag",jsonString);
            String fileName = System.currentTimeMillis()+".txt";
            FileUtils.save(EditFuelActivity.this,fileName,jsonString);
            Toast.makeText(EditFuelActivity.this, "save success!", Toast.LENGTH_SHORT).show();
        }
        //notify MainActivity update ui

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_fuel, menu);
        return true;
    }


    /**
     *
     */
    private TextWatcher setDecimalDigits(final int decimalDigits) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > decimalDigits) {
                    s.delete(posDot + decimalDigits + 1, posDot + decimalDigits + 2);
                }
            }
        };
        return textWatcher;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialog();
        }
        return true;
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditFuelActivity.this);
        builder.setTitle("Wraning").setMessage("Sure Give up editing?");
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String date = editTextChooseDate.getText().toString();
        String station = editTextStation.getText().toString();
        String odometer = editTextOdometer.getText().toString();
        String fuelGrade = editextGrade.getText().toString();
        String fuelAmout = editTextFuelAmount.getText().toString();
        String fuelUnitCost = editTextFuelUnitCost.getText().toString();
        FuelUse fuel = new FuelUse();
        fuel.setDate(date);
        fuel.setStationName(station);
        fuel.setOdometer(Float.parseFloat(odometer));
        fuel.setFuelGrade(fuelGrade);
        fuel.setFuelAmount(Float.parseFloat(fuelAmout));
        fuel.setFuelUnitCost(Float.parseFloat(fuelUnitCost));
        String Id = textViewId.getText().toString();
        if(!Id.endsWith("-1")){
            fuel.setId(Integer.parseInt(Id));
        }
        outState.putSerializable("fuel",fuel);
    }
}

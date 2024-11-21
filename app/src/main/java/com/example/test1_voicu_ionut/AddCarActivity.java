package com.example.test1_voicu_ionut;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddCarActivity extends AppCompatActivity {
    public static final String CAR_KEY = "sendCar";

    FloatingActionButton fab;
    EditText et_make, et_model, et_year;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        intent = getIntent();

        initComponents();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    Car car = buildCarFromComponents();
                    intent.putExtra(CAR_KEY, car);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    public boolean isValid(){
        if(et_make.getText() == null || et_make.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.add_car_make_err, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(et_model.getText() == null || et_model.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.add_car_model_err, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(et_year.getText() == null || et_year.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.add_car_year_err, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    public Car buildCarFromComponents(){
        String make = et_make.getText().toString().strip();
        String model = et_model.getText().toString().strip();
        Integer year = Integer.parseInt(et_year.getText().toString());
        return new Car(make,model,year);
    }

    public void initComponents(){
        fab = findViewById(R.id.fab_add);
        et_make = findViewById(R.id.et_make);
        et_model = findViewById(R.id.et_model);
        et_year = findViewById(R.id.et_year);
    }
}
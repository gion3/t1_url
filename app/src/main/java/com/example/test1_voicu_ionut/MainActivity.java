package com.example.test1_voicu_ionut;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Car> cars = new ArrayList<>();
    private ActivityResultLauncher<Intent> addLauncher;

    ListView lv_cars;

    int bg_color_1,bg_color_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        addLauncher = registerAddLauncher();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add){
            Intent intentAdd = new Intent(getApplicationContext(), AddCarActivity.class);
            addLauncher.launch(intentAdd);
        }
        else if(item.getItemId() == R.id.menu_details){
            fetchTextFromURL("https://pastebin.com/raw/BWQhkEBD");
        }
        return true;
    }

    private void fetchTextFromURL(String urlString) {
        new Thread(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    String finalResult = result.toString();
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, finalResult, Toast.LENGTH_LONG).show());

                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Eroare la preluarea datelor!", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private ActivityResultLauncher<Intent> registerAddLauncher() {
        ActivityResultCallback<ActivityResult> callback = getCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    private ActivityResultCallback<ActivityResult> getCallback() {
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Car car = (Car) result.getData().getSerializableExtra(AddCarActivity.CAR_KEY);
                    cars.add(car);
                    ArrayAdapter adapter = (ArrayAdapter) lv_cars.getAdapter();
                    adapter.notifyDataSetChanged();
                    updateBackgroundColor();
                }
            }
        };
    }
    public void updateBackgroundColor(){
        if(cars.size() % 2 == 0){
            lv_cars.setBackgroundColor(bg_color_1);
        }
        else{
            lv_cars.setBackgroundColor(bg_color_2);
        }
    }
    public void initComponents(){
        lv_cars = findViewById(R.id.lv_cars);
        ArrayAdapter<Car> carArrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,cars);
        lv_cars.setAdapter(carArrayAdapter);
        bg_color_1 = ContextCompat.getColor(this, R.color.background_odd);
        bg_color_2 = ContextCompat.getColor(this,R.color.background_even);
    }
}
package com.proj.androidexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.proj.androidexam.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private final String TAG = "MAIN_ACTIVITY";
    private final int MAX_ITEMS = 10;
    private CountriesApiService countriesApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        countriesApiService = new CountriesApiService(MainActivity.this);

        binding.tvRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countriesApiService.getRegions(new CountriesApiService.ResponseListener() {
                    @Override
                    public void onResponse(List<String> regions) {
                        setRegionItems(regions);
                    }

                    @Override
                    public void onError(String message) {
                        throw new RuntimeException(message);
                    }
                });
            }
        });

        binding.tvCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String region = binding.tvRegion.getText().toString();
                countriesApiService.getCountries(region, new CountriesApiService.ResponseListener() {

                    @Override
                    public void onResponse(List<String> countries) {
                        setCountyItems(countries);
                    }

                    @Override
                    public void onError(String message) {
                        throw new RuntimeException(message);
                    }
                });
            }
        });

        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvCountry.setText("");
                binding.tvRegion.setText("");
                binding.tvName.setText("");
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String region = binding.tvRegion.getText().toString();
                String country = binding.tvCountry.getText().toString();
                String name = binding.tvName.getText().toString();
                if (!(region.equals("") || country.equals("") || name.equals(""))) {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("region", region);
                    intent.putExtra("country", country);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            }
        });
    }

    private void setRegionItems(List<String> regions) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (MainActivity.this, R.layout.region_item, regions);
        binding.tvRegion.setThreshold(1);
        binding.tvRegion.setAdapter(adapter);
    }

    private void setCountyItems(List<String> countyItems) {
//        if (countyItems != null && countyItems.size() > MAX_ITEMS) {
//            countyItems = countyItems.subList(0, MAX_ITEMS);
//        }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (MainActivity.this, R.layout.region_item, countyItems);
        binding.tvCountry.setThreshold(1);
        binding.tvCountry.setAdapter(adapter);
    }
}
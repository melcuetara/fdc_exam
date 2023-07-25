package com.proj.androidexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.proj.androidexam.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name = getIntent().getStringExtra("name");
        String region = getIntent().getStringExtra("region");
        String country = getIntent().getStringExtra("country");

        binding.tvNameLabel.setText("Hi" + name);
        binding.tvCountryRegionLabel.setText("You are from\n" + region + " ,\n" + country);
    }
}
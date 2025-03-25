package com.openclassrooms.tajmahal.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DetailsFragment.newInstance())
                    .commitNow();
        }

        Log.d("FragmentCheck", "MainActivity est affiché");
    }}


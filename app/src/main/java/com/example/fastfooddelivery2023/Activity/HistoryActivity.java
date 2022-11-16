package com.example.fastfooddelivery2023.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fastfooddelivery2023.R;

public class HistoryActivity extends AppCompatActivity {
private RecyclerView rcv_history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        rcv_history = findViewById(R.id.rcv_history);

    }
}
package com.example.umquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class student_home extends AppCompatActivity {

    private ImageButton streakbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.student_dahboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Studenthome), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        streakbutton = findViewById(R.id.FireStreak1);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhoIsUsing();
            }
        };

        streakbutton.setOnClickListener(listener);

    }

    public void openWhoIsUsing() {
        Intent intent = new Intent(this, QuestStreak.class);
        startActivity(intent);
    }
}
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

public class MainActivity extends AppCompatActivity {

    private ImageButton startbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.get_started);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.getstart), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startbutton = findViewById(R.id.start);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhoIsUsing();
            }
        };

        startbutton.setOnClickListener(listener);

    }

    public void openWhoIsUsing() {
        Intent intent = new Intent(this, Thisacc.class);
        startActivity(intent);
    }
}
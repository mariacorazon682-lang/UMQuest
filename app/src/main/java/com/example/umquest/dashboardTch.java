package com.example.umquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class dashboardTch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard_tch);

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.profileCard).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            SharedPreferences prefs = getSharedPreferences("UMQuestPrefs", MODE_PRIVATE);
            prefs.edit().remove("user_role").apply();
            
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, getstarted.class));
            finish();
        });

        findViewById(R.id.fabAdd).setOnClickListener(v -> {
            Toast.makeText(this, "Add Activity (Teacher) clicked", Toast.LENGTH_SHORT).show();
        });
    }
}
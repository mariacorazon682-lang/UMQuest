package com.example.umquest;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class whoisusing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_whoisusing);

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

        ImageView backgroundGif = findViewById(R.id.backgroundGif1);

        Glide.with(this)
                .asGif()
                .load(R.drawable.bubbles)
                .apply(new RequestOptions()
                        .format(DecodeFormat.PREFER_RGB_565)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .priority(Priority.IMMEDIATE)
                        .override(360, 640)
                        .dontTransform())
                .into(backgroundGif);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btn_students = findViewById(R.id.btn_students);
        btn_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(whoisusing.this, studentlogin.class);
                View logo = findViewById(R.id.imageButton1);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(whoisusing.this, 
                        logo, "logo_shared_element");
                startActivity(intent, options.toBundle());
            }
        });

        Button btn_teacher = findViewById(R.id.btn_teacher);
        btn_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(whoisusing.this, teacherlogin.class);
                View logo = findViewById(R.id.imageButton1);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(whoisusing.this, 
                        logo, "logo_shared_element");
                startActivity(intent, options.toBundle());
            }
        });
    }
}

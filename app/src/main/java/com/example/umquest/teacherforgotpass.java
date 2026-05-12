package com.example.umquest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class teacherforgotpass extends AppCompatActivity {

    private static final String TAG = "teacherforgotpass";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacherforgotpass);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://umquest-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users").child("Teachers");

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

        ImageView backgroundGif = findViewById(R.id.backgroundGifForgot);

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

        EditText usernameInput = findViewById(R.id.usernameTchForgot);
        EditText newPasswordInput = findViewById(R.id.newTchPasswordInput);
        EditText confirmPasswordInput = findViewById(R.id.confirmTchPasswordInput);
        Button btnCreateForgot = findViewById(R.id.btn_create_forgotTch);

        btnCreateForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String newPass = newPasswordInput.getText().toString().trim();
                String confirmPass = confirmPasswordInput.getText().toString().trim();

                if (username.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(teacherforgotpass.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPass.equals(confirmPass)) {
                    Toast.makeText(teacherforgotpass.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (newPass.length() < 6) {
                    Toast.makeText(teacherforgotpass.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Find the email associated with the username in the database
                mDatabase.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot userSnap : snapshot.getChildren()) {
                                String email = userSnap.child("email").getValue(String.class);
                                if (email != null) {
                                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(teacherforgotpass.this, "Check your email (" + email + ") to reset your password.", Toast.LENGTH_LONG).show();
                                                finish();
                                            } else {
                                                Log.e(TAG, "Reset failed", task.getException());
                                                Toast.makeText(teacherforgotpass.this, "Failed to send reset email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(teacherforgotpass.this, "Could not find email for this username", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                        } else {
                            Toast.makeText(teacherforgotpass.this, "Username not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(teacherforgotpass.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
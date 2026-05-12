package com.example.umquest;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.SharedPreferences;

public class studentlogin extends AppCompatActivity {

    private static final String TAG = "studentlogin";
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_studentlogin);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://umquest-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mRef = mDatabase.getReference("Users").child("Students");

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

        ImageView backgroundGif = findViewById(R.id.backgroundGif3);

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

        EditText textUsernameStd = findViewById(R.id.textUsernameStd);
        EditText textPasswordStd = findViewById(R.id.textPasswordStd);
        Button btn_loginstd = findViewById(R.id.btn_loginstd);
        TextView signupstd = findViewById(R.id.textloginstd);
        TextView forgotpass1 = findViewById(R.id.forgotpass1);

        btn_loginstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textUsernameStd.getText().toString().trim();
                String password = textPasswordStd.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(studentlogin.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Query database to find the email associated with the username
                mRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // There should be only one match, get the first one
                            for (DataSnapshot userSnap : snapshot.getChildren()) {
                                String email = userSnap.child("email").getValue(String.class);
                                
                                if (email != null) {
                                    // Log in using the retrieved email
                                    mAuth.signInWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(studentlogin.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "signInWithEmail:success");

                                                        SharedPreferences prefs = getSharedPreferences("UMQuestPrefs", MODE_PRIVATE);
                                                        prefs.edit().putString("user_role", "student").apply();

                                                        // Navigate to dashboard
                                                        Intent intent = new Intent(studentlogin.this, dashboardStd.class);
                                                        startActivity(intent);
                                                        finish();
                                                        overridePendingTransition(R.anim.slide_up, R.anim.stay);
                                                    } else {
                                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                                        Toast.makeText(studentlogin.this, "Authentication failed: " + task.getException().getMessage(),
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(studentlogin.this, "Could not find this username", Toast.LENGTH_SHORT).show();
                                }
                                break; // Found the user, exit loop
                            }
                        } else {
                            Toast.makeText(studentlogin.this, "Username not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "databaseError", error.toException());
                        Toast.makeText(studentlogin.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        forgotpass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(studentlogin.this, studentforgotpass.class);
                View logo = findViewById(R.id.imageButton);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(studentlogin.this, 
                        logo, "logo_shared_element");
                startActivity(intent, options.toBundle());
            }
        });

        signupstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(studentlogin.this, studentsignup.class);
                View logo = findViewById(R.id.imageButton);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(studentlogin.this, 
                        logo, "logo_shared_element");
                startActivity(intent, options.toBundle());
            }
        });
    }
}
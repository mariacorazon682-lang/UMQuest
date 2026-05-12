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
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import android.content.SharedPreferences;

public class studentsignup extends AppCompatActivity {

    private static final String TAG = "studentsignup";
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private boolean isTermsAgreed = false;
    private ActivityResultLauncher<Intent> termsLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_studentrsignup);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://umquest-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mRef = mDatabase.getReference("Users").child("Students");

        DatabaseReference myRef = mDatabase.getReference("message");
        myRef.setValue("Welcome to UMquest");

        termsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        isTermsAgreed = true;
                        Toast.makeText(this, "Terms Agreed", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

        ImageView backgroundGif = findViewById(R.id.backgroundGifSignup1);

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

        EditText userstdsgn = findViewById(R.id.userstdsgn);
        EditText emailstdsgn = findViewById(R.id.emailstdsgn);
        EditText studentId = findViewById(R.id.studentId);
        EditText passwordtsdsgn = findViewById(R.id.passwordtsdsgn);
        EditText confirmpasswordstdsgn = findViewById(R.id.confirmpasswordstdsgn);
        Button btn_create = findViewById(R.id.btn_create);
        TextView textSignIn = findViewById(R.id.textSignIn);
        TextView terms1 = findViewById(R.id.terms1);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userstdsgn.getText().toString().trim();
                String email = emailstdsgn.getText().toString().trim();
                String id = studentId.getText().toString().trim();
                String pass = passwordtsdsgn.getText().toString().trim();
                String confirmPass = confirmpasswordstdsgn.getText().toString().trim();

                if (!isTermsAgreed) {
                    Toast.makeText(studentsignup.this, "Please read and agree to the Terms and Condition", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (username.isEmpty() || email.isEmpty() || id.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(studentsignup.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass.equals(confirmPass)) {
                    Toast.makeText(studentsignup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pass.length() < 6) {
                    Toast.makeText(studentsignup.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(studentsignup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmailAndPassword:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String uid = user.getUid();
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("username", username);
                                map.put("email", email);
                                map.put("studentId", id);
                                map.put("role", "student");

                                mRef.child(uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> dbTask) {
                                        if (dbTask.isSuccessful()) {
                                            Toast.makeText(studentsignup.this, "Student Account Created", Toast.LENGTH_SHORT).show();
                                        
                                        SharedPreferences prefs = getSharedPreferences("UMQuestPrefs", MODE_PRIVATE);
                                        prefs.edit().putString("user_role", "student").apply();
                                        
                                        // Navigate to dashboard
                                        Intent intent = new Intent(studentsignup.this, dashboardStd.class);
                                        startActivity(intent);
                                        finish();
                                        overridePendingTransition(R.anim.slide_up, R.anim.stay);
                                        } else {
                                            Log.w(TAG, "saveUserData:failure", dbTask.getException());
                                            Toast.makeText(studentsignup.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.w(TAG, "createUserWithEmailAndPassword:failure", task.getException());
                            Toast.makeText(studentsignup.this, "Authentication failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        terms1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(studentsignup.this, termsandcondition.class);
                termsLauncher.launch(intent);
            }
        });

        textSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(studentsignup.this, studentlogin.class);
                View logo = findViewById(R.id.logo_signup);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(studentsignup.this, 
                        logo, "logo_shared_element");
                startActivity(intent, options.toBundle());
                finish();
            }
        });
    }
}

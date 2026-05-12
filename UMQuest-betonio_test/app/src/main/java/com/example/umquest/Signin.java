package com.example.umquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Signin extends AppCompatActivity {

    private EditText usertext;
    private EditText umdctext;
    private EditText passtext;

    private ImageButton login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signhere), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usertext = findViewById(R.id.username_login);
        umdctext = findViewById(R.id.umdcacc_login);
        passtext = findViewById(R.id.password_login);
        login =  findViewById(R.id.loginbutton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextPage();
            }
        });
    }

    public void openNextPage() {

        String myUsername = "Myacc";
        String myUmacc = "me.umdc@gmail.com";
        String mypassword = "trylang";

        String inputEmail = usertext.getText().toString();
        String inputUmdc = umdctext.getText().toString();
        String inputPass = passtext.getText().toString();

        if (inputEmail.equals(myUsername) && inputPass.equals(mypassword) && inputUmdc.equals(myUmacc)) {
            Intent intent = new Intent(this, student_home.class);
            startActivity(intent);
        } else {
            usertext.setText("");
            passtext.setText("");
            umdctext.setText("");
            umdctext.setError("Umdc account does not Exist");
            usertext.setError("Invalid Username");
            passtext.setError("Invalid Password");
        }
    }
}
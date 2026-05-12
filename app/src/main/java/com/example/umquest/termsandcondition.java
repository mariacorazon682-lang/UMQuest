package com.example.umquest;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class termsandcondition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_termsandcondition);

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

        ImageView backgroundGif = findViewById(R.id.backgroundGifTerms);

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

        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnAgree = findViewById(R.id.btnAgree);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set result to OK so the calling activity knows the user agreed
                setResult(RESULT_OK);
                finish();
            }
        });

        setupTermsText();
    }

    private void setupTermsText() {
        TextView termsText = findViewById(R.id.termsText);
        String htmlTerms = "<h2>Terms and Conditions for UMQuest</h2>" +
                "<p><b>Last Updated:</b> May 11, 2026</p>" +
                "<p>Welcome to <b>UMQuest</b>. By accessing or using this application, you agree to follow these Terms and Conditions. Please read them carefully before using the app.</p>" +
                "<hr>" +
                "<h3>1. Purpose of UMQuest</h3>" +
                "<p>UMQuest is an educational platform designed for students and teachers to support learning, communication, academic activities, and educational collaboration.</p>" +
                "<p>The app is intended strictly for:</p>" +
                "<ul>" +
                "  <li>Educational use</li>" +
                "  <li>Classroom learning</li>" +
                "  <li>Academic collaboration</li>" +
                "  <li>School-related activities</li>" +
                "</ul>" +
                "<p>Users must not use the platform for illegal, harmful, or non-educational purposes.</p>" +
                "<hr>" +
                "<h3>2. User Eligibility</h3>" +
                "<p>UMQuest may only be used by:</p>" +
                "<ul>" +
                "  <li>Registered students</li>" +
                "  <li>Teachers</li>" +
                "  <li>School administrators</li>" +
                "  <li>Authorized educational users</li>" +
                "</ul>" +
                "<p>Users must provide accurate information during registration and keep their account information secure.</p>" +
                "<hr>" +
                "<h3>3. User Responsibilities</h3>" +
                "<p>By using UMQuest, users agree to:</p>" +
                "<ul>" +
                "  <li>Use respectful and appropriate language</li>" +
                "  <li>Follow school policies and academic rules</li>" +
                "  <li>Protect their account credentials</li>" +
                "  <li>Use the platform responsibly</li>" +
                "  <li>Submit only appropriate educational content</li>" +
                "</ul>" +
                "<p>Users must not:</p>" +
                "<ul>" +
                "  <li>Share false information</li>" +
                "  <li>Harass, bully, or threaten others</li>" +
                "  <li>Upload harmful software or malicious files</li>" +
                "  <li>Attempt to hack, damage, or disrupt the system</li>" +
                "  <li>Use the app for cheating or academic dishonesty</li>" +
                "</ul>" +
                "<hr>" +
                "<h3>4. Educational Content</h3>" +
                "<p>All uploaded materials, assignments, messages, and learning resources must be related to educational purposes.</p>" +
                "<p>Teachers are responsible for the content they provide to students, while students are responsible for ensuring their submissions are original and appropriate.</p>" +
                "<p>UMQuest reserves the right to remove content that violates these Terms and Conditions.</p>" +
                "<hr>" +
                "<h3>5. Privacy and Data Protection</h3>" +
                "<p>UMQuest values user privacy and works to protect personal information.</p>" +
                "<p>User data collected through the app may include:</p>" +
                "<ul>" +
                "  <li>Names</li>" +
                "  <li>School information</li>" +
                "  <li>Academic records</li>" +
                "  <li>Messages and submissions</li>" +
                "</ul>" +
                "<p>This information is used only for educational and platform-related purposes.</p>" +
                "<p>Users should not share sensitive personal information publicly within the app.</p>" +
                "<hr>" +
                "<h3>6. Intellectual Property</h3>" +
                "<p>All app designs, logos, features, and system content of UMQuest are protected by intellectual property laws.</p>" +
                "<p>Users may not:</p>" +
                "<ul>" +
                "  <li>Copy the app</li>" +
                "  <li>Modify system features</li>" +
                "  <li>Redistribute app content without permission</li>" +
                "  <li>Use UMQuest branding without authorization</li>" +
                "</ul>" +
                "<p>Users retain ownership of their own academic submissions and uploaded content.</p>" +
                "<hr>" +
                "<h3>7. Account Suspension or Termination</h3>" +
                "<p>UMQuest reserves the right to suspend or terminate accounts that:</p>" +
                "<ul>" +
                "  <li>Violate these Terms and Conditions</li>" +
                "  <li>Misuse the platform</li>" +
                "  <li>Engage in harmful or inappropriate behavior</li>" +
                "  <li>Attempt unauthorized access to the system</li>" +
                "</ul>" +
                "<p>Serious violations may also be reported to school authorities.</p>" +
                "<hr>" +
                "<h3>8. System Availability</h3>" +
                "<p>While UMQuest aims to provide reliable service, the app may occasionally experience:</p>" +
                "<ul>" +
                "  <li>Maintenance downtime</li>" +
                "  <li>Technical issues</li>" +
                "  <li>System updates</li>" +
                "  <li>Temporary interruptions</li>" +
                "</ul>" +
                "<p>UMQuest is not liable for losses caused by temporary unavailability of the service.</p>" +
                "<hr>" +
                "<h3>9. Limitation of Liability</h3>" +
                "<p>UMQuest is provided as an educational tool. While efforts are made to maintain accuracy and reliability, the platform does not guarantee uninterrupted or error-free operation.</p>" +
                "<p>Users are responsible for how they use the information and features provided within the app.</p>" +
                "<hr>" +
                "<h3>10. Changes to Terms and Conditions</h3>" +
                "<p>UMQuest may update these Terms and Conditions at any time to improve the platform or comply with school policies and legal requirements.</p>" +
                "<p>Users will be informed of significant updates when necessary.</p>" +
                "<hr>" +
                "<h3>11. Acceptance of Terms</h3>" +
                "<p>By creating an account or using UMQuest, users confirm that they have read, understood, and agreed to these Terms and Conditions.</p>" +
                "<hr>" +
                "<h3>12. Contact Information</h3>" +
                "<p>For questions, concerns, or support regarding UMQuest, users may contact the app administrators or their educational institution.</p>" +
                "<hr>" +
                "<p align=\"center\">© 2026 UMQuest. All Rights Reserved.</p>";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            termsText.setText(Html.fromHtml(htmlTerms, Html.FROM_HTML_MODE_LEGACY));
        } else {
            termsText.setText(Html.fromHtml(htmlTerms));
        }
    }
}

package com.zeekzakatgold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeekzakatgold.R;

public class AboutUsActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        textView = findViewById(R.id.textView10);

        textView.setOnClickListener(view -> {
            Uri uri = Uri.parse(textView.getText().toString());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        ImageView imageview = (ImageView) findViewById(R.id.imageView);
        imageview.setImageResource(R.drawable.haziq);
    }
}
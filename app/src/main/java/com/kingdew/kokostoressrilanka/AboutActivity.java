package com.kingdew.kokostoressrilanka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.skydoves.elasticviews.ElasticImageView;

public class AboutActivity extends AppCompatActivity {
    ElasticImageView github,gmail,facebook,whatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        MobileAds.initialize(this);

        github=findViewById(R.id.github);
        gmail=findViewById(R.id.gmail);
        facebook=findViewById(R.id.facebook);
        whatsapp=findViewById(R.id.whatsapp);

        AdView mAdView;
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        github.setOnClickListener(view -> {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://github.com/shendew"));
            startActivity(i);
        });

        gmail.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW)
                    .setType("plain/text")
                    .setData(Uri.parse("sahokavishka@gmail.com"))
                    .setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail")
                    .putExtra(Intent.EXTRA_EMAIL, new String[]{"sahokavishka@gmail.com"})
                    .putExtra(Intent.EXTRA_SUBJECT, "")
                    .putExtra(Intent.EXTRA_TEXT, "This is a message sent from my KokoStores app :-)");
            startActivity(intent);
        });

        facebook.setOnClickListener(view -> {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://facebook.com/profile.php?id=100093382697939"));
            startActivity(i);
        });

        whatsapp.setOnClickListener(view -> {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://api.whatsapp.com/send?phone=94764247796&text=Koko%20Shop%20List%20App"));
            startActivity(i);
        });
    }
}
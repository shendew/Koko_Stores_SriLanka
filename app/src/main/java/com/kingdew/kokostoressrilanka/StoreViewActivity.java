package com.kingdew.kokostoressrilanka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.kingdew.kokostoressrilanka.Adapters.ContactAdapter;
import com.kingdew.kokostoressrilanka.Adapters.TagAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class StoreViewActivity extends AppCompatActivity {
    String Stitle,Saddress,Scontact,Stags,Sweb,Slogo,Sdesc;
    ArrayList<String> tags,contact;
    TextView title,desc,address;
    TagAdapter tagAdapter;
    ContactAdapter contactAdapter;
    RecyclerView contact_rv,tag_rv;
    AppCompatButton view;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_view);
        MobileAds.initialize(this);

        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        address=findViewById(R.id.address);
        contact_rv=findViewById(R.id.contact_rv);
        tag_rv=findViewById(R.id.tag_rv);
        view=findViewById(R.id.Visitbutton);
        logo=findViewById(R.id.logo);

        AdView mAdView;
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        contact_rv.setLayoutManager(new LinearLayoutManager(this));
        tag_rv.setLayoutManager(new GridLayoutManager(this,4));

        Stitle=getIntent().getStringExtra("title");
        Saddress=getIntent().getStringExtra("address");
        Scontact=getIntent().getStringExtra("contact");
        Stags=getIntent().getStringExtra("tags");
        Sweb=getIntent().getStringExtra("web");
        Slogo=getIntent().getStringExtra("logo");
        Sdesc=getIntent().getStringExtra("desc");

        if (!logo.equals("no")){
            Glide.with(this).load(Slogo).into(logo);
        }


        contact= new ArrayList<>(Arrays.asList(Scontact.split(",")));
        tags=new ArrayList<>(Arrays.asList(Stags.split(",")));

        title.setText(Stitle);
        address.setText(Saddress);
        desc.setText(Sdesc);


        tagAdapter=new TagAdapter(this,tags);
        contactAdapter=new ContactAdapter(this,contact);


        if (!Stags.equals("no")){
            tag_rv.setAdapter(tagAdapter);
        }
        if (!Scontact.equals("no")){
            contact_rv.setAdapter(contactAdapter);
        }

        view.setOnClickListener(view1 -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Sweb));
            startActivity(i);
        });
    }
}
package com.kingdew.kokostoressrilanka.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.kingdew.kokostoressrilanka.HomeActivity;
import com.kingdew.kokostoressrilanka.Models.Store;
import com.kingdew.kokostoressrilanka.R;
import com.kingdew.kokostoressrilanka.StoreViewActivity;

import java.util.ArrayList;

public class StoreAdaper extends RecyclerView.Adapter<StoreAdaper.ViewHolder> {

    ArrayList<Store> stores;
    private RequestBuilder<PictureDrawable> requestBuilder;
    Context context;
    private InterstitialAd mInterstitialAd;
    Store clickedStore;
    int showed=0;

    public StoreAdaper(Context context) {
        this.context = context;
    }

    public StoreAdaper(HomeActivity homeActivity, ArrayList<Store> stores) {
        this.stores=stores;
        this.context=homeActivity;

        loadAd();


    }

    public void UpdateStores(ArrayList<Store> uStores){
        this.stores=uStores;
    }

    @NonNull
    @Override
    public StoreAdaper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdaper.ViewHolder holder, int position) {
        Store store=stores.get(position);

        holder.title.setText(store.getShopName());
        if (!store.getShopLogo().equals("no")){
            Glide.with(context).load(store.getShopLogo()).fitCenter().into(holder.img);
        }


        holder.itemView.setOnClickListener(view -> {
            clickedStore=store;
            if (mInterstitialAd != null){
                if (showed%2==0){
                    mInterstitialAd.show((Activity) context);
                }else {
                    itemClicked();
                }
                showed++;
            }else{
                itemClicked();
            }

        });
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.title);
        }
    }

    private void loadAd(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context,context.getResources().getString(R.string.store_inter_ad), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;

                        Toast.makeText(context, "loaded"+showed, Toast.LENGTH_SHORT).show();
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                itemClicked();
                                mInterstitialAd = null;

                                loadAd();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                itemClicked();
                                mInterstitialAd = null;
                               // loadAd();

                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                //mInterstitialAd=null;

                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error

                        mInterstitialAd = null;
                    }
                });
    }

    private void itemClicked(){
        context.startActivity(new Intent(context,StoreViewActivity.class)
                .putExtra("title",clickedStore.getShopName())
                .putExtra("logo",clickedStore.getShopLogo())
                .putExtra("web",clickedStore.getShopWebsite())
                .putExtra("address",clickedStore.getShopAddress())
                .putExtra("tags",clickedStore.getShopTags())
                .putExtra("contact",clickedStore.getShopContact())
                .putExtra("desc",clickedStore.getShopDesc())

        );
    }


}

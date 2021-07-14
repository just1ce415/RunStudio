package com.roflanRun.CulComf;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class CollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        String CollectionName = SavedData.Collection;

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Back");
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        try {
            dbHelper.openDataBase();
        } catch (SQLException sqle){
            throw sqle;
        }

        int count = dbHelper.getBootsCount();
        for (int i = 1; i <= count; i++) {
            Boot boot = dbHelper.getBoot(i);
            if (boot.getPurpose().equals(CollectionName)){
                SavedData.ListOfCollectionIds.add(i);
            }
        }

        Typeface Anton = Typeface.createFromAsset(getAssets(), "fonts/antonio.regular.ttf");


        LinearLayout conteiner = findViewById(R.id.containerInCollectionActivity);

        int counttemp;
        if (SavedData.ListOfCollectionIds.size() == (SavedData.ListOfCollectionIds.size() / 2 * 2)) {
            counttemp = SavedData.ListOfCollectionIds.size() / 2;
        }
        else {
            counttemp = SavedData.ListOfCollectionIds.size() / 2 + 1;
        }

        int k = 0;

        for (int i = 0; i < counttemp; i++) {
            LinearLayout llv = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.vertical_frag_layout, conteiner, false);
            conteiner.addView(llv);

            int var;
            if (((i + 1) * 2 - SavedData.ListOfCollectionIds.size()) == 1){
                var = 1;
            } else {
                var = 2;
            }
            for (int j = 0; j < var; j++) {
                Boot sneaker = dbHelper.getBoot(SavedData.ListOfCollectionIds.get(k));

                RelativeLayout rlel = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.boot_reference_frag,
                        llv, false);
                llv.addView(rlel);

                ImageView imgmain = (ImageView) LayoutInflater.from(this).inflate(R.layout.main_bootimg,
                        rlel, false);
                rlel.addView(imgmain);

                TextView bootname = (TextView) LayoutInflater.from(this).inflate(R.layout.boot_name,
                        rlel, false);
                rlel.addView(bootname);
                bootname.setTypeface(Anton);

                final int tmp = k;
                (rlel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CollectionActivity.this, BootActivity.class);
                        SavedData.BootId = SavedData.ListOfCollectionIds.get(tmp);
                        startActivity(intent);
                    }
                });

                try {
                    bootname.setText(sneaker.getName());
                } catch (Exception e) {
                    Log.i("ML", "kak tak to???");
                }

                String imgname = sneaker.getImages();
                imgname = imgname + "1";

                try {
                    int resID = getResources().getIdentifier(imgname, "drawable", "" + SavedData.APP_PACKEGE);
                    Picasso.with(this).load(resID).into(imgmain);
                } catch (Exception e) {
                    Log.i("ML", "No image");
                }

                try {
                    switch (sneaker.getPurpose()) {
                        case "Basketball":
                            rlel.setBackground(ContextCompat.getDrawable(this, R.drawable.tlo_basket));
                            break;
                        case "Running":
                            rlel.setBackground(ContextCompat.getDrawable(this, R.drawable.tlo_run));
                            break;
                    }
                }catch (Exception e){
                    Log.i("ML", "kak tak to x2???");
                }
                k++;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SavedData.ListOfCollectionIds.clear();
        SavedData.Collection = "";
    }
}

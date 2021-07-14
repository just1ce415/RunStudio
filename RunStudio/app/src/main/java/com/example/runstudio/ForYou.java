package com.roflanRun.CulComf;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ForYou extends Fragment {

    private DatabaseHelper databaseHelper = null;


    public ForYou() {
        // Required empty public constructor
    }

    public  void  setdb(DatabaseHelper db)
    {
        databaseHelper  = db;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_for_you, container, false);

        try {
            databaseHelper.openDataBase();
        } catch (SQLException sqle){
            throw sqle;
        }

        SavedData.ListOfBootIds = new SavedData().GetIds(databaseHelper);

        Log.i("Main Log", "" + SavedData.Gender + " " + SavedData.Weight + " " + SavedData.Pronacia +
                " " + SavedData.ReamWidth + " " + SavedData.ListOfBootIds);

        Context context = Objects.requireNonNull(getActivity()).getApplicationContext();
        LinearLayout ll_conteiner = layout.findViewById(R.id.LL_container);

        Typeface Anton = Typeface.createFromAsset(context.getAssets(), "fonts/antonio.regular.ttf");

        int count;
        if (SavedData.ListOfBootIds.size() == (SavedData.ListOfBootIds.size() / 2 * 2)) {
            count = SavedData.ListOfBootIds.size() / 2;
        }
        else {
            count = SavedData.ListOfBootIds.size() / 2 + 1;
        }

        int k = 0;

        for (int i = 0; i < count; i++) {
            LinearLayout llv = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.vertical_frag_layout, ll_conteiner, false);
            ll_conteiner.addView(llv);

            int var;
            if (((i + 1) * 2 - SavedData.ListOfBootIds.size()) == 1){
                var = 1;
            } else {
                var = 2;
            }
            for (int j = 0; j < var; j++) {
                Boot sneaker = databaseHelper.getBoot(SavedData.ListOfBootIds.get(k));

                RelativeLayout rlel = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.boot_reference_frag,
                        llv, false);
                llv.addView(rlel);

                ImageView imgmain = (ImageView) LayoutInflater.from(context).inflate(R.layout.main_bootimg,
                        rlel, false);
                rlel.addView(imgmain);

                TextView bootname = (TextView) LayoutInflater.from(context).inflate(R.layout.boot_name,
                        rlel, false);
                rlel.addView(bootname);
                bootname.setTypeface(Anton);

                final int tmp = k;
                (rlel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), BootActivity.class);
                        SavedData.BootId = SavedData.ListOfBootIds.get(tmp);
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
                    Picasso.with(context).load(resID).into(imgmain);
                } catch (Exception e) {
                    Log.i("ML", "No image");
                }

                try {
                    switch (sneaker.getPurpose()) {
                        case "Basketball":
                            rlel.setBackground(ContextCompat.getDrawable(context, R.drawable.tlo_basket));
                            break;
                        case "Running":
                            rlel.setBackground(ContextCompat.getDrawable(context, R.drawable.tlo_run));
                            break;
                    }
                }catch (Exception e){
                    Log.i("ML", "kak tak to x2???");
                }
                k++;
            }
        }

        return layout;
    }

}

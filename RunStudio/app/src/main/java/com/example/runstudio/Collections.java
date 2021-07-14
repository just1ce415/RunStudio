package com.roflanRun.CulComf;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Collections extends Fragment {


    public Collections() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_collections, container, false);

        Context context = Objects.requireNonNull(getActivity()).getApplicationContext();
        LinearLayout ll_conteiner = layout.findViewById(R.id.Collections_container);

        ImageView imgcollrun = (ImageView) LayoutInflater.from(context).inflate(R.layout.collectionimg, ll_conteiner, false);
        ll_conteiner.addView(imgcollrun);
        String imgnamerun = "running";
        try {
            int resID = getResources().getIdentifier(imgnamerun, "drawable", "" + SavedData.APP_PACKEGE);
            imgcollrun.setImageResource(resID);
        } catch (Exception e) {
            Log.i("ML", "No image");
        }
        imgcollrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CollectionActivity.class);
                SavedData.Collection = "Running";
                startActivity(intent);
            }
        });

        ImageView imgcollbask = (ImageView) LayoutInflater.from(context).inflate(R.layout.collectionimg, ll_conteiner, false);
        ll_conteiner.addView(imgcollbask);
        String imgnamebask = "basketball";
        try {
            int resID = getResources().getIdentifier(imgnamebask, "drawable", "" + SavedData.APP_PACKEGE);
            imgcollbask.setImageResource(resID);
        } catch (Exception e) {
            Log.i("ML", "No image");
        }
        imgcollbask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CollectionActivity.class);
                SavedData.Collection = "Basketball";
                startActivity(intent);
            }
        });


        return layout;
    }

}

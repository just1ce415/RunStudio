package com.roflanRun.CulComf;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class Pronation extends AppCompatActivity {

    ImageView next;
    TextView HowTo;
    RadioButton rbp1;
    RadioButton rbp2;
    RadioButton rbp3;
    ImageView rbpimg1;
    ImageView rbpimg2;
    ImageView rbpimg3;
    TextView rbptv1;
    TextView rbptv2;
    TextView rbptv3;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronation);

        SavedData.Pronacia = 0;

        next = findViewById(R.id.next3);
        next.setVisibility(View.INVISIBLE);
        next.setEnabled(false);

        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setTitle("Tell about yourself");
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Main Log", "" + SavedData.Gender + " " + SavedData.Weight + " " + SavedData.Pronacia +
                        " " + SavedData.ReamWidth + " " + SavedData.ListOfBootIds);
                startActivity(new Intent(Pronation.this, ReamWidth.class));
            }
        });

        rbp1 = findViewById(R.id.rbp1);
        rbp2 = findViewById(R.id.rbp2);
        rbp3 = findViewById(R.id.rbp3);

        rbp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(1);
            }
        });
        rbp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(2);
            }
        });
        rbp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(3);
            }
        });

        rbpimg1 = findViewById(R.id.rbrimg1);
        rbpimg2 = findViewById(R.id.rbrimg2);
        rbpimg3 = findViewById(R.id.rbrimg3);
        rbptv1 = findViewById(R.id.rbptv1);
        rbptv2 = findViewById(R.id.rbptv2);
        rbptv3 = findViewById(R.id.rbptv3);
        rbpimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(1);
            }
        });
        rbptv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(1);
            }
        });
        rbpimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(2);
            }
        });
        rbptv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(2);
            }
        });
        rbpimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(3);
            }
        });
        rbptv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(3);
            }
        });

        HowTo = findViewById(R.id.howto1);
        HowTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedData.IsPronacia = true;
                startActivity(new Intent(Pronation.this, Description.class));
            }
        });

        Typeface Anton_bold = Typeface.createFromAsset(getAssets(), "fonts/antonio.regular.ttf");
        Typeface Anton_light = Typeface.createFromAsset(getAssets(), "fonts/antonio.light.ttf");

        title = findViewById(R.id.TellAbPron);
        title.setTypeface(Anton_bold);
        rbptv1.setTypeface(Anton_light);
        rbptv2.setTypeface(Anton_light);
        rbptv3.setTypeface(Anton_light);
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

    private void RadioCheck (int button){
        switch (button){
            case 1:
                if (rbp2.isChecked()){
                    rbp2.setChecked(false);
                    rbp1.setChecked(true);
                }
                else if (rbp3.isChecked()){
                    rbp3.setChecked(false);
                    rbp1.setChecked(true);
                }
                else {
                    rbp1.setChecked(true);
                    next.setEnabled(true);
                    next.setVisibility(View.VISIBLE);
                }
                SavedData.Pronacia = 1;
                break;
            case 2:
                if (rbp1.isChecked()){
                    rbp1.setChecked(false);
                    rbp2.setChecked(true);
                }
                else if (rbp3.isChecked()){
                    rbp3.setChecked(false);
                    rbp2.setChecked(true);
                }
                else {
                    rbp2.setChecked(true);
                    next.setEnabled(true);
                    next.setVisibility(View.VISIBLE);
                }
                SavedData.Pronacia = 2;
                break;
            case 3:
                if (rbp1.isChecked()){
                    rbp1.setChecked(false);
                    rbp3.setChecked(true);
                }
                else if (rbp2.isChecked()){
                    rbp2.setChecked(false);
                    rbp3.setChecked(true);
                }
                else {
                    rbp3.setChecked(true);
                    next.setEnabled(true);
                    next.setVisibility(View.VISIBLE);
                }
                SavedData.Pronacia = 3;
                break;
                default: return;
        }
    }
}

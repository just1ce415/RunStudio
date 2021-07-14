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

public class ReamWidth extends AppCompatActivity {

    ImageView next;
    TextView HowTo;
    RadioButton rbr1;
    RadioButton rbr2;
    RadioButton rbr3;
    TextView rbrtv1;
    TextView rbrtv2;
    TextView rbrtv3;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ream_width);

        SavedData.ReamWidth = 0;

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Tell about yourself");
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        next = findViewById(R.id.next3);
        next.setVisibility(View.INVISIBLE);
        next.setEnabled(false);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SavedData.IsRegister = true;
                Log.i("Main Log", "" + SavedData.Gender + " " + SavedData.Weight + " " + SavedData.Pronacia +
                        " " + SavedData.ReamWidth + " " + SavedData.ListOfBootIds);
                Intent intent = new Intent(ReamWidth.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        rbr1 = findViewById(R.id.rbr1);
        rbr2 = findViewById(R.id.rbr2);
        rbr3 = findViewById(R.id.rbr3);

        rbr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(1);
            }
        });
        rbr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(2);
            }
        });
        rbr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(3);
            }
        });

        rbrtv1 = findViewById(R.id.rbrtv1);
        rbrtv2 = findViewById(R.id.rbrtv2);
        rbrtv3 = findViewById(R.id.rbrtv3);
        rbrtv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(1);
            }
        });
        rbrtv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(2);
            }
        });
        rbrtv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioCheck(3);
            }
        });

        HowTo = findViewById(R.id.howto2);
        HowTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedData.IsPronacia = false;
                startActivity(new Intent(ReamWidth.this, Description.class));
            }
        });

        Typeface Anton_bold = Typeface.createFromAsset(getAssets(), "fonts/antonio.regular.ttf");
        Typeface Anton_light = Typeface.createFromAsset(getAssets(), "fonts/antonio.light.ttf");

        title = findViewById(R.id.TellAbReam);
        title.setTypeface(Anton_bold);
        rbrtv1.setTypeface(Anton_light);
        rbrtv2.setTypeface(Anton_light);
        rbrtv3.setTypeface(Anton_light);
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
                if (rbr2.isChecked()){
                    rbr2.setChecked(false);
                    rbr1.setChecked(true);
                }
                else if (rbr3.isChecked()){
                    rbr3.setChecked(false);
                    rbr1.setChecked(true);
                }
                else {
                    rbr1.setChecked(true);
                    next.setEnabled(true);
                    next.setVisibility(View.VISIBLE);
                }
                SavedData.ReamWidth = 1;
                break;
            case 2:
                if (rbr1.isChecked()){
                    rbr1.setChecked(false);
                    rbr2.setChecked(true);
                }
                else if (rbr3.isChecked()){
                    rbr3.setChecked(false);
                    rbr2.setChecked(true);
                }
                else {
                    rbr2.setChecked(true);
                    next.setEnabled(true);
                    next.setVisibility(View.VISIBLE);
                }
                SavedData.ReamWidth = 2;
                break;
            case 3:
                if (rbr1.isChecked()){
                    rbr1.setChecked(false);
                    rbr3.setChecked(true);
                }
                else if (rbr2.isChecked()){
                    rbr2.setChecked(false);
                    rbr3.setChecked(true);
                }
                else {
                    rbr3.setChecked(true);
                    next.setEnabled(true);
                    next.setVisibility(View.VISIBLE);
                }
                SavedData.ReamWidth = 3;
                break;
            default: return;
        }
    }
}

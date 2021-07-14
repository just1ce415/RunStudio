package com.roflanRun.CulComf;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GenderWeight extends AppCompatActivity {

    ImageView next;
    TextView tap;
    TextView vals;
    EditText ed;
    TextView gender;
    TextView weight;
    TextView Tell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_weight);

        SavedData.Gender = "";
        SavedData.Weight = 0.0f;

        next = findViewById(R.id.next1);
        next.setVisibility(View.INVISIBLE);
        next.setEnabled(false);

        Typeface Anton_bold = Typeface.createFromAsset(getAssets(), "fonts/antonio.regular.ttf");
        Typeface Anton_light = Typeface.createFromAsset(getAssets(), "fonts/antonio.light.ttf");

        final boolean[] isKg = new boolean[2];

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
                startActivity(new Intent(GenderWeight.this, Pronation.class));
            }
        });

        tap = findViewById(R.id.TapToGender);
        final String[] arrgen = {"Male", "Female"};
        tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog ad = new AlertDialog.Builder(GenderWeight.this).setTitle("Choose your gender")
                        .setCancelable(true)
                        .setSingleChoiceItems(arrgen, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SavedData.Gender = arrgen[which];
                                tap.setText(arrgen[which]);
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!SavedData.Gender.equals("") && SavedData.Weight != 0 && isKg[1]){
                                    next.setVisibility(View.VISIBLE);
                                    next.setEnabled(true);
                                }
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        vals = findViewById(R.id.TapToWeight);
        final String[] arrwei = {"kg", "pounds"};
        vals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog ad1 = new AlertDialog.Builder(GenderWeight.this).setTitle("Choose units")
                        .setCancelable(true)
                        .setSingleChoiceItems(arrwei, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (arrwei[which].equals(arrwei[0])){
                                    isKg[0] = true;
                                }
                                isKg[1] = true;
                                vals.setText(arrwei[which]);
                                if(isKg[0]){
                                    SavedData.Weight /= 0.453592f;
                                }
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!(SavedData.Gender).equals("") && SavedData.Weight != 0 && isKg[1]){
                                    next.setVisibility(View.VISIBLE);
                                    next.setEnabled(true);
                                }
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        ed = findViewById(R.id.Value);
        ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    try {
                        if (!isKg[0]) {
                            SavedData.Weight = (float)(Integer.parseInt(v.getText().toString())) * 0.453592f;
                        }
                        else{
                            SavedData.Weight = (float)Integer.parseInt(v.getText().toString());
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    handled = true;
                }
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                if (!(SavedData.Gender.equals("")) && SavedData.Weight != 0 && isKg[1]){
                    next.setVisibility(View.VISIBLE);
                    next.setEnabled(true);
                }

                return handled;
            }
        });

        gender = findViewById(R.id.main_gender);
        weight = findViewById(R.id.main_weight);
        Tell = findViewById(R.id.TellAb);

        tap.setTypeface(Anton_light);
        vals.setTypeface(Anton_light);
        ed.setTypeface(Anton_light);
        gender.setTypeface(Anton_light);
        weight.setTypeface(Anton_light);
        Tell.setTypeface(Anton_bold);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishAffinity();
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}


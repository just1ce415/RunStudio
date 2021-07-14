package com.roflanRun.CulComf;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


public class StartMenu extends AppCompatActivity implements View.OnTouchListener {

    TextView start;
    TextView title;
    Animation anim;
    float x1;
    float x2;
    float x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.hide();
        }

        if(this.getSharedPreferences(SavedData.APP_PREFERENCES, MODE_PRIVATE).getBoolean(SavedData.APP_PREFERENCE_REG, false)){
            startActivity(new Intent(StartMenu.this, MainActivity.class));
        }

        Typeface Anton = Typeface.createFromAsset(getAssets(), "fonts/Anton.ttf");
        title = findViewById(R.id.title);
        title.setTypeface(Anton);
        anim = AnimationUtils.loadAnimation(StartMenu.this, R.anim.title_anim);
        title.startAnimation(anim);

        start = findViewById(R.id.Start);
        start.setTypeface(Anton);
        start.setOnTouchListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        v.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPressed));
        x = event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = x;
                break;
            case MotionEvent.ACTION_UP:
                x2 = x;
                if((x1 - x2) >= 0){
                    startActivity(new Intent(StartMenu.this, GenderWeight.class));
                }
                v.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
                break;
        }

        return true;
    }
}

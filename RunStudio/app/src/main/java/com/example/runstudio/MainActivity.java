package com.roflanRun.CulComf;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences pref;
    ImageView sett;
    Animation anim;
    ExpandableRelativeLayout expandableRelativeLayout;

    TextView settSettGen;
    TextView settUnits;
    TextView settSettProcia;
    TextView settSettReamWidth;
    TextView title;
    EditText settVal;
    TextView settGen;
    TextView settProcia;
    TextView settReamWidth;
    TextView settWidth;
    Button apply;
    TextView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //антоны
        Typeface AntonBold = Typeface.createFromAsset(getAssets(), "fonts/Anton.ttf");
        Typeface AntonLight = Typeface.createFromAsset(getAssets(), "fonts/antonio.light.ttf");

        //условия для аппли
        final boolean[] IsSetPronation = new boolean[1];
        final boolean[] IsSetRw = new boolean[1];
        final boolean[] IsSetGender = new boolean[1];
        final boolean[] IsSetWeight = new boolean[1];

        //инициализация БД...
        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        try {
            dbHelper.createDataBase();
        } catch (IOException e){
            throw new Error("Unable to create database");
        }

        try {
            dbHelper.openDataBase();
        } catch (SQLException sqle){
            throw sqle;
        }

        //массив для диалогов
        final boolean isKg[] = new boolean[1];

        //кастомизация акшон бара
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View custom = LayoutInflater.from(this).inflate(R.layout.ab, (ViewGroup)findViewById(R.id.cc));
        home = custom.findViewById(R.id.home);
        home.setTypeface(AntonBold);

        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(false);
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayUseLogoEnabled(false);
            ab.setDisplayShowHomeEnabled(false);
            ab.setElevation(0f);
            ab.setDisplayShowCustomEnabled(true);
            ab.setCustomView(custom, layoutParams);
        }

        //восстановление параметров пользователя
        pref = getSharedPreferences(SavedData.APP_PREFERENCES, MODE_PRIVATE);

        //SavedData.IsRegister = true;
        //SavedData.Gender = pref.getString(SavedData.APP_PREFERENCE_GENDER, "");
        //SavedData.Weight = pref.getFloat(SavedData.APP_PREFERENCE_WEIGHT, 0);
        //SavedData.Pronacia = pref.getInt(SavedData.APP_PREFERENCE_PRONATION, 0);
        //SavedData.ReamWidth = pref.getInt(SavedData.APP_PREFERENCE_REAM_WIDTH, 0);

        //определение настроек - выдвижной лейаут
        sett = findViewById(R.id.settpref);
        expandableRelativeLayout = findViewById(R.id.expandble);
        expandableRelativeLayout.collapse();
        sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.settings_anim);
                sett.startAnimation(anim);
                expandableRelativeLayout.toggle();
            }
        });

        //определение диалогов
        title = findViewById(R.id.tvv1);
        settSettGen = findViewById(R.id.settSettGen);
        settUnits = findViewById(R.id.settUnit);
        settVal = findViewById(R.id.settVal);
        settSettProcia = findViewById(R.id.settSettPronacia);
        settSettReamWidth = findViewById(R.id.settSettReamWidth);
        settGen = findViewById(R.id.settGen);
        settProcia = findViewById(R.id.settPronacia);
        settReamWidth = findViewById(R.id.settReamWidth);
        settWidth = findViewById(R.id.settWei);

        settGen.setTypeface(AntonLight);
        settProcia.setTypeface(AntonLight);
        settWidth.setTypeface(AntonLight);
        settReamWidth.setTypeface(AntonLight);
        title.setTypeface(AntonBold);
        settSettGen.setTypeface(AntonLight);
        settSettProcia.setTypeface(AntonLight);
        settSettReamWidth.setTypeface(AntonLight);
        settUnits.setTypeface(AntonLight);
        settVal.setTypeface(AntonLight);

        //для пола
        final String[] arrgen = {"Male", "Female"};
        settSettGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog ad1 = new AlertDialog.Builder(MainActivity.this).setTitle("Choose your gender")
                        .setCancelable(true)
                        .setSingleChoiceItems(arrgen, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SavedData.Gender = arrgen[which];
                                settSettGen.setText(arrgen[which]);
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                IsSetGender[0] = true;
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

        //единицы измерения
        final String[] arrunit = {"kg", "pounds"};
        settUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog ad2 = new AlertDialog.Builder(MainActivity.this).setTitle("Choose units")
                        .setCancelable(true)
                        .setSingleChoiceItems(arrunit, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (arrunit[which].equals(arrunit[0])){
                                    isKg[0] = true;
                                }
                                settUnits.setText(arrunit[which]);
                                if(isKg[0]){
                                    SavedData.Weight /= 0.453592f;
                                }
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

        //для пронации
        final String[] arrpron = {"Hypopronation", "Normal", "Hyperpronation"};
        settSettProcia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog ad3 = new AlertDialog.Builder(MainActivity.this).setTitle("Choose your pronation")
                        .setCancelable(true)
                        .setSingleChoiceItems(arrpron, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (arrpron[which].equals(arrpron[0])){
                                    SavedData.Pronacia = 1;
                                }
                                else if (arrpron[which].equals(arrpron[1])){
                                    SavedData.Pronacia = 2;
                                }
                                else if (arrpron[which].equals(arrpron[2])){
                                    SavedData.Pronacia = 3;
                                }
                                settSettProcia.setText(arrpron[which]);
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                IsSetPronation[0] = true;
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

        //для рв
        final String[] arream = {"Narrow", "Normal", "Wide"};
        settSettReamWidth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog ad4 = new AlertDialog.Builder(MainActivity.this).setTitle("Choose your ream width")
                        .setCancelable(true)
                        .setSingleChoiceItems(arream, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (arream[which].equals(arream[0])){
                                    SavedData.ReamWidth = 1;
                                }
                                else if (arream[which].equals(arream[1])){
                                    SavedData.ReamWidth = 2;
                                }
                                else if (arream[which].equals(arream[2])){
                                    SavedData.ReamWidth = 3;
                                }
                                settSettReamWidth.setText(arream[which]);
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                IsSetRw[0] = true;
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

        //определение веса
        settVal.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
                    IsSetWeight[0] = true;
                    handled = true;
                }
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                return handled;
            }
        });

        //определение табов
        ViewPager viewPager = findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        ForYou frag1 = new ForYou();
        frag1.setdb(dbHelper);
        adapter.addFragment(frag1, "For You");
        adapter.addFragment(new Collections(), "Collections");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Log.i("Main Log", "" + SavedData.Gender + " " + SavedData.Weight + " " + SavedData.Pronacia +
                " " + SavedData.ReamWidth + " " + SavedData.ListOfBootIds);

        //кнопка аппли, которая ничего не делает
        apply = findViewById(R.id.apply);
        apply.setTypeface(AntonLight);
        apply.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPressed));
                        break;
                    case MotionEvent.ACTION_UP:
                        if (IsSetGender[0] && IsSetPronation[0] && IsSetRw[0] && IsSetWeight[0] && isKg[0]) {
                            SavedData.ListOfBootIds.clear();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                        v.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.border));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //сохранение параметров пользователя
        pref.edit().putBoolean(SavedData.APP_PREFERENCE_REG, SavedData.IsRegister).apply();
        pref.edit().putString(SavedData.APP_PREFERENCE_GENDER, SavedData.Gender).apply();
        pref.edit().putFloat(SavedData.APP_PREFERENCE_WEIGHT, SavedData.Weight).apply();
        pref.edit().putInt(SavedData.APP_PREFERENCE_PRONATION, SavedData.Pronacia).apply();
        pref.edit().putInt(SavedData.APP_PREFERENCE_REAM_WIDTH, SavedData.ReamWidth).apply();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        //класс для табов
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

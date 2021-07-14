package com.roflanRun.CulComf;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Description extends AppCompatActivity {

    TextView main;
    ViewPager viewPager;
    LinearLayout DotSlider;
    private int dotascount;
    private ImageView[] dots;
    ImageView endimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Back");
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        viewPager = findViewById(R.id.DescrPager);
        List<Integer> ListOfImages = new ArrayList<>();
        ViewDescrAdapter viewDescrAdapter;
        if (SavedData.IsPronacia){
            viewPager.setBackground(ContextCompat.getDrawable(this, R.drawable.pronation));
            viewDescrAdapter = new ViewDescrAdapter(this, ListOfImages);
        }
        else {
            int resID;
            int k = 1;
            do{
                String tempimgname = "descr";
                String kstr = Integer.toString(k);
                tempimgname = tempimgname + kstr;

                resID = getResources().getIdentifier(tempimgname, "drawable", "" + SavedData.APP_PACKEGE);
                if (resID != 0){
                    ListOfImages.add(resID);
                }
                k++;
            } while (resID != 0);

            viewDescrAdapter = new ViewDescrAdapter(this, ListOfImages);
        }
        viewPager.setAdapter(viewDescrAdapter);

        if (ListOfImages.size() > 0) {
            DotSlider = findViewById(R.id.DotDescr);
            dotascount = viewDescrAdapter.getCount();
            dots = new ImageView[dotascount];

            for (int i = 0; i < dotascount; i++) {

                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(8, 0, 8, 0);

                DotSlider.addView(dots[i], layoutParams);
            }
            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < dotascount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                    }

                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                }
            });
        }

        Typeface Anton_light = Typeface.createFromAsset(getAssets(), "fonts/antonio.light.ttf");

        main = findViewById(R.id.descriction);
        main.setTypeface(Anton_light);
        if (SavedData.IsPronacia){
            main.setText(SavedData.PronationDescription);
        }
        else {
            main.setText(SavedData.ReamWidthDescription);
        }

        endimg = findViewById(R.id.endimg);
        if (!SavedData.IsPronacia) {
            endimg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.animation));
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
}

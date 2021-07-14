package com.roflanRun.CulComf;

import android.database.SQLException;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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

public class BootActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout DotSlider;
    private int dotascount;
    private ImageView[] dots;
    TextView name;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);

        Typeface Anton_title = Typeface.createFromAsset(getAssets(), "fonts/Anton.ttf");
        Typeface Anton_light = Typeface.createFromAsset(getAssets(), "fonts/antonio.light.ttf");

        name = findViewById(R.id.name);
        description = findViewById(R.id.desriptoin);
        int BootID = SavedData.BootId;

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        try {
            dbHelper.openDataBase();
        } catch (SQLException sqle){
            throw sqle;
        }

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Back");
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        Boot currentSneaker = dbHelper.getBoot(BootID);
        String sneakerName = currentSneaker.getName();
        name.setText(sneakerName.toUpperCase());
        name.setTypeface(Anton_title);

        viewPager = findViewById(R.id.ImgPager);
        int resID;
        int k = 1;
        List<Integer> ListOfImages = new ArrayList<>();
        do{
            String tempimgname = dbHelper.getBoot(BootID).getImages();
            String kstr = Integer.toString(k);
            tempimgname = tempimgname + kstr;

            resID = getResources().getIdentifier(tempimgname, "drawable", "" + SavedData.APP_PACKEGE);
            if (resID != 0){
                ListOfImages.add(resID);
            }
            k++;
        } while (resID != 0);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, ListOfImages);
        viewPager.setAdapter(viewPagerAdapter);

        DotSlider = findViewById(R.id.DotSlider);
        dotascount = viewPagerAdapter.getCount();
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
            public void onPageScrolled(int i, float v, int i1) {}

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotascount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });

        String desriptoin = currentSneaker.getDescription();
        description.setText(desriptoin);
        description.setTypeface(Anton_light);
        Drawable bg;
        switch (currentSneaker.getPurpose()){
            case "Running":
                bg = ContextCompat.getDrawable(this, R.drawable.tlo_run);
                break;
            case "Basketball":
                bg = ContextCompat.getDrawable(this, R.drawable.tlo_basket);
                break;
                default:
                    bg = ContextCompat.getDrawable(this, R.drawable.tlo_basket);
        }
        description.setBackground(bg);
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
        SavedData.BootId = 0;
    }
}

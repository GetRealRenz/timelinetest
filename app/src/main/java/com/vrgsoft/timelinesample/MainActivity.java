package com.vrgsoft.timelinesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vrgsoft.yearview.ClickView;
import com.vrgsoft.yearview.OnRowClick;
import com.vrgsoft.yearview.YearLayout;
import com.vrgsoft.yearview.YearModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnRowClick {
    private YearLayout mYearLayout;
    private List<YearModel> mModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mModels = new ArrayList<>();
        for (int i = 1860; i <= 2014; i++) {
            mModels.add(new SomeModel(i, i + new Random().nextInt(10),
                    "www.guidingstar.ca/Born_Under_What_Year_of_Chinese_Zodiac.png", String.valueOf(i)));
        }
        mYearLayout = (YearLayout) findViewById(R.id.year_layout);

        YearLayout.Builder builder = new YearLayout.Builder();
        builder.setYears(mModels)
                .setMaxYear(2014)
                .setMinYear(1860)
                .attachToActivity(this)
                .setOnRowClick(this)
                .setYearBackgroundColor(R.color.line_color)
                .setYearTitleColor(R.color.colorPrimary)
                .setYearRowTextColor(R.color.colorPrimaryDark)
                .create();
        mYearLayout.setBuilder(builder);
    }

    @Override
    public void onClick(int year, ClickView.ItemHolder view) {
        Toast.makeText(this, "Clicked on year " + year, Toast.LENGTH_SHORT).show();
    }
}

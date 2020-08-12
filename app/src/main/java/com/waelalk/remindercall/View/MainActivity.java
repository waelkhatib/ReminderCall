package com.waelalk.remindercall.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.waelalk.remindercall.Helper.Application;
import com.waelalk.remindercall.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        CardView cardView=findViewById(R.id.timesCardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TimesActivity.class));
            }
        });
        cardView=findViewById(R.id.contactCardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Application.getSystemSetting().isFirstPhaseFinished())
                    Application.makeSimpleDialog(MainActivity.this,R.string.info,R.string.this_phase_has_not_activate_yet);
                else
                startActivity(new Intent(MainActivity.this,ContactsActivity.class));
            }
        });
        cardView=findViewById(R.id.settingCardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Application.getSystemSetting().isSecondPhaseFinished())
                    Application.makeSimpleDialog(MainActivity.this,R.string.info,R.string.this_phase_has_not_activate_yet);
                else
                    startActivity(new Intent(MainActivity.this,ConfigurationActivity.class));
            }
        });
        if(Application.getSystemSetting().isFirstPhaseFinished()){
            ((TextView)findViewById(R.id.lbl2)).setTextColor(getResources().getColor(android.R.color.black));
            ((TextView)findViewById(R.id.title2)).setTextColor(getResources().getColor(android.R.color.black));
        }else {
            ((TextView)findViewById(R.id.lbl2)).setTextColor(getResources().getColor(android.R.color.tab_indicator_text));
            ((TextView)findViewById(R.id.title2)).setTextColor(getResources().getColor(android.R.color.tab_indicator_text));
        }
        if(Application.getSystemSetting().isSecondPhaseFinished()){
            ((TextView)findViewById(R.id.lbl3)).setTextColor(getResources().getColor(android.R.color.black));
            ((TextView)findViewById(R.id.title3)).setTextColor(getResources().getColor(android.R.color.black));
        }else {
            ((TextView)findViewById(R.id.lbl3)).setTextColor(getResources().getColor(android.R.color.tab_indicator_text));
            ((TextView)findViewById(R.id.title3)).setTextColor(getResources().getColor(android.R.color.tab_indicator_text));
        }


    }

    @Override
    protected void onResume() {
        initViews();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.help:
                startActivity(new Intent(this,About_us.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

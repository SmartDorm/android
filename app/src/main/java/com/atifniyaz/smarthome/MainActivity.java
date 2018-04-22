package com.atifniyaz.smarthome;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private WelcomeViewPager mViewPager;
    private WelcomePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mViewPager = findViewById(R.id.pager);
        mPagerAdapter = new WelcomePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPagingEnabled(false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    mViewPager.setPagingEnabled(false);
                } else if(position == 1) {

                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter == null) {
                        createNoBluetoothDialog();
                    } else {
                        if (!mBluetoothAdapter.isEnabled()) {
                            createEnableBluetoothDialog();
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void createEnableBluetoothDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bluetooth isn't Enabled!");
        builder.setMessage("In order for SmartDorm to work, Bluetooth needs to be enabled.");
        builder.setPositiveButton("Go Enable", (dialog, id) -> {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        });
        builder.setNegativeButton("Cancel", (dialog, id) -> mViewPager.setCurrentItem(0));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createNoBluetoothDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your device isn't supported");
        builder.setMessage("In order for SmartDorm to work, SmartDorm needs Bluetooth.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setOnDismissListener(dialog -> {
            finish();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class WelcomePagerAdapter extends FragmentStatePagerAdapter {

        private int NUM_PAGES = 2;

        private WelcomeFragment welcomeFragment;
        private ConnectFragment connectFragment;

        public WelcomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return welcomeFragment == null ? new WelcomeFragment() : welcomeFragment;
                case 1:
                    return connectFragment == null ? new ConnectFragment() : connectFragment;
            }

            return new WelcomeFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
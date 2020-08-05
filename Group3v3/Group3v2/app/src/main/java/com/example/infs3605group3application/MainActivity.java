package com.example.infs3605group3application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.infs3605group3application.fragments.ContactFragment;
import com.example.infs3605group3application.fragments.FAQsFragment;
import com.example.infs3605group3application.fragments.MineFragment;
import com.example.infs3605group3application.fragments.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private Button tester;
    private Button Contact;

    BottomNavigationView bottomNavigation;
    private FAQsFragment fAQsFragment;
    private Fragment currentFragment;
    private NewsFragment newsFragment;
    private ContactFragment contactFragment;
    private MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, Joanna!");

        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.nav_bar);
        if (mineFragment == null) {
            mineFragment = new MineFragment();;
        }
        if (newsFragment == null) {
            newsFragment = new NewsFragment();;
        }
        if (contactFragment == null) {
            contactFragment = new ContactFragment();;
        }
        if (fAQsFragment == null) {
            fAQsFragment = new FAQsFragment();;
        }
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.login:
                        SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
                        boolean isLogin = sp.getBoolean("login", false);
                        if (!isLogin){
                            toLogin();

                        }else{
                            switchFragment(mineFragment);
                        }

                        return true;

                    case R.id.news:

                        switchFragment(newsFragment);
                        return true;
                    case R.id.contact:

                        switchFragment(contactFragment);
                        return true;
                    case R.id.FAQs:

                        switchFragment(fAQsFragment);
                        return true;
                    default:
                        return false;
                }

            }
        });


        switchFragment(newsFragment);


    }

    public  void toLoginOut() {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Logout")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SharedPreferences sp =getSharedPreferences("info",Context. MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("name","");
                        edit.putBoolean("login",false);
                        edit.apply();
                        bottomNavigation.setSelectedItemId(bottomNavigation.getMenu().getItem(0).getItemId());
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }
    private void toLogin() {

        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Please log in first")
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        bottomNavigation.setSelectedItemId(bottomNavigation.getMenu().getItem(0).getItemId());
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bottomNavigation.setSelectedItemId(bottomNavigation.getMenu().getItem(0).getItemId());
                dialog.dismiss();
            }
        }).create().show();
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments( transaction);
        if (!fragment.isAdded()) {
            transaction.add(R.id.container, fragment, fragment.getClass().getName());
        }
        transaction.show(fragment).commit();
    }
    private void hideFragments( FragmentTransaction transaction) {
        if (mineFragment != null && mineFragment.isVisible()) {
            transaction.hide(mineFragment);
        }
        if (newsFragment != null && newsFragment.isVisible()) {
            transaction.hide(newsFragment);
        }
        if (contactFragment != null && contactFragment.isVisible()) {
            transaction.hide(contactFragment);
        }
        if (fAQsFragment != null && fAQsFragment.isVisible()) {
            transaction.hide(fAQsFragment);
        }
    }
}
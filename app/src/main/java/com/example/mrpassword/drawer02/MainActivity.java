package com.example.mrpassword.drawer02;

import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    Dialog myDialog;
    TextView txtclose;
    Button btnFollow;
    String selectchild;
    Food food = new Food();
    TypeF typeF = new TypeF();



    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        ///hamberger icon//////////////////////////////
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                mDrawer, R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(drawerToggle);
        ///////////////////////////////////////////////////
        ///bottom//////////////////////////////////////////
        BottomNavigationView bnv = findViewById(R.id.bottom_nav);
        bnv.setOnNavigationItemSelectedListener(bnvSelectedListener);
        bnv.setOnNavigationItemReselectedListener(bnvReselectedListener);
        ///////////////////////////////////////////////////////////////////
        ///Call maincontent///////////////////////
        if (savedInstanceState == null) {
            MainContent fragment1 = new MainContent();
            FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.flContent, fragment1, "Fragment");
            fragmentTransaction1.commit();
        }
        ///////////////////////////////////////////
        myDialog = new Dialog(this);
    }

    ///hamberger///////////////////////////////////////
    @Override
    protected void onPostCreate(@Nullable Bundle saveInstanceState) {
        super.onPostCreate(saveInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
    ////////////////////////////////////////////////////////////////////////////////

    ///click drawer//////////////////////////////////////////////////////////////////
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = FirstFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = SecondFragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = ThirdFragment.class;
                break;
            default:
                fragmentClass = FirstFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title

        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    ///BOTTOM CLICK///////////////////////////////////////////////////////////////////////////////
    private BottomNavigationView.OnNavigationItemSelectedListener bnvSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.home_bar:
                            MainContent fragment1 = new MainContent();
                            FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction1.replace(R.id.flContent, fragment1, "Fragment");
                            fragmentTransaction1.commit();
                            return true;
                        case R.id.lb_bar:
                            LibaryFragment fragment2 = new LibaryFragment();
                            FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction2.replace(R.id.flContent, fragment2, "Fragment");
                            fragmentTransaction2.commit();
                            return true;
                        case R.id.search_bar:
                            SearchFragment fragment3 = new SearchFragment();
                            FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction3.replace(R.id.flContent, fragment3, "Fragment");
                            fragmentTransaction3.commit();
                            return true;
                        case R.id.acc_bar:
                            AccountFragment fragment4 = new AccountFragment();
                            FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction4.replace(R.id.flContent, fragment4, "Fragment");
                            fragmentTransaction4.commit();

                            return true;
                    }
                    return false;


                }
            };
    private BottomNavigationView.OnNavigationItemReselectedListener bnvReselectedListener
            = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {

        }
    };

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // POP UP////////////////////////////////////////////////////////////////////////////////////////////
    public void ShowPopup(View v) {
        random();
        myDialog.setContentView(R.layout.popup_food);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        TextView txtRandomName = (TextView) myDialog.findViewById(R.id.ranName);
        ImageView imageView = (ImageView) myDialog.findViewById(R.id.ranImage);
        TextView textD = (TextView) myDialog.findViewById(R.id.food_dis);
        txtRandomName.setText(food.getName());
        Picasso.with(this).load(food.getPic()).into(imageView);
        textD.setText(food.getFID());
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    private void random() {
        FirebaseDatabase.getInstance().getReference().child("TypeF").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int childcount = safeLongToInt(dataSnapshot.getChildrenCount());
//                selectchild = childcount+"";
                if (childcount == 0) return;
                Random rand = new Random();
                int random = rand.nextInt(childcount);
                selectchild = Integer.toString(random);
                typeF.setTID(dataSnapshot.child(selectchild).child("TID").getValue().toString());
//                food.setName(dataSnapshot.child(selectchild).child("TID").getValue().toString());
                Switch swfd = (Switch) findViewById(R.id.SWFD);
                Switch swfr = (Switch) findViewById(R.id.SWFR);
                Switch swfn = (Switch) findViewById(R.id.SWFN);
                if (swfd.isChecked() && swfn.isChecked() && swfr.isChecked()) {
                    String[] tmp = {"FD", "FN", "FR"};
                    Random generator = new Random();
                    int randomIndex = generator.nextInt(tmp.length);
                    typeF.setTID(tmp[randomIndex]);
                } else if (swfd.isChecked() && swfn.isChecked()) {
                    String[] tmp = {"FD", "FN"};
                    Random generator = new Random();
                    int randomIndex = generator.nextInt(tmp.length);
                    typeF.setTID(tmp[randomIndex]);
                } else if (swfn.isChecked() && swfr.isChecked()) {
                    String[] tmp = {"FN", "FR"};
                    Random generator = new Random();
                    int randomIndex = generator.nextInt(tmp.length);
                    typeF.setTID(tmp[randomIndex]);
                } else if (swfd.isChecked() && swfr.isChecked()) {
                    String[] tmp = {"FD", "FR"};
                    Random generator = new Random();
                    int randomIndex = generator.nextInt(tmp.length);
                    typeF.setTID(tmp[randomIndex]);
                } else if (swfn.isChecked()) {
                    typeF.setTID("FN");
                } else if (swfr.isChecked()) {
                    typeF.setTID("FR");
                } else if (swfd.isChecked()) {
                    typeF.setTID("FD");
                }
                FirebaseDatabase.getInstance().getReference().child("Food").child(typeF.getTID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int childcount = safeLongToInt(dataSnapshot.getChildrenCount());
//                        selectchild = childcount+"";
                        if (childcount == 0) return;
                        Random rand = new Random();
                        int random = rand.nextInt(childcount);
                        selectchild = Integer.toString(random + 1);
                        if (selectchild.length() == 1) {
                            selectchild = typeF.getTID() + "0" + selectchild;
                        } else {
                            selectchild = typeF.getTID() + selectchild;
                        }
                        food.setName(dataSnapshot.child(selectchild).child("Name").getValue().toString());
                        food.setPic(dataSnapshot.child(selectchild).child("Pic").getValue().toString());
                        food.setFID(dataSnapshot.child(selectchild).child("FID").getValue().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
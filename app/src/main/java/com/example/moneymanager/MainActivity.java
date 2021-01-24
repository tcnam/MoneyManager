package com.example.moneymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button updateButton;

    //profile data
    String _username;
    String nameFromDB;
    String usernameFromDB;
    String emailFromDB;
    String phoneNoFromDB;
    String passwordFromDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //hooks
        drawerLayout  = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        updateButton = findViewById(R.id.updateButton);

        //toolbars
        //setSupportActionBar(toolbar);

        //Navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_view);

        updateButton.setOnClickListener(this);
        Intent intent = getIntent();
        _username = intent.getStringExtra("username");

        GetProfileData();
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_view:
                break;
            case R.id.nav_profile:
                ToProfile();
                break;
            case R.id.nav_chart:
                intent = new Intent(MainActivity.this, Chart.class);
                startActivity(intent);
                break;
            case R.id.nav_category:
                intent = new Intent(MainActivity.this, Category.class);
                startActivity(intent);
                break;
            case R.id.nav_setting:
                intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);
                break;
            case R.id.nav_about:
                intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, Update.class);
        startActivity(intent);
    }

    private void ToProfile()
    {
        Intent intent = new Intent(getApplicationContext(), profile.class);
        intent.putExtra("name", nameFromDB);
        intent.putExtra("username", usernameFromDB);
        intent.putExtra("email", emailFromDB);
        intent.putExtra("phonenumber", phoneNoFromDB);
        intent.putExtra("password", passwordFromDB);
        startActivity(intent);
    }

    private void GetProfileData()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        reference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                         nameFromDB = dataSnapshot.child(_username).child("name").getValue(String.class);
                         usernameFromDB = dataSnapshot.child(_username).child("username").getValue(String.class);
                         phoneNoFromDB = dataSnapshot.child(_username).child("phonenumber").getValue(String.class);
                         emailFromDB = dataSnapshot.child(_username).child("email").getValue(String.class);
                         passwordFromDB = dataSnapshot.child(_username).child("password").getValue(String.class);
                    }
                    public void onCancelled(@NonNull DatabaseError databaseError){
                    }
                });
    }
}

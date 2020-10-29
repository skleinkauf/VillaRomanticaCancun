package com.example.villaromanticacancun.tours;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.villaromanticacancun.ContactUs;
import com.example.villaromanticacancun.gallery.Gallery;
import com.example.villaromanticacancun.Home;
import com.example.villaromanticacancun.R;
import com.example.villaromanticacancun.rooms.Rooms;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class TourDetail extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_detail_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tour Detail");

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(TourDetail.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nav_view_tour_details);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });

        if (getIntent().hasExtra("SelectedTourDetail"))
        {
            Intent tourDetailIntent = getIntent();
            ToursDatabase tourDatabase = tourDetailIntent.getParcelableExtra("SelectedTourDetail");
            String tourName = tourDatabase.getTourName();
            String tourLocation = tourDatabase.getTourLocation();
            String tourAbout = tourDatabase.getTourAbout();
            String tourPhoto = tourDatabase.getTourPhoto();

            ImageView imageTourDetail = findViewById(R.id.imageViewTourDetail);
            TextView toursName = findViewById(R.id.textViewTourDetail);
            TextView toursLocation = findViewById(R.id.textViewTourLocation);
            TextView toursAbout = findViewById(R.id.textViewTourAbout);

            toursName.setText(tourName);
            toursLocation.setText(tourLocation);
            toursAbout.setText(tourAbout);
            Picasso.get().load(tourPhoto).into(imageTourDetail);

        }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent homeIntent = new Intent(this, Home.class);
                startActivity(homeIntent);
                break;
            case R.id.menu_gallery:
                Intent galleryIntent = new Intent(this, Gallery.class);
                startActivity(galleryIntent);
                break;
            case R.id.menu_rooms:
                Intent roomsIntent = new Intent(this, Rooms.class);
                startActivity(roomsIntent);
                break;
            case R.id.menu_tours:
                Intent toursIntent = new Intent(this, Tours.class);
                startActivity(toursIntent);
                break;
            case R.id.menu_contact_us:
                Intent contactUsIntent = new Intent(this, ContactUs.class);
                startActivity(contactUsIntent);
                break;
            case R.id.menu_logout:
                firebaseAuth.signOut();
                SendUserToLogin();
                Toast.makeText(this, "you logout from the app", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void SendUserToLogin() {
        Intent loginIntent = new Intent(this, Home.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

}

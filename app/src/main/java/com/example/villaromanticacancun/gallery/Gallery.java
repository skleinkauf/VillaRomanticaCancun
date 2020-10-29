package com.example.villaromanticacancun.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villaromanticacancun.ContactUs;
import com.example.villaromanticacancun.Home;
import com.example.villaromanticacancun.R;
import com.example.villaromanticacancun.rooms.Rooms;
import com.example.villaromanticacancun.tours.Tours;
import com.example.villaromanticacancun.tours.ToursAdapter;
import com.example.villaromanticacancun.tours.ToursDatabase;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Gallery extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerViewGallery;
    private DatabaseReference galleryDatabase;
    private ArrayList<GalleryDatabase> galleryList;
    private GalleryAdapter galleryAdapter;
    private Context galleryContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gallery");

        galleryDatabase = FirebaseDatabase.getInstance().getReference();
        galleryDatabase.keepSynced(true);

        recyclerViewGallery = findViewById(R.id.recyclerViewGallery);
        recyclerViewGallery.setHasFixedSize(true);
        recyclerViewGallery.setLayoutManager(new LinearLayoutManager(this));
        galleryList = new ArrayList<>();
        ClearAll();
        GetDataFromDatabase();

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(Gallery.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nav_view_gallery);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                UserMenuSelector(item);
                return false;
            }
        });
    }

    private void GetDataFromDatabase() {

        Query query = galleryDatabase.child("Gallery");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    GalleryDatabase gallery = new GalleryDatabase();
                    gallery.setGalleryPhoto(snapshot.child("galleryPhoto").getValue().toString());

                    galleryList.add(gallery);
                }
                galleryAdapter = new GalleryAdapter(galleryContext, galleryList);
                recyclerViewGallery.setAdapter(galleryAdapter);
                galleryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void ClearAll() {
        if (galleryList != null) {
            galleryList.clear();
            if (galleryAdapter != null) {
                galleryAdapter.notifyDataSetChanged();
            }
        }
        galleryList = new ArrayList<>();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item)
    {
        switch (item.getItemId())
        {
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

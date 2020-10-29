package com.example.villaromanticacancun.rooms;

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
import com.example.villaromanticacancun.gallery.Gallery;
import com.example.villaromanticacancun.Home;
import com.example.villaromanticacancun.R;
import com.example.villaromanticacancun.tours.Tours;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Rooms extends AppCompatActivity implements RoomsAdapter.OnItemRoomClickListener {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerViewRooms;
    private DatabaseReference roomsDatabase;
    private ArrayList<RoomsDatabase> roomsList;
    private RoomsAdapter roomsAdapter;
    private Context roomsContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rooms");
        roomsDatabase = FirebaseDatabase.getInstance().getReference();
        roomsDatabase.keepSynced(true);

        recyclerViewRooms = findViewById(R.id.recyclerViewRooms);
        recyclerViewRooms.setHasFixedSize(true);
        recyclerViewRooms.setLayoutManager(new LinearLayoutManager(this));
        roomsList = new ArrayList<>();
        ClearAll();
        GetDataFromDatabase();

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(Rooms.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nav_view_rooms);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });
    }

    private void GetDataFromDatabase() {

        Query query = roomsDatabase.child("Rooms");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RoomsDatabase rooms = new RoomsDatabase();
                    rooms.setRoomPhoto(snapshot.child("roomPhoto").getValue().toString());
                    rooms.setRoomType(snapshot.child("roomType").getValue().toString());
                    rooms.setRoomLocation(snapshot.child("roomLocation").getValue().toString());
                    rooms.setRoomAbout(snapshot.child("roomAbout").getValue().toString());

                    roomsList.add(rooms);
                }
                roomsAdapter = new RoomsAdapter(roomsContext, roomsList, Rooms.this);
                recyclerViewRooms.setAdapter(roomsAdapter);
                roomsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void ClearAll() {
        if (roomsList != null) {
            roomsList.clear();
            if (roomsAdapter != null) {
                roomsAdapter.notifyDataSetChanged();
            }
        }
        roomsList = new ArrayList<>();
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

    @Override
    public void onItemRoomClick(int position) {
        roomsList.get(position);
        Intent roomDetailActivity = new Intent(Rooms.this, RoomDetail.class);
        roomDetailActivity.putExtra("SelectedRoomDetail", roomsList.get(position));
        startActivity(roomDetailActivity);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

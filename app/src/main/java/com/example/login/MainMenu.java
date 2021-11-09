package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.login.adapter.MenuRecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {

    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        linearLayoutManager = new LinearLayoutManager(MainMenu.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<MainMenuUser> allMenuInfo = getAllMenuInfo();
        MenuRecyclerViewAdapter menuRecyclerViewAdapter = new MenuRecyclerViewAdapter(MainMenu.this, allMenuInfo);
        recyclerView.setAdapter(menuRecyclerViewAdapter);
    }

    private List<MainMenuUser> getAllMenuInfo(){
        List<MainMenuUser> allMenu = new ArrayList<MainMenuUser>();

        allMenu.add(new MainMenuUser ("Time to Recycle", R.drawable.recycle));
        allMenu.add(new MainMenuUser("Time to Donate", R.drawable.donate));
        allMenu.add(new MainMenuUser("Rewards For You",R.drawable.rewards));
        allMenu.add(new MainMenuUser("Your Rewards",R.drawable.yourrewards));
        allMenu.add(new MainMenuUser("Your Points",R.drawable.points));

        return allMenu;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;

        switch (item.getItemId()){
            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
                break;

            case R.id.menu_profile:
                Toast.makeText(MainMenu.this,"Profile", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainMenu.this, ProfileActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
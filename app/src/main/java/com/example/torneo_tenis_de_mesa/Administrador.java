package com.example.torneo_tenis_de_mesa;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.torneo_tenis_de_mesa.Polimorfismo.Persona;
import com.example.torneo_tenis_de_mesa.UIAdministrador.AAboutFragment;
import com.example.torneo_tenis_de_mesa.UIAdministrador.MatchesFragment;
import com.example.torneo_tenis_de_mesa.UIAdministrador.ParticipantesFragment;
import com.example.torneo_tenis_de_mesa.UI_Participante.HomeFragment;
import com.example.torneo_tenis_de_mesa.UI_Participante.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class Administrador extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;





    //Creación de la activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        Toolbar toolbar = findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.drawer_layout_admin);
        NavigationView navigationView = findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MatchesFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_matches);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_matches:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MatchesFragment()).commit();
                break;
            case R.id.nav_participantes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ParticipantesFragment()).commit();

                break;
            case R.id.nav_settings_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;
            case R.id.nav_about_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AAboutFragment()).commit();
                break;
            case R.id.logout_admin:
                Toast.makeText(this, "Logout!!", Toast.LENGTH_LONG).show();
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}

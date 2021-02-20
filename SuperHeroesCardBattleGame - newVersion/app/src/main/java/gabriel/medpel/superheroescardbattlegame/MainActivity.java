package gabriel.medpel.superheroescardbattlegame;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private Song s;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference mReff = FirebaseDatabase.getInstance().getReference();
    private String username = auth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_play, R.id.navigation_mycards, R.id.navigation_tryyourluck)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        s = new Song();
        s.PlaySong(R.raw.app_music, true, this);


        mReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("users").child(username).child("name").getValue(String.class);

                new AlertDialog.Builder(MainActivity.this).setMessage("Welcome to the game, " + name + " !!").show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        s.PauseSong();
    }

    @Override
    protected void onResume() {
        super.onResume();
        s.ResumeSong();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            auth.signOut();
            startActivity(new Intent(this, Splash.class));
        } else if (item.getItemId() == R.id.reset) {

            mReff.child("users").child(username).child("myDeck").getRef().removeValue();
            mReff.child("users").child(username).child("deck").getRef().removeValue();
            mReff.child("users").child(username).child("lastDay").getRef().removeValue();


            startActivity(new Intent(this, MainActivity.class));
        } else if (item.getItemId() == R.id.sound) {
            if (s.isMuted()) {
                s.unMute();
                item.setTitle("Mute Sounds");
            } else if (!s.isMuted()) {
                s.mute();
                item.setTitle("UnMute Sounds");
            }

        }

        return super.onOptionsItemSelected(item);
    }
}

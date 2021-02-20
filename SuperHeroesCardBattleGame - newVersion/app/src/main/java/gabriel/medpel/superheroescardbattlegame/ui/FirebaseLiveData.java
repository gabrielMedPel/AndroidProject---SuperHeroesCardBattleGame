package gabriel.medpel.superheroescardbattlegame.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import gabriel.medpel.superheroescardbattlegame.models.Deck;
import gabriel.medpel.superheroescardbattlegame.models.User;

public class FirebaseLiveData {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference mReff = FirebaseDatabase.getInstance().getReference();
    private String username = auth.getCurrentUser().getUid();
    private ArrayList<User> users = new ArrayList<>();


    public void readDemo(MutableLiveData<Deck> deckMLD, MutableLiveData<Deck> myDeckMLD, MutableLiveData<Deck> opponentDeck, MutableLiveData<String> opponentName) {
        mReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Deck deck = snapshot.child("users").child(username).child("deck").getValue(Deck.class);
                Deck myDeck = snapshot.child("users").child(username).child("myDeck").getValue(Deck.class);

                deckMLD.postValue(deck);
                myDeckMLD.postValue(myDeck);

                for (DataSnapshot snap: snapshot.child("users").getChildren()) {
                    String name = snap.child("name").getValue(String.class);
                    String email = snap.child("email").getValue(String.class);

                    String nameUser = snapshot.child("users").child(username).child("name").getValue(String.class);
                    if (!nameUser.equals(name)){
                        User user = new User(name,email);
                        user.setDeck(snap.child("myDeck").getValue(Deck.class));

                        users.add(user);
                    }

                }

                Random rand = new Random();
                int randNumber = rand.nextInt(users.size());


                opponentDeck.postValue(users.get(randNumber).getDeck());

                String name = users.get(randNumber).getName();
                opponentName.postValue(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readDemoWin(MutableLiveData<Deck> deckMLD, MutableLiveData<Deck> myDeckMLD, MutableLiveData<Integer> winsMLD, MutableLiveData<Deck> oppDeckMLD) {
        mReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Deck deck = snapshot.child("users").child(username).child("deck").getValue(Deck.class);
                Deck myDeck = snapshot.child("users").child(username).child("myDeck").getValue(Deck.class);
                Deck oppDeck = snapshot.child("users").child(username).child("oppDeck").getValue(Deck.class);
                int wins = snapshot.child("users").child(username).child("wins").getValue(Integer.class);

                winsMLD.postValue(wins);
                oppDeckMLD.postValue(oppDeck);
                deckMLD.postValue(deck);
                myDeckMLD.postValue(myDeck);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readDemoLastDayLuck(MutableLiveData<Integer> lastDayMLD) {
        mReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("users").child(username).child("lastDay").exists()) {
                    int lastDay = snapshot.child("users").child(username).child("lastDay").getValue(Integer.class);
                    lastDayMLD.postValue(lastDay);
                } else {
                    int lastDay = 0;
                    lastDayMLD.postValue(lastDay);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readDemoLastName(MutableLiveData<String> nameMLD) {
        mReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("users").child(username).child("name").getValue(String.class);
                nameMLD.postValue(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}

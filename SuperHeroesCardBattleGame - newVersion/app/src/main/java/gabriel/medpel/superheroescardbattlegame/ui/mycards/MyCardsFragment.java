package gabriel.medpel.superheroescardbattlegame.ui.mycards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import gabriel.medpel.superheroescardbattlegame.R;
import gabriel.medpel.superheroescardbattlegame.Song;
import gabriel.medpel.superheroescardbattlegame.models.Deck;
import gabriel.medpel.superheroescardbattlegame.models.HeroCard;

public class MyCardsFragment extends Fragment {
    private Song s;

    private MyCardsViewModel myCardsViewModel;
    private TextView tvCardsLeft;
    private Deck deck;
    private Deck myDeck;
    private FirebaseAuth auth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myReff;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myCardsViewModel =
                new ViewModelProvider(this).get(MyCardsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mycards, container, false);
        final RecyclerView rvHeroCards = root.findViewById(R.id.rvHeroCards);
        tvCardsLeft = root.findViewById(R.id.tvCardsLeft);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        myReff = mDatabase.getReference();
        String username = auth.getCurrentUser().getUid();

        s = new Song();
        s.PlaySong(R.raw.app_music, true, getContext());

        myReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("users").child(username).child("deck").exists()) {
                    deck = snapshot.child("users").child(username).child("deck").getValue(Deck.class);

                } else {

                    myCardsViewModel.getHeroCards().observe(getViewLifecycleOwner(), heroCards -> {
                        deck = new Deck();

                        for (HeroCard heroCard : heroCards) {
                            deck.addCard(heroCard);

                        }

                        myReff.child("users").child(username).child("deck").setValue(deck);
                    });
                }
                if (snapshot.child("users").child(username).child("myDeck").exists()) {
                    myDeck = snapshot.child("users").child(username).child("myDeck").getValue(Deck.class);



                } else {
                    myDeck = new Deck();

                    myDeck.addCard(deck.dealCard());
                    myDeck.addCard(deck.dealCard());
                    myDeck.addCard(deck.dealCard());
                    myDeck.addCard(deck.dealCard());
                    myDeck.addCard(deck.dealCard());
                    myDeck.addCard(deck.dealCard());

                    myReff.child("users").child(username).child("myDeck").setValue(myDeck);
                    myReff.child("users").child(username).child("deck").setValue(deck);
                }
                int cardsLeft = 563 - myDeck.cardsLeft();
                tvCardsLeft.setText(getString(R.string.cards_left) + cardsLeft);



                rvHeroCards.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                HeroCardAdapter adapter = new HeroCardAdapter(myDeck.getDeck());
                rvHeroCards.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        s.ResumeSong();

    }

    @Override
    public void onPause() {
        super.onPause();
        s.PauseSong();
    }
}
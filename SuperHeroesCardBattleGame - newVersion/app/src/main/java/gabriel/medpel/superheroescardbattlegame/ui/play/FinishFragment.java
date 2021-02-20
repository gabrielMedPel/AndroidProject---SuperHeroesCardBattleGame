package gabriel.medpel.superheroescardbattlegame.ui.play;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import gabriel.medpel.superheroescardbattlegame.R;
import gabriel.medpel.superheroescardbattlegame.Song;
import gabriel.medpel.superheroescardbattlegame.models.Deck;
import gabriel.medpel.superheroescardbattlegame.models.HeroCard;
import gabriel.medpel.superheroescardbattlegame.models.HeroCardView;

public class FinishFragment extends Fragment {

    private FinishViewModel finishViewModel;

    private Deck deck, myDeck, oppDeck;

    private int wins;
    private Song s;

    private HeroCardView hcCardReceived;

    private Button btnPlayAgain;

    private ImageView ivNot;
    private TextView tvCheckWin;

    private String username = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DatabaseReference myReff = FirebaseDatabase.getInstance().getReference();

    public static FinishFragment newInstance() {
        return new FinishFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.finish_fragment, container, false);

        hcCardReceived = root.findViewById(R.id.hcCardReceived);
        ivNot = root.findViewById(R.id.ivNot);
        tvCheckWin = root.findViewById(R.id.tvCheckWin);
        btnPlayAgain = root.findViewById(R.id.btnPlayAgain);

        hcCardReceived.setVisibility(View.VISIBLE);

        finishViewModel = new ViewModelProvider(this).get(FinishViewModel.class);

        s = new Song();
//        s.PlayFX(R.raw.ruff,getContext(),false);


        finishViewModel.getWins().observe(getViewLifecycleOwner(), newWins -> {
            wins = newWins;

            if (wins >= 3) {
                s.PlayFX(R.raw.win_card, getContext(), false);
                tvCheckWin.setText("Congrats !! You Won a Card !!");

                finishViewModel.getOppDeck().observe(getViewLifecycleOwner(), newOppDeck -> {
                    oppDeck = newOppDeck;
                    oppDeck.shuffle();
                    HeroCard cardReceived = oppDeck.dealCard();

                    finishViewModel.getMyDeck().observe(getViewLifecycleOwner(), newMyDeck -> {
                        myDeck = newMyDeck;
                        myDeck.addCard(cardReceived);

                        hcCardReceived.setCard(cardReceived);
                        hcCardReceived.flipCard();

                        btnPlayAgain.setVisibility(View.VISIBLE);
                        btnPlayAgain.setEnabled(true);

                        ArrayList<HeroCard> backToDeck = oppDeck.getDeck();

                        finishViewModel.getDeck().observe(getViewLifecycleOwner(), newDeck -> {
                            deck = newDeck;
                            for (HeroCard heroCard : backToDeck) {
                                deck.addCard(heroCard);
                            }
                        });
                        myReff.child("users").child(username).child("deck").setValue(deck);
                        myReff.child("users").child(username).child("myDeck").setValue(myDeck);

                        myReff.child("users").child(username).child("oppDeck").removeValue();
                        myReff.child("users").child(username).child("wins").removeValue();


                    });

                });

            } else {
                s.PlayFX(R.raw.not_win_card, getContext(), false);
                tvCheckWin.setText("Sorry, you don't won a Card !!");
                btnPlayAgain.setVisibility(View.VISIBLE);
                btnPlayAgain.setEnabled(true);

                ivNot.setVisibility(View.VISIBLE);
                hcCardReceived.setVisibility(View.INVISIBLE);
            }

        });

        btnPlayAgain.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();

            GameFragment myFragment = new GameFragment();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.finishContainer, myFragment)
                    .commit();
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
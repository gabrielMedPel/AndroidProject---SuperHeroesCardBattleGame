package gabriel.medpel.superheroescardbattlegame.ui.play;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import gabriel.medpel.superheroescardbattlegame.R;
import gabriel.medpel.superheroescardbattlegame.Song;
import gabriel.medpel.superheroescardbattlegame.models.Deck;
import gabriel.medpel.superheroescardbattlegame.models.HeroCard;
import gabriel.medpel.superheroescardbattlegame.models.HeroCardView;

public class GameFragment extends Fragment {
    private Song s;

    private TextView tvIntelligenceGame, tvStrengthGame, tvSpeedGame, tvDurabilityGame, tvPowerGame, tvCombatGame,
            tvIntelligenceGameInfo, tvStrengthGameInfo, tvSpeedGameInfo, tvDurabilityGameInfo, tvPowerGameInfo, tvCombatGameInfo,
            tvChosenStat, tvYouChooseInfo, tvYourStat, tvYourStatInfo, tvOppStat, tvOppStatInfo, tvWhoWin, tvStatusInfo, tvOpponentName;
    private Button btnPlay, btnNextRound, btnFinish;
    private HeroCardView hcYourCardView, hcOpponentCardView;

    private Deck deck, myDeck, cardsToWin, opponentDeck;

    private HeroCard yourCard, oppCard;

    private String yourStat, oppStat, yourStatNumber, oppStatNumber, opponentName;

    private int roundCount, winCount;

    private DatabaseReference myReff = FirebaseDatabase.getInstance().getReference();
    private String username = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private GameViewModel gameViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.game_fragment, container, false);

        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        hcYourCardView = root.findViewById(R.id.hcYourCardView);
        hcOpponentCardView = root.findViewById(R.id.hcOpponentCardView);

        tvIntelligenceGame = root.findViewById(R.id.tvIntelligenceGame);
        tvStrengthGame = root.findViewById(R.id.tvStrengthGame);
        tvSpeedGame = root.findViewById(R.id.tvSpeedGame);
        tvDurabilityGame = root.findViewById(R.id.tvDurabilityGame);
        tvPowerGame = root.findViewById(R.id.tvPowerGame);
        tvCombatGame = root.findViewById(R.id.tvCombatGame);
        tvIntelligenceGameInfo = root.findViewById(R.id.tvIntelligenceGameInfo);
        tvStrengthGameInfo = root.findViewById(R.id.tvStrengthGameInfo);
        tvSpeedGameInfo = root.findViewById(R.id.tvSpeedGameInfo);
        tvDurabilityGameInfo = root.findViewById(R.id.tvDurabilityGameInfo);
        tvPowerGameInfo = root.findViewById(R.id.tvPowerGameInfo);
        tvCombatGameInfo = root.findViewById(R.id.tvCombatGameInfo);

        btnPlay = root.findViewById(R.id.btnPlay);
        btnNextRound = root.findViewById(R.id.btnNextRound);
        btnFinish = root.findViewById(R.id.btnFinish);

        tvYouChooseInfo = root.findViewById(R.id.tvYouChooseInfo);
        tvChosenStat = root.findViewById(R.id.tvChosenStat);

        tvYourStat = root.findViewById(R.id.tvYourStat);
        tvOppStat = root.findViewById(R.id.tvOppStat);
        tvWhoWin = root.findViewById(R.id.tvWhoWon);

        tvYourStatInfo = root.findViewById(R.id.tvYourStatInfo);
        tvOppStatInfo = root.findViewById(R.id.tvOppStatInfo);

        tvStatusInfo = root.findViewById(R.id.tvStatusInfo);

        tvOpponentName = root.findViewById(R.id.tvOpponentName);

        cardsToWin = new Deck();

        gameViewModel.getOpponentName().observe(getViewLifecycleOwner(), newName -> {
            opponentName = newName;

            tvOpponentName.setText("Your Opponent: " + opponentName);

        });

        gameViewModel.getDeck().observe(getViewLifecycleOwner(), newDeck -> {
            deck = newDeck;
            newRound();
        });


        tvIntelligenceGame.setTag("Intelligence");
        tvStrengthGame.setTag("Strength");
        tvSpeedGame.setTag("Speed");
        tvDurabilityGame.setTag("Durability");
        tvPowerGame.setTag("Power");
        tvCombatGame.setTag("Combat");
        winCount = 0;

        btnPlay.setOnClickListener(v -> {
            tvIntelligenceGame.setOnClickListener(null);
            tvStrengthGame.setOnClickListener(null);
            tvSpeedGame.setOnClickListener(null);
            tvDurabilityGame.setOnClickListener(null);
            tvPowerGame.setOnClickListener(null);
            tvCombatGame.setOnClickListener(null);

            hcOpponentCardView.flipCard();

            btnPlay.setVisibility(View.INVISIBLE);
            btnPlay.setEnabled(false);
            tvChosenStat.setVisibility(View.INVISIBLE);
            tvYouChooseInfo.setVisibility(View.INVISIBLE);

            tvYourStatInfo.setVisibility(View.VISIBLE);
            tvOppStatInfo.setVisibility(View.VISIBLE);

            tvYourStat.setText(tvChosenStat.getText().toString());
            tvYourStat.setVisibility(View.VISIBLE);
            switch (oppStat) {
                case "Intelligence":
                    oppStatNumber = oppCard.getIntelligence();
                    break;
                case "Strength":
                    oppStatNumber = oppCard.getStrength();
                    break;
                case "Speed":
                    oppStatNumber = oppCard.getSpeed();
                    break;
                case "Durability":
                    oppStatNumber = oppCard.getDurability();
                    break;
                case "Power":
                    oppStatNumber = oppCard.getPower();
                    break;
                case "Combat":
                    oppStatNumber = oppCard.getCombat();
                    break;
            }
            tvOppStat.setText(oppStat + "  " + oppStatNumber);
            tvOppStat.setVisibility(View.VISIBLE);

            if (Integer.parseInt(yourStatNumber) > Integer.parseInt(oppStatNumber)) {
                tvWhoWin.setText("YOU WON!!");
                tvWhoWin.setTextColor(Color.GREEN);
                winCount++;

            } else if (Integer.parseInt(yourStatNumber) < Integer.parseInt(oppStatNumber)) {
                tvWhoWin.setText("YOU LOST!!");
                tvWhoWin.setTextColor(Color.RED);
            } else {
                tvWhoWin.setText("IT'S A TIE!!");
                tvWhoWin.setTextColor(Color.BLUE);
            }

            if (roundCount == 6) {
                btnFinish.setEnabled(true);
                btnFinish.setVisibility(View.VISIBLE);
            } else {
                btnNextRound.setEnabled(true);
                btnNextRound.setVisibility(View.VISIBLE);
            }

            tvWhoWin.setVisibility(View.VISIBLE);
            tvStatusInfo.setVisibility(View.INVISIBLE);


        });
        btnNextRound.setOnClickListener(v -> {
            prepareForGame();
            hcOpponentCardView.flipCard();
            hcYourCardView.flipCard();
            btnNextRound.postDelayed(this::newRound, 1500);

        });
        btnFinish.setOnClickListener(v -> {

            AppCompatActivity activity = (AppCompatActivity) v.getContext();

            FinishFragment myFragment = new FinishFragment();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.gameContainer, myFragment)
                    .commit();

            myReff.child("users").child(username).child("oppDeck").setValue(cardsToWin);
            myReff.child("users").child(username).child("wins").setValue(winCount);
        });

        s = new Song();

        return root;
    }

    View.OnClickListener onStatClick = v -> {
        tvIntelligenceGame.setBackground(null);
        tvStrengthGame.setBackground(null);
        tvSpeedGame.setBackground(null);
        tvDurabilityGame.setBackground(null);
        tvPowerGame.setBackground(null);
        tvCombatGame.setBackground(null);
        v.setBackgroundResource(R.drawable.border);

        yourStatNumber = ((TextView) v).getText().toString();

        yourStat = v.getTag() + "  " + yourStatNumber;

        oppStat = v.getTag().toString();


        tvChosenStat.setText(yourStat);

        btnPlay.setVisibility(View.VISIBLE);
        btnPlay.setEnabled(true);
        tvChosenStat.setVisibility(View.VISIBLE);
        tvYouChooseInfo.setVisibility(View.VISIBLE);
    };

    public void newRound() {
        roundCount++;

        tvIntelligenceGame.setBackground(null);
        tvStrengthGame.setBackground(null);
        tvSpeedGame.setBackground(null);
        tvDurabilityGame.setBackground(null);
        tvPowerGame.setBackground(null);
        tvCombatGame.setBackground(null);

        prepareForGame();

        gameViewModel.getMyDeck().observe(getViewLifecycleOwner(), newMyDeck -> {
            myDeck = newMyDeck;

            yourCard = myDeck.dealCard();

            tvIntelligenceGame.setText(yourCard.getIntelligence());
            tvStrengthGame.setText(yourCard.getStrength());
            tvSpeedGame.setText(yourCard.getSpeed());
            tvDurabilityGame.setText(yourCard.getDurability());
            tvPowerGame.setText(yourCard.getPower());
            tvCombatGame.setText(yourCard.getCombat());


            tvIntelligenceGame.setVisibility(View.VISIBLE);
            tvStrengthGame.setVisibility(View.VISIBLE);
            tvSpeedGame.setVisibility(View.VISIBLE);
            tvDurabilityGame.setVisibility(View.VISIBLE);
            tvPowerGame.setVisibility(View.VISIBLE);
            tvCombatGame.setVisibility(View.VISIBLE);
            tvIntelligenceGameInfo.setVisibility(View.VISIBLE);
            tvStrengthGameInfo.setVisibility(View.VISIBLE);
            tvSpeedGameInfo.setVisibility(View.VISIBLE);
            tvDurabilityGameInfo.setVisibility(View.VISIBLE);
            tvPowerGameInfo.setVisibility(View.VISIBLE);
            tvCombatGameInfo.setVisibility(View.VISIBLE);

            hcYourCardView.setCard(yourCard);
            hcYourCardView.flipCard();

            tvIntelligenceGame.setOnClickListener(onStatClick);
            tvStrengthGame.setOnClickListener(onStatClick);
            tvSpeedGame.setOnClickListener(onStatClick);
            tvDurabilityGame.setOnClickListener(onStatClick);
            tvPowerGame.setOnClickListener(onStatClick);
            tvCombatGame.setOnClickListener(onStatClick);

            tvStatusInfo.setText(R.string.choosestat);
            tvStatusInfo.setVisibility(View.VISIBLE);
        });
        gameViewModel.getOpponentDeck().observe(getViewLifecycleOwner(), newOpponentDeck -> {
            opponentDeck = newOpponentDeck;



            oppCard = opponentDeck.dealCard();
            cardsToWin.addCard(oppCard);
            hcOpponentCardView.setCard(oppCard);
        });




    }

    public void prepareForGame() {
        tvIntelligenceGame.setVisibility(View.INVISIBLE);
        tvStrengthGame.setVisibility(View.INVISIBLE);
        tvSpeedGame.setVisibility(View.INVISIBLE);
        tvDurabilityGame.setVisibility(View.INVISIBLE);
        tvPowerGame.setVisibility(View.INVISIBLE);
        tvCombatGame.setVisibility(View.INVISIBLE);
        tvIntelligenceGameInfo.setVisibility(View.INVISIBLE);
        tvStrengthGameInfo.setVisibility(View.INVISIBLE);
        tvSpeedGameInfo.setVisibility(View.INVISIBLE);
        tvDurabilityGameInfo.setVisibility(View.INVISIBLE);
        tvPowerGameInfo.setVisibility(View.INVISIBLE);
        tvCombatGameInfo.setVisibility(View.INVISIBLE);

        btnPlay.setVisibility(View.INVISIBLE);
        btnPlay.setEnabled(false);
        btnFinish.setVisibility(View.INVISIBLE);
        btnFinish.setEnabled(false);
        btnNextRound.setVisibility(View.INVISIBLE);
        btnNextRound.setEnabled(false);

        tvStatusInfo.setText("Dealing your card...");
        tvStatusInfo.setVisibility(View.VISIBLE);

        tvChosenStat.setVisibility(View.INVISIBLE);
        tvYouChooseInfo.setVisibility(View.INVISIBLE);
        tvYourStat.setVisibility(View.INVISIBLE);
        tvOppStat.setVisibility(View.INVISIBLE);
        tvYourStatInfo.setVisibility(View.INVISIBLE);
        tvOppStatInfo.setVisibility(View.INVISIBLE);


        tvWhoWin.setVisibility(View.INVISIBLE);
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
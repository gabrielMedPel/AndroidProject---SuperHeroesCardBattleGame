package gabriel.medpel.superheroescardbattlegame.ui.tryyourluck;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import gabriel.medpel.superheroescardbattlegame.R;
import gabriel.medpel.superheroescardbattlegame.Song;
import gabriel.medpel.superheroescardbattlegame.models.Deck;
import gabriel.medpel.superheroescardbattlegame.models.HeroCard;
import gabriel.medpel.superheroescardbattlegame.models.HeroCardView;

public class TryYourLuckFragment extends Fragment {
    private Deck deck, myDeck;
    private FirebaseAuth auth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myReff;
    private TextView tvWin;
    private int lastDay = 0, today;
    private Song s;
    private ImageView ivNotLuck;
    private HeroCardView hcLuckyCard;
    private TryYourLuckViewModel tryYourLuckViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tryYourLuckViewModel =
                new ViewModelProvider(this).get(TryYourLuckViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tryyourluck, container, false);


        s = new Song();
        if (s.getCurrentSongId() != R.raw.app_music) {
            s.PlaySong(R.raw.app_music, true, getContext());
        }

        ivNotLuck = root.findViewById(R.id.ivNotLuck);
        tvWin = root.findViewById(R.id.tvWin);

        hcLuckyCard = root.findViewById(R.id.hcLuckyCard);
        hcLuckyCard.setVisibility(View.INVISIBLE);

        LuckyWheel wheel = root.findViewById(R.id.lwv);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        myReff = mDatabase.getReference();

        Calendar cal = Calendar.getInstance();
        today = cal.get(Calendar.DAY_OF_YEAR);
        System.out.println(today);

        tryYourLuckViewModel.getLastDay().observe(getViewLifecycleOwner(), newLastDay -> {
            lastDay = newLastDay;

            System.out.println(lastDay);
            if (today == lastDay) {
                tvWin.setText("Come Back tomorrow and try again !!");
                ivNotLuck.setVisibility(View.VISIBLE);

            } else {
                tvWin.setText("Spin the Wheel and try to win a Card");
                wheel.setVisibility(View.VISIBLE);

            }
        });

        myReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                deck = snapshot.child("users").child(auth.getCurrentUser().getUid()).child("deck").getValue(Deck.class);
                myDeck = snapshot.child("users").child(auth.getCurrentUser().getUid()).child("myDeck").getValue(Deck.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        List<WheelItem> items = new ArrayList<>();
        Bitmap star = BitmapFactory.decodeResource(root.getResources(),
                R.drawable.star);
        Bitmap rStar = Bitmap.createScaledBitmap(
                star, 200, 200, false);
        Bitmap x = BitmapFactory.decodeResource(root.getResources(),
                R.drawable.x);
        Bitmap rSadFace = Bitmap.createScaledBitmap(
                x, 200, 200, false);

        items.add(new WheelItem(Color.YELLOW, rStar));
        items.add(new WheelItem(Color.RED, rSadFace));
        items.add(new WheelItem(Color.RED, rSadFace));
        items.add(new WheelItem(Color.RED, rSadFace));
        wheel.addWheelItems(items);


        Random random = new Random();
        int number = random.nextInt(4);
//        int number = 0;
        switch (number) {
            case 0:
                wheel.setTarget(1);
                break;
            case 1:
                wheel.setTarget(2);
                break;
            case 2:
                wheel.setTarget(3);
                break;
            case 3:
                wheel.setTarget(4);
                break;
        }

        wheel.setLuckyWheelReachTheTarget(() -> {
            if (number == 0) {

                s.PlayFX(R.raw.win_card, getContext(), false);
                tvWin.setText("CONGRATS!! YOU WON A CARD!!");
                HeroCard newCard = deck.dealCard();
                myDeck.addCard(newCard);

                myReff.child("users").child(auth.getCurrentUser().getUid()).child("myDeck").setValue(myDeck);
                myReff.child("users").child(auth.getCurrentUser().getUid()).child("deck").setValue(deck);


                hcLuckyCard.setCard(newCard);
                hcLuckyCard.setVisibility(View.VISIBLE);
                hcLuckyCard.postDelayed(() -> {
                    hcLuckyCard.flipCard();
                }, 1000);
            } else {
                s.PlayFX(R.raw.not_win_card, getContext(), false);
                tvWin.setText("TRY AGAIN TOMORROW!!");
                ivNotLuck.setVisibility(View.VISIBLE);

            }
            wheel.setVisibility(View.GONE);
            myReff.child("users").child(auth.getCurrentUser().getUid()).child("lastDay").setValue(today);
        });
        return root;
    }
}
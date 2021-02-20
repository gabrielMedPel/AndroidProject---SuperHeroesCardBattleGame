package gabriel.medpel.superheroescardbattlegame.models;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import gabriel.medpel.superheroescardbattlegame.R;

public class HeroCardView extends LinearLayout {
    private ImageView heroCard, cardBack, cardFrame;
    private TextView tvHeroNameFullCard;
    private HeroCard mCard;
    private boolean isFront = true;
    private boolean isFlipped = false;
    private AnimatorSet frontAnim, frontAnim2, frontAnim3, backAnim, backAnim2, backAnim3;

    public HeroCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.full_hero_card, this);

        heroCard = findViewById(R.id.heroCard);
        cardBack = findViewById(R.id.cardBack);
        cardFrame = findViewById(R.id.cardFrame);
        tvHeroNameFullCard = findViewById(R.id.tvHeroNameFullCard);

        cardFrame.setImageResource(R.drawable.moldura);
        cardBack.setImageResource(R.drawable.card_back);

        float scale = getResources().getDisplayMetrics().density;
        heroCard.setCameraDistance(8000 * scale);
        cardBack.setCameraDistance(8000 * scale);
        cardFrame.setCameraDistance(8000 * scale);
        tvHeroNameFullCard.setCameraDistance(8000 * scale);


        frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.front_animator);
        frontAnim2 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.front_animator);
        frontAnim3 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.front_animator);
        backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.back_animator);
        backAnim2 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.back_animator);
        backAnim3 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.back_animator);

    }

    public void flipCard() {
        isFlipped = true;
        if (isFront) {
            frontAnim.setTarget(cardBack);
            backAnim.setTarget(heroCard);
            backAnim2.setTarget(cardFrame);
            backAnim3.setTarget(tvHeroNameFullCard);
            frontAnim.start();
            backAnim.start();
            backAnim2.start();
            backAnim3.start();
            isFront = false;
        } else {
            frontAnim.setTarget(heroCard);
            frontAnim2.setTarget(cardFrame);
            frontAnim3.setTarget(tvHeroNameFullCard);
            backAnim.setTarget(cardBack);
            frontAnim.start();
            frontAnim2.start();
            frontAnim3.start();
            backAnim.start();
            isFront = true;
        }
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setCard(HeroCard card) {
        this.mCard = card;

        Picasso.get().load(mCard.getUrlImage()).into(heroCard);

        tvHeroNameFullCard.setText(mCard.getHeroName());

    }




}

package gabriel.medpel.superheroescardbattlegame.ui.mycards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gabriel.medpel.superheroescardbattlegame.R;
import gabriel.medpel.superheroescardbattlegame.models.HeroCard;
import gabriel.medpel.superheroescardbattlegame.models.HeroCardView;


public class HeroCardAdapter extends RecyclerView.Adapter<HeroCardAdapter.ViewHolder> {
    private ArrayList<HeroCard> heroCards;


    public HeroCardAdapter(ArrayList<HeroCard> heroCards) {
        this.heroCards = heroCards;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Inflate
        View heroCardItem = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.hero_card_item,
                parent,
                false
        );
        return new ViewHolder(heroCardItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HeroCard heroCard = heroCards.get(position);

        holder.heroCardView.setCard(heroCard);
        if (!holder.heroCardView.isFlipped()) {
            holder.heroCardView.flipCard();
        }


        holder.itemView.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();

            HeroInfoFragment myFragment = new HeroInfoFragment(heroCard.getHeroName(), heroCard.getUrlImage(),
                    heroCard.getIntelligence(), heroCard.getStrength(), heroCard.getSpeed(), heroCard.getDurability(),
                    heroCard.getPower(), heroCard.getCombat(), heroCard.getGender(), heroCard.getRace(), heroCard.getHeight(),
                    heroCard.getWeight(), heroCard.getEyeColor(), heroCard.getHairColor(), heroCard.getBiography(),
                    heroCard.getWork(), heroCard.getConnections(), heroCard.getSmallImageUrl());
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .add(R.id.containerCardInfo, myFragment)
                    .commit();

        });

    }

    @Override
    public int getItemCount() {
        return heroCards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        HeroCardView heroCardView;
        TextView tvCardsLeft;
        ImageView ivHeroImgInfo;
        TextView tvHeroInfoName, tvIntelligence, tvStrength, tvSpeed, tvDurability, tvPower, tvCombat, tvGender, tvRace, tvHeight, tvWeigth, tvEyeColor, tvHairColor, tvBiography, tvWork, tvConnections;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            heroCardView = itemView.findViewById(R.id.heroCardView);
            ivHeroImgInfo = itemView.findViewById(R.id.ivHeroImgInfo);
            tvHeroInfoName = itemView.findViewById(R.id.tvHeroNameInfo);
            tvIntelligence = itemView.findViewById(R.id.tvIntelligence);
            tvStrength = itemView.findViewById(R.id.tvStrength);
            tvSpeed = itemView.findViewById(R.id.tvSpeed);
            tvDurability = itemView.findViewById(R.id.tvDurability);
            tvPower = itemView.findViewById(R.id.tvPower);
            tvCombat = itemView.findViewById(R.id.tvCombat);
            tvGender = itemView.findViewById(R.id.tvGender);
            tvRace = itemView.findViewById(R.id.tvRace);
            tvHeight = itemView.findViewById(R.id.tvHeight);
            tvWeigth = itemView.findViewById(R.id.tvWeigth);
            tvEyeColor = itemView.findViewById(R.id.tvEyeColor);
            tvHairColor = itemView.findViewById(R.id.tvHairColor);
            tvBiography = itemView.findViewById(R.id.tvBiography);
            tvWork = itemView.findViewById(R.id.tvWork);
            tvConnections = itemView.findViewById(R.id.tvConnections);


        }
    }
}

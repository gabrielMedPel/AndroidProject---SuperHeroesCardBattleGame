package gabriel.medpel.superheroescardbattlegame.ui.mycards;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import gabriel.medpel.superheroescardbattlegame.R;


public class HeroInfoFragment extends Fragment {
    private ImageView ivHeroImgInfo;
    private Button btnBack;
    private TextView tvHeroInfoName, tvIntelligence, tvStrength, tvSpeed, tvDurability, tvPower, tvCombat, tvGender, tvRace, tvHeight, tvWeigth, tvEyeColor, tvHairColor, tvBiography, tvWork, tvConnections;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NAME = "name";
    private static final String URLIMAGE = "urlImage";
    private static final String INTELLIGENCE = "intelligence";
    private static final String STRENGTH = "strength";
    private static final String SPEED = "speed";
    private static final String DURABILITY = "durability";
    private static final String POWER = "power";
    private static final String COMBAT = "combat";
    private static final String GENDER = "gender";
    private static final String RACE = "race";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String EYECOLOR = "eyeColor";
    private static final String HAIRCOLOR = "hairColor";
    private static final String BIOGRAPHY = "biography";
    private static final String WORK = "work";
    private static final String CONNECTIONS = "connections";


    // TODO: Rename and change types of parameters
    private String name;
    private String urlImage;
    private String intelligence;
    private String strength;
    private String speed;
    private String durability;
    private String power;
    private String combat;
    private String gender;
    private String race;
    private String height;
    private String weight;
    private String eyeColor;
    private String hairColor;
    private String biography;
    private String work;
    private String connections;
    private String smallImgUrl;

    public HeroInfoFragment(String name, String urlImage, String intelligence, String strength, String speed, String durability, String power, String combat, String gender, String race, String height, String weight, String eyeColor, String hairColor, String biography, String work, String connections, String smallImgUrl) {
        this.name = name;
        this.urlImage = urlImage;
        this.intelligence = intelligence;
        this.strength = strength;
        this.speed = speed;
        this.durability = durability;
        this.power = power;
        this.combat = combat;
        this.gender = gender;
        this.race = race;
        this.height = height;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.biography = biography;
        this.work = work;
        this.connections = connections;
        this.smallImgUrl = smallImgUrl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hero_info, container, false);
        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                back();
                return true;
            }
            return false;
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivHeroImgInfo = view.findViewById(R.id.ivHeroImgInfo);
        tvHeroInfoName = view.findViewById(R.id.tvHeroNameInfo);
        tvIntelligence = view.findViewById(R.id.tvIntelligence);
        tvStrength = view.findViewById(R.id.tvStrength);
        tvSpeed = view.findViewById(R.id.tvSpeed);
        tvDurability = view.findViewById(R.id.tvDurability);
        tvPower = view.findViewById(R.id.tvPower);
        tvCombat = view.findViewById(R.id.tvCombat);
        tvGender = view.findViewById(R.id.tvGender);
        tvRace = view.findViewById(R.id.tvRace);
        tvHeight = view.findViewById(R.id.tvHeight);
        tvWeigth = view.findViewById(R.id.tvWeigth);
        tvEyeColor = view.findViewById(R.id.tvEyeColor);
        tvHairColor = view.findViewById(R.id.tvHairColor);
        tvBiography = view.findViewById(R.id.tvBiography);
        tvWork = view.findViewById(R.id.tvWork);
        tvConnections = view.findViewById(R.id.tvConnections);

        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            back();
        });

        Picasso.get().load(urlImage).into(ivHeroImgInfo);
        tvHeroInfoName.setText(name);
        tvIntelligence.setText(intelligence);
        tvStrength.setText(strength);
        tvSpeed.setText(speed);
        tvDurability.setText(durability);
        tvPower.setText(power);
        tvCombat.setText(combat);
        tvGender.setText(gender);
        tvRace.setText(race);
        tvHeight.setText(height);
        tvWeigth.setText(weight);
        tvEyeColor.setText(eyeColor);
        tvHairColor.setText(hairColor);
        tvBiography.setText(biography);
        tvWork.setText(work);
        tvConnections.setText(connections);
    }


    public void back() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        activity.getSupportFragmentManager().beginTransaction().remove(FragmentManager.findFragment(getView())).commit();
    }


}
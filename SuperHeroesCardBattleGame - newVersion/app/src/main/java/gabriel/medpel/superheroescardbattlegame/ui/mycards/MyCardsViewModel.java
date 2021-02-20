package gabriel.medpel.superheroescardbattlegame.ui.mycards;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import gabriel.medpel.superheroescardbattlegame.models.HeroCard;
import gabriel.medpel.superheroescardbattlegame.models.HeroDataSource;


public class MyCardsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<HeroCard>> heroCards;


    public MyCardsViewModel() {
        heroCards = new MutableLiveData<>();

        HeroDataSource ds = new HeroDataSource();
        ds.readDemo(heroCards);

    }

    public LiveData<ArrayList<HeroCard>> getHeroCards() {
        return heroCards;
    }
}
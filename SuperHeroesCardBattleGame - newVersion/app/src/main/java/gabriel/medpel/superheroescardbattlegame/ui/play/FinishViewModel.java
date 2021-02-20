package gabriel.medpel.superheroescardbattlegame.ui.play;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import gabriel.medpel.superheroescardbattlegame.models.Deck;
import gabriel.medpel.superheroescardbattlegame.ui.FirebaseLiveData;

public class FinishViewModel extends ViewModel {
    private MutableLiveData<Deck> deckMLD;
    private MutableLiveData<Deck> myDeckMLD;
    private MutableLiveData<Deck> oppDeckMLD;
    private MutableLiveData<Integer> winsMLD;


    public FinishViewModel() {
        deckMLD = new MutableLiveData<>();
        myDeckMLD = new MutableLiveData<>();
        oppDeckMLD = new MutableLiveData<>();
        winsMLD = new MutableLiveData<>();

        FirebaseLiveData ds = new FirebaseLiveData();
        ds.readDemoWin(deckMLD, myDeckMLD, winsMLD, oppDeckMLD);
    }

    public LiveData<Deck> getDeck() {
        return deckMLD;
    }

    public LiveData<Deck> getMyDeck() {
        return myDeckMLD;
    }

    public LiveData<Deck> getOppDeck() {
        return oppDeckMLD;
    }

    public LiveData<Integer> getWins() {
        return winsMLD;
    }
}
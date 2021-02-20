package gabriel.medpel.superheroescardbattlegame.ui.play;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import gabriel.medpel.superheroescardbattlegame.models.Deck;
import gabriel.medpel.superheroescardbattlegame.ui.FirebaseLiveData;

public class GameViewModel extends ViewModel {
    private MutableLiveData<Deck> deckMLD;
    private MutableLiveData<Deck> myDeckMLD;
    private MutableLiveData<Deck> opponentDeckMLD;
    private MutableLiveData<String> opponentNameMLD;


    public GameViewModel() {
        deckMLD = new MutableLiveData<>();
        myDeckMLD = new MutableLiveData<>();
        opponentDeckMLD = new MutableLiveData<>();
        opponentNameMLD = new MutableLiveData<>();

        FirebaseLiveData ds = new FirebaseLiveData();
        ds.readDemo(deckMLD, myDeckMLD,opponentDeckMLD,opponentNameMLD);
    }

    public LiveData<Deck> getDeck() {
        return deckMLD;
    }

    public LiveData<Deck> getMyDeck() {
        return myDeckMLD;
    }

    public LiveData<Deck> getOpponentDeck() {
        return opponentDeckMLD;
    }

    public LiveData<String> getOpponentName() {
        return opponentNameMLD;
    }
}
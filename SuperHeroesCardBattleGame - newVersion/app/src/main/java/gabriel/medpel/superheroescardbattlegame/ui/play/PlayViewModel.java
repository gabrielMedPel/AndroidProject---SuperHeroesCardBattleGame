package gabriel.medpel.superheroescardbattlegame.ui.play;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import gabriel.medpel.superheroescardbattlegame.ui.FirebaseLiveData;

public class PlayViewModel extends ViewModel {

    private MutableLiveData<String> nameMLD;

    public PlayViewModel() {
        nameMLD = new MutableLiveData<>();

        FirebaseLiveData ds = new FirebaseLiveData();
        ds.readDemoLastName(nameMLD);

    }

    public LiveData<String> getUserName() {
        return nameMLD;
    }
}
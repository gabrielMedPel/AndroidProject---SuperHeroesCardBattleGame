package gabriel.medpel.superheroescardbattlegame.ui.tryyourluck;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import gabriel.medpel.superheroescardbattlegame.ui.FirebaseLiveData;

public class TryYourLuckViewModel extends ViewModel {
    private MutableLiveData<Integer> lastDayMLD;


    public TryYourLuckViewModel() {
        lastDayMLD = new MutableLiveData<>();


        FirebaseLiveData ds = new FirebaseLiveData();
        ds.readDemoLastDayLuck(lastDayMLD);
    }

    public LiveData<Integer> getLastDay() {
        return lastDayMLD;
    }


}
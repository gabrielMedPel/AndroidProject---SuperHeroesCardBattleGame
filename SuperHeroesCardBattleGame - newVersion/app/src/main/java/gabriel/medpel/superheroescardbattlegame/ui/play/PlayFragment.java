package gabriel.medpel.superheroescardbattlegame.ui.play;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import gabriel.medpel.superheroescardbattlegame.R;
import gabriel.medpel.superheroescardbattlegame.Song;


public class PlayFragment extends Fragment {

    private Song s;
    private PlayViewModel playViewModel;
    private String name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        playViewModel =
                new ViewModelProvider(this).get(PlayViewModel.class);
        View root = inflater.inflate(R.layout.fragment_play, container, false);

        Button btnStart = root.findViewById(R.id.btnStart);

        s = new Song();
        s.PlaySong(R.raw.game_music, true, getContext());

        btnStart.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();

            GameFragment myFragment = new GameFragment();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.playContainer, myFragment)
                    .commit();
        });
        return root;
    }
}
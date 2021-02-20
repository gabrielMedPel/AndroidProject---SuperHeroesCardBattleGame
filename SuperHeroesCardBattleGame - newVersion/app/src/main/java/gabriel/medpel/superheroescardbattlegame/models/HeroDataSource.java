package gabriel.medpel.superheroescardbattlegame.models;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HeroDataSource {
    private String address = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/all.json";

    public void readDemo(MutableLiveData<ArrayList<HeroCard>> callback) {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();

        Call call = httpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ArrayList<HeroCard> heroCards = new ArrayList<>();

                String json = response.body().string();


                try {
                    JSONArray heroesJsonArray = new JSONArray(json);
                    for (int i = 0; i < heroesJsonArray.length(); i++) {
                        JSONObject heroCardJsonObject = heroesJsonArray.getJSONObject(i);

                        String heroName = heroCardJsonObject.getString("name");

                        JSONObject heroCardImages = heroesJsonArray.getJSONObject(i).getJSONObject("images");

                        String urlImage = heroCardImages.getString("lg");
                        String smallImgUrl = heroCardImages.getString("sm");


                        JSONObject heroPowerStats = heroesJsonArray.getJSONObject(i).getJSONObject("powerstats");

                        String intelligence = heroPowerStats.getString("intelligence");
                        String strength = heroPowerStats.getString("strength");
                        String speed = heroPowerStats.getString("speed");
                        String durability = heroPowerStats.getString("durability");
                        String power = heroPowerStats.getString("power");
                        String combat = heroPowerStats.getString("combat");

                        JSONObject heroAppearance = heroesJsonArray.getJSONObject(i).getJSONObject("appearance");

                        String gender = heroAppearance.getString("gender");
                        String race = heroAppearance.getString("race");
                        String height = heroAppearance.getJSONArray("height").getString(1);
                        String weight = heroAppearance.getJSONArray("weight").getString(1);
                        String eyeColor = heroAppearance.getString("eyeColor");
                        String hairColor = heroAppearance.getString("hairColor");

                        JSONObject heroBiography = heroesJsonArray.getJSONObject(i).getJSONObject("biography");

                        String biography = "Full name: " + heroBiography.getString("fullName")
                                + "\nPlace of birth: " + heroBiography.getString("placeOfBirth")
                                + "\nPublisher: " + heroBiography.getString("publisher")
                                + "\nAlignment: " + heroBiography.getString("alignment");

                        JSONObject heroWork = heroesJsonArray.getJSONObject(i).getJSONObject("work");

                        String work = "Occupation: " + heroWork.getString("occupation")
                                + "\nBase: " + heroWork.getString("base");

                        JSONObject heroConnections = heroesJsonArray.getJSONObject(i).getJSONObject("connections");

                        String connections = "Group affiliation: " + heroConnections.getString("groupAffiliation")
                                + "\nRelatives: " + heroConnections.getString("relatives");

                        heroCards.add(new HeroCard(heroName, urlImage, intelligence, strength, speed, durability, power
                                , combat, gender, race, height, weight, eyeColor, hairColor, biography, work, connections, smallImgUrl));


                    }


                    callback.postValue(heroCards);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

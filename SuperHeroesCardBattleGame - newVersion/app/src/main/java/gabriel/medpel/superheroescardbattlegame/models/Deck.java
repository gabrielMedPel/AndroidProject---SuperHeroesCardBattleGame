package gabriel.medpel.superheroescardbattlegame.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    ArrayList<HeroCard> deck = new ArrayList<>();

    public Deck() {
    }

    public ArrayList<HeroCard> getDeck() {
        return deck;
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public HeroCard dealCard() {
        shuffle();
        return deck.remove(0);
    }

    public void addCard(HeroCard card) {
        deck.add(card);
    }

    public int cardsLeft() {
        return deck.size();
    }

//    public HeroCard pickRandomCard(){
//        Random rand = new Random();
//        int randomNumber = rand.nextInt(cardsLeft());
//        return deck.get(randomNumber);
//    }
}

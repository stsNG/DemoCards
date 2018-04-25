package com.earlymath.additionprops.cards;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;

public class Deck {

    private static ArrayList<Image> cardAssets;
    private static boolean loaded = false;


    public static final int BACK = 10;
    public static final int STACK = 11;
    public static final int EQUALS = 12;
    public static final int PLUS = 13;
    public static final int DEAL = 14;
    public static final int RETURN = 15;
    public static final int SHUFFLE = 16;

    private static void init() {
        cardAssets = new ArrayList<Image>();

        File file;

        for (int i = 0; i < 10; ++i) {
            file = new File("./res/Cards/card_" + i +".png");
            cardAssets.add(new Image(file.toURI().toString()));
        }

        //add the back card as card ten.
        file = new File("./res/Cards/card_back.png");
        cardAssets.add(new Image(file.toURI().toString()));

        //add the cards stack as card eleven.
        file = new File("./res/Cards/card_stack.png");
        cardAssets.add(new Image(file.toURI().toString()));

        //add the plus card as card twelve.
        file = new File("./res/Cards/card_equals.png");
        cardAssets.add(new Image(file.toURI().toString()));

        //add the plus card as card thirteen.
        file = new File("./res/Cards/card_plus.png");
        cardAssets.add(new Image(file.toURI().toString()));

        file = new File("./res/Cards/deal.png");
        cardAssets.add(new Image(file.toURI().toString()));

        file = new File("./res/Cards/return.png");
        cardAssets.add(new Image(file.toURI().toString()));

        file = new File("./res/Cards/shuffle.png");
        cardAssets.add(new Image(file.toURI().toString()));

        loaded = true;
    }

    private static void ready() {
        if (!loaded) {
            init();
        }
    }

    public static Image GetCardAsset(int value) {
        ready();
        return cardAssets.get(value);
    }

    public static ArrayList<Card> GetCardSet() {
        ArrayList<Card> set = new ArrayList<Card>();
        for (int i = 0; i < 10; i++) {
            System.out.println("Adding card: "+i+" to deck.");
            set.add(new Card(i));
        }

        return set;
    }

}

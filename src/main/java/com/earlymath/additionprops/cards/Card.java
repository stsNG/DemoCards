package com.earlymath.additionprops.cards;

import javafx.scene.canvas.GraphicsContext;

public class Card {

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Card(int value) {
        this.value = value;
    }

    public void draw(GraphicsContext gc, double x, double y) {
        draw(gc, x, y, 1.0);
    }

    public void draw(GraphicsContext gc, double x, double y, double s) {
        if ( value < Deck.STACK) {
            gc.drawImage( Deck.GetCardAsset(value),  x-((160*s)/2), y-((216*s)/2), 160*s, 216*s);
        }
        else if (value == Deck.STACK)  {
            //draw stack asset with different off set.
            gc.drawImage(Deck.GetCardAsset(value),  x-((186*s)/2), y-((242*s)/2), 186*s, 242*s);
        }
        else if (value > Deck.STACK) {
            gc.drawImage( Deck.GetCardAsset(value),  x-((160*s)/2), y-((216*s)/2), 160*s, 216*s);
        }
    }

}

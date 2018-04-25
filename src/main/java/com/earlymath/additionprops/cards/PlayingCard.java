package com.earlymath.additionprops.cards;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;

public class PlayingCard {
    private Card card;
    private DoubleProperty x;
    private DoubleProperty y;
    private double scale;
    private boolean faceUp;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public double getX() {
        return x.getValue();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.setValue(x);
    }

    public double getY() {
        return y.getValue();
    }

    public DoubleProperty yProperty() {
        return y;
    }
    public void setY(double y) {
        this.y.setValue(y);
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void flip() {
        faceUp = !faceUp;
    }

    public void flip(boolean up) {
        faceUp = up;
    }

    public PlayingCard(Card card, double x, double y, double s, boolean showing)  {
        this(card, x, y, s);
        this.faceUp = showing;
}

    public PlayingCard(Card card, double x, double y, double s) {
        this.card = card;
        this.x = new SimpleDoubleProperty();
        this.x.setValue(x);
        this.y = new SimpleDoubleProperty();
        this.y.set(y);
        this.scale = s;
        this.faceUp = true;
    }

    public PlayingCard(Card card, double x, double y) {
        this(card, x, y, 1.0);
    }

    public void draw(GraphicsContext gc) {
        draw(gc, scale);
    }

    public void draw(GraphicsContext gc, double s) {
        if (faceUp) {
            card.draw(gc, x.getValue(), y.getValue(), s);
        }
        else {
            new Card(Deck.BACK).draw(gc, x.getValue(), y.getValue(), s);
        }
    }

}

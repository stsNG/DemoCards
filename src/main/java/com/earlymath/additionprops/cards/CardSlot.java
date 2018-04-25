package com.earlymath.additionprops.cards;

import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventListener;

public class CardSlot {

    private int x;
    private int y;
    private Dealer dealer;
    private ArrayList<PlayingCard> cardStack;
    private Button dealButton;
    private Button returnButton;
    private GraphicsContext graphicsContext;

    public void setDiscard(Dealer discard) {
        this.discard = discard;
    }

    private Dealer discard;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDealer(Dealer d) {
        this.dealer = d;
    }

    public void accept(PlayingCard card) {
        cardStack.add(0, card);
        card.setX(x);
        card.setY(y);
    }

    public CardSlot(int x, int y) {
        cardStack = new ArrayList<>();
        dealButton = new Button();
        dealButton.setGraphic(new ImageView(Deck.GetCardAsset(Deck.DEAL)));
        dealButton.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                dealToMe();
            }
        });
        dealButton.relocate(x-90, y-120);
        returnButton = new Button();
        returnButton.setGraphic(new ImageView(Deck.GetCardAsset(Deck.RETURN)));
        returnButton.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                returnTop();
            }
        });
        returnButton.relocate(x+31, y+90);
        this.x = x;
        this.y = y;
    }

    private void returnTop() {
        discard.returnCardAnimated(cardStack.remove(0), graphicsContext, false );
    }

    public void draw(GraphicsContext gc) {

        gc.strokeRect(x-90.0, y-120, 180, 240);

        if (!cardStack.isEmpty()) {
            if (cardStack.size() > 1) {
                new PlayingCard( new Card( Deck.STACK), x, y).draw(gc, 1.0);
            }
            cardStack.get(0).draw(gc, 1.0);
        }
    }

    public void registerButtons(ObservableList<Node> children) {
        children.add(dealButton);
        children.add(returnButton);
    }

    public void setGraphicsContext(GraphicsContext gc) {
        this.graphicsContext = gc;
    }

    private void dealToMe() {
        dealer.dealCardAnimated(this, graphicsContext);
    }
}

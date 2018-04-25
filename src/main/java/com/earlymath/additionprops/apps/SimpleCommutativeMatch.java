package com.earlymath.additionprops.apps;

import com.earlymath.additionprops.cards.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class SimpleCommutativeMatch extends Application {

    private int width = 1024;
    private int height = 768;
    private GraphicsContext gc;

    private AnimationTimer drawTimer;

    private Dealer dealer;
    private Dealer discard;

    private CardSlot leftMost;
    private CardSlot leftMid;
    private CardSlot rightMid;
    private CardSlot rightMost;
    private PlayingCard leftPlus;
    private PlayingCard equal;
    private PlayingCard rightPlus;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple Commutative Match");
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));

        discard = new Dealer( 3*(width/4)+100, (height/3)*0.5 );
        discard.setGraphicsContext(gc);
        discard.setFaceUp(false);

        dealer = new Dealer( 3*(width/4)-80, (height/3)*0.5 );
        dealer.setGraphicsContext(gc);
        dealer.setFaceUp(true);
        dealer.addCard(3, true);
        dealer.addCard(5, true);
        dealer.addCard(3, true);
        dealer.addCard(5, true);

        dealer.setDiscard(discard);
        discard.setDiscard(dealer);

        dealer.registerButtons(root.getChildren());
        discard.registerButtons(root.getChildren());

        leftMost = new CardSlot( (width/4)-150, (2*(height/3))-130 );
        leftMost.setDealer(dealer);
        leftMost.setDiscard(discard);
        leftMost.setGraphicsContext(gc);
        leftMost.registerButtons(root.getChildren());

        leftPlus = new PlayingCard(new Card(Deck.PLUS), (width/4)-15, (2*(height/3))-130 );

        leftMid = new CardSlot( (width/4)+120, (2*(height/3))-130 );
        leftMid.setDealer(dealer);
        leftMid.setDiscard(discard);
        leftMid.setGraphicsContext(gc);
        leftMid.registerButtons(root.getChildren());

        equal = new PlayingCard(new Card(Deck.EQUALS), (width/2), (2*(height/3))-130);

        rightMid = new CardSlot( (3*(width/4))-120, (2*(height/3))-130 );
        rightMid.setDealer(dealer);
        rightMid.setDiscard(discard);
        rightMid.setGraphicsContext(gc);
        rightMid.registerButtons(root.getChildren());

        rightPlus = new PlayingCard(new Card(Deck.PLUS), (3*(width/4))+15, (2*(height/3))-130 );

        rightMost = new CardSlot( (3*(width/4))+150, (2*(height/3))-130 );
        rightMost.setDealer(dealer);
        rightMost.setDiscard(discard);
        rightMost.setGraphicsContext(gc);
        rightMost.registerButtons(root.getChildren());

        drawTimer = new AnimationTimer() {
            private long last;
            @Override
            public void handle(long now) {
                if (now - last > 16_600_000 ) {
                    drawGame(gc);
                    last = now;
                }
            }
        };

        primaryStage.show();
        drawTimer.start();
    }

    private void drawGame(GraphicsContext gc) {
        gc.clearRect(0, 0, width, height);
        dealer.draw(gc);
        discard.draw(gc);

        leftMost.draw(gc);
        leftPlus.draw(gc, .75);
        leftMid.draw(gc);
        equal.draw(gc, .75);

        rightMid.draw(gc);
        rightPlus.draw(gc, .75);
        rightMost.draw(gc);

    }
}

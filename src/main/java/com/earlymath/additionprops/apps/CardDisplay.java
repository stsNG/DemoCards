package com.earlymath.additionprops.apps;

import com.earlymath.additionprops.cards.Card;
import com.earlymath.additionprops.cards.Deck;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CardDisplay extends Application {

    private ArrayList<Card> cards;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        cards = Deck.GetCardSet();
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(825, 450);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawCards(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawCards(GraphicsContext gc) {
        int i = 0;
        for(Card c: cards) {
            System.out.println("drawing card: "+i);
            c.draw(gc, ((i%5)*160.0)+85 , ((i/5)*216.0)+113 );
            i++;
        }
    }


}

package com.earlymath.additionprops.cards;

import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class Dealer {


    private double x;
    private double y;
    private boolean faceUp;
    private Dealer discard;
    private ArrayList<PlayingCard> cards;
    private Button shuffleButton;
    private GraphicsContext graphicsContext;
    private AnimationTimer shuffleTimer;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public void setDiscard(Dealer discard) {
        this.discard = discard;
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public void addCard(PlayingCard card) {
        card.flip(faceUp);
        this.cards.add(card);
    }

    public void addCard(Card card, boolean faceUp) {
        addCard(new PlayingCard( card, x, y, 1.0, faceUp ));
    }

    public void addCard(Card card) {
        addCard(new PlayingCard( card, x, y, 1.0, false ));
    }

    public void addCard(int value, boolean faceUp) {
        addCard(new Card(value), faceUp);
    }

    public void addCard(int value) {
        addCard(new Card(value));
    }

    public PlayingCard dealCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        else {
            return new PlayingCard(new Card(Deck.BACK), x, y);
        }
    }

    public void dealCard(CardSlot target) {
        target.accept(cards.remove(0));
    }

    public void dealToDiscard() {

        if (cards.isEmpty()) {
            shuffleTimer.stop();
            return;
        }

        PlayingCard next = dealCard();

        AnimationTimer timer = new AnimationTimer() {
            private long last;
            @Override
            public void handle(long now) {
                if (now - last > 16_600_000 ) {
                    next.draw(graphicsContext, 1.0);
                    last = now;
                }
            }
        };

        final Timeline tl = new Timeline();
        tl.setCycleCount(1);

        final KeyValue[] kvArray = new KeyValue[2];
        kvArray[0] = new KeyValue(next.xProperty(), discard.getX(), Interpolator.EASE_BOTH);
        kvArray[1] = new KeyValue(next.yProperty(), discard.getY(), Interpolator.EASE_BOTH);
        final KeyFrame kf = new KeyFrame(Duration.millis(1000), kvArray);

        tl.getKeyFrames().add(kf);
        tl.setOnFinished((event) -> {
                    discard.addCard(next);
                    timer.stop();
                }
        );

        tl.play();
        timer.start();

    }

    public void dealCardAnimated(CardSlot target, GraphicsContext gc) {
        PlayingCard animCard = dealCard();

        AnimationTimer timer = new AnimationTimer() {
            private long last;
            @Override
            public void handle(long now) {
                if (now - last > 16_600_000 ) {
                    animCard.draw(gc, 1.0);
                    last = now;
                }
            }
        };

        final Timeline tl = new Timeline();
        tl.setCycleCount(1);

        final KeyValue[] kvArray = new KeyValue[2];
        kvArray[0] = new KeyValue(animCard.xProperty(), target.getX(), Interpolator.EASE_BOTH);
        kvArray[1] = new KeyValue(animCard.yProperty(), target.getY(), Interpolator.EASE_BOTH);
        final KeyFrame kf = new KeyFrame(Duration.millis(1000), kvArray);

        tl.getKeyFrames().add(kf);
        tl.setOnFinished((event) -> {
                target.accept(animCard);
                timer.stop();
            }
        );

        tl.play();
        timer.start();
    }

    public void returnCardAnimated( PlayingCard returnedCard, GraphicsContext gc, Boolean faceUp) {
        AnimationTimer timer = new AnimationTimer() {
            private long last;
            @Override
            public void handle(long now) {
                if (now - last > 16_600_000 ) {
                    returnedCard.draw(gc, 1.0);
                    last = now;
                }
            }
        };

        final Timeline tl = new Timeline();
        tl.setCycleCount(1);

        final KeyValue[] kvArray = new KeyValue[2];
        kvArray[0] = new KeyValue(returnedCard.xProperty(), this.x, Interpolator.EASE_BOTH);
        kvArray[1] = new KeyValue(returnedCard.yProperty(), this.y, Interpolator.EASE_BOTH);
        final KeyFrame kf = new KeyFrame(Duration.millis(1000), kvArray);

        tl.getKeyFrames().add(kf);
        tl.setOnFinished((event) -> {
                    returnedCard.flip(faceUp);
                    addCard(returnedCard);
                    timer.stop();
                }
        );

        tl.play();
        timer.start();
    }

    public Dealer(double x, double y) {
        this(x, y, new ArrayList<PlayingCard>());
    }

    public Dealer(double x, double y, ArrayList<PlayingCard> cards) {
        this.x = x;
        this.y = y;
        this.cards = cards;

        shuffleButton = new Button();
        shuffleButton.setGraphic(new ImageView(Deck.GetCardAsset(Deck.SHUFFLE)));
        shuffleButton.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {

                shuffleTimer = new AnimationTimer() {
                    private long last;
                    @Override
                    public void handle(long now) {
                        if (now - last > (6_600_000 * 100)) {
                            dealToDiscard();
                            last = now;
                        }
                    }
                };
                shuffleTimer.start();
            }
        });
        shuffleButton.relocate(x+31, y+90);
    }

    public void draw(GraphicsContext gc) {
        if (!cards.isEmpty()) {
            if (cards.size() > 1) {
                new PlayingCard( new Card(Deck.STACK), x, y).draw(gc, 1.0);
            }
            cards.get(0).draw(gc, 1.0);
        }
    }

    public void registerButtons(ObservableList<Node> children) {
        children.add(shuffleButton);
    }
}

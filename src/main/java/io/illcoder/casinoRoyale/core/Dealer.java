package io.illcoder.casinoRoyale.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by syoung on 9/22/15.
 */
public class Dealer {

    private Deck deck = new Deck;
    private List<Card> cardDeck = deck.getShuffledDeck();
    private int cardIndex = 0;
    public Dealer(){

    }



    /**
     * Starts a game depending on the gameType argument that is passed.
     *
     *
    public void startGame(int _gametype, Player _user) {
        switch (_gametype) {
            case 1:
                BlackjackEngine game = new BlackjackEngine;
                game.runGame(_user);
                break;
            case 2:
                PokerEngine game = new PokerEngine;
                game.runGame(_user);
                break;
        }
     }
*/
    public Card dealCard() {
        Card topCard = cardDeck[cardIndex];
        cardIndex++;
        return topCard;
    }




    /**
     * Deals hand to the player and CPU and
     * @return a String with message saying you got your hand.
     */
    public String dealHand(){
        String dealHandMessage = "That's your hand, deal with it!";
        return dealHandMessage;
    }

    public String quit(){
        return "Returning to main menu";
    }

}
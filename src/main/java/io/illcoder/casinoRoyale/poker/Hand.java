package io.illcoder.casinoRoyale.poker;

import io.illcoder.casinoRoyale.core.Card;
import io.illcoder.casinoRoyale.core.Dealer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by seth on 9/28/2015.
 * class to evaluate hand strength
 */
public class Hand {
    private List<Card> theHand;
    private int[] value;

    public Hand(List<Card> _hand) {
        theHand = _hand;
        value = new int[6];



        int[] cardRanks = new int[15];

        for (int i = 0; i < 15; i++) {
            cardRanks[i] = 0;
        }

        for (int j = 0; j < 5; j++){
            Card card = theHand.get(j);
            cardRanks[card.getCardPokerValue()]++;
            System.out.println(card.getCardPokerValue());
        }
    }

    public Hand() {
        Dealer dealer = new Dealer();
        value = new int[6];
        theHand = new ArrayList<Card>();

        for (int k = 0; k < 5; k++){
            theHand.add(dealer.dealCard());
        }

        int[] cardRanks = new int[15];

        for (int i = 0; i < 15; i++) {
            cardRanks[i] = 0;
        }

        for (int j = 0; j < 5; j++){
            Card card = theHand.get(j);
            cardRanks[card.getCardPokerValue()]++;
            System.out.println(card.getCardPokerValue());
        }
        System.out.println(Arrays.toString(cardRanks));
    }

    public List<Card> getTheHand(){
        return theHand;
    }

    public int compare(Hand compHand) {
        for (int i = 0; i < 6; i++){
            if (this.value[i] > compHand.value[i])
                return 1;
            else if (this.value[i]< compHand.value[i])
                return -1;
        }
        return 0;
    }

    public static void main(String[] args) {
        Hand hand = new Hand();
    }
}

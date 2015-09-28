package io.illcoder.casinoRoyale.poker;

import io.illcoder.casinoRoyale.core.Card;
import io.illcoder.casinoRoyale.core.Dealer;
import io.illcoder.casinoRoyale.core.Rank;
import io.illcoder.casinoRoyale.core.Suit;

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
        int sameRanks = 1;
        int sameRanks2 = 1;
        int smallRankValue = 0;
        int largeRankValue = 0;
        boolean flush = true;
        boolean straight = false;
        int topStraightRank = 0;

//        for (int k = 0; k < 5; k++){
//            theHand.add(dealer.dealCard());
//        }
        Card card1 = new Card(Suit.CLUBS, Rank.ACE);
        Card card2 = new Card(Suit.CLUBS, Rank.TEN);
        Card card3 = new Card(Suit.SPADES, Rank.TEN);
        Card card4 = new Card(Suit.CLUBS, Rank.KING);
        Card card5 = new Card(Suit.CLUBS, Rank.QUEEN);
        theHand.add(card1);
        theHand.add(card2);
        theHand.add(card3);
        theHand.add(card4);
        theHand.add(card5);
        int[] cardRanks = new int[15];

        // array to hold counts of each card rank
        for (int i = 0; i < 15; i++) {
            cardRanks[i] = 0;
        }

        // loop through the 5 cards counting those with the same rank and
        // checking for a flush
        for (int j = 0; j < 5; j++){
            //Card card = theHand.get(j);
            cardRanks[theHand.get(j).getCardPokerValue()]++;
            if (j < 4){
                if (theHand.get(j).getSuit() != theHand.get(j + 1).getSuit()){
                    flush = false;
                }
            }
            System.out.println(theHand.get(j).getCardPokerValue() + ":" + theHand.get(j).getSuit());

        }
        System.out.println(Arrays.toString(cardRanks));
        System.out.println(flush);

        for (int n = 2; n < 11; n++){
            if (cardRanks[n] == 1 && cardRanks[n+1] == 1 && cardRanks[n+2] == 1 && cardRanks[n+3] == 1 && cardRanks[n+4] == 1){
                straight = true;
                topStraightRank = n + 4;
                break;
            }
        }

        // check for A-5 wheel straight
        if (cardRanks[14] == 1 && cardRanks[2] == 1 && cardRanks[3] == 1 && cardRanks[4] == 1 && cardRanks[5] == 1){
            straight = true;
            topStraightRank = 5;
        }
        System.out.println(straight + ":" + topStraightRank);
        // goes through cardRanks array and records any pairs, trips, or quads
        for (int m = 14; m > 1; m--){
            if (cardRanks[m] > sameRanks){
                if (sameRanks != 1){
                    sameRanks2 = sameRanks;
                    smallRankValue = largeRankValue;
                }
                sameRanks = cardRanks[m];
                largeRankValue = m;
            } else if (cardRanks[m] > sameRanks2){
                sameRanks2 = cardRanks[m];
                smallRankValue = m;
            }
        }
        System.out.println(sameRanks + ":" + largeRankValue + "\n" + sameRanks2 + ":" + smallRankValue);
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

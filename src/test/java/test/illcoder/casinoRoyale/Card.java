package test.illcoder.casinoRoyale;
import test.illcoder.casinoRoyale.Rank;
import test.illcoder.casinoRoyale.Suit;

/**
 * Created by syoung on 9/22/15.
 */

public class Card {



    private Suit suit;
    private Rank rank;

    private int cardPokerValue;
    private int cardBlackjackValue;

    /**
     * Constructor for Card class
     * @param suit
     * @param rank
     */
     Card(Suit suit, Rank rank){

        this.suit = suit;
        this.rank = rank;
        this.cardPokerValue = this.rank.getPokerValue();
        this.cardBlackjackValue = this.rank.getBlackjackValue();
    }

    public Suit getSuit(){
        return this.suit;
    }

    public Rank getRank(){
        return this.rank;
    }

    public int getCardPokerValue(){
        return cardPokerValue;
    }

    public int getCardBlackjackValue(){
        return cardBlackjackValue;
    }

    public static void main(String[] args) {
        Card xcard = new Card(Suit.HEARTS, Rank.FIVE);
        System.out.println(xcard.rank);
    }
}

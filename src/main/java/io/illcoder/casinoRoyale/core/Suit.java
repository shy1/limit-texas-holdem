package io.illcoder.casinoRoyale.core;

/**
 * Created by gsuhocki on 9/23/15.
 * Add all suits for possible card game hands to the casino
 */


public enum Suit {
    SPADES("S"), HEARTS("H"), CLUBS("C"), DIAMONDS("D");

    private String altSuit;

    public Suit (String altSuit) {
        this.altSuit = altSuit;
    }

    public getAltSuit(){
        return this.altSuit;
    }
}

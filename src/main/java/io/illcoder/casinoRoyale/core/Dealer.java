package io.illcoder.casinoRoyale.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by syoung on 9/22/15.
 */
public class Dealer {
    private int wager;

    public String compareHands(){
        return "Player wins";
    }

    /**
     * Dealer constructor.
     */
    public Dealer(){

    }


    public Deck createDeck(){
        return new Deck();
    }



    /**
     * Starts the game and
     * @return Message to user letting them know which game has started.
     */
    public void runGame() throws IOException {
        String gameMessage= "Starting game... ";
        Boolean continueLoop = true;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(continueLoop){


            System.out.println("Enter response");
            String input = br.readLine();
            if (input.equals("quit"))  continueLoop = false;





        }

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
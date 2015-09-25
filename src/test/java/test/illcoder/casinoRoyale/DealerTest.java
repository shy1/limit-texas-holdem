package test.illcoder.casinoRoyale;

import io.illcoder.casinoRoyale.core.Dealer;
import io.illcoder.casinoRoyale.core.Deck;
import io.illcoder.casinoRoyale.core.Player;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by sstrauss on 9/22/15.
 */
public class DealerTest {

    Dealer dealer = new Dealer();

   @Test
    public void checkToSeeIfDealerTakesWager(){
       Assert.assertEquals("Check to see if dealer accepts wager.", "We have accepted your bet of 5", dealer.takeWager(5));
   }
//    @Test
//    public void checkRunGame() throws IOException {
//        Assert.assertEquals("Checks return message when starting game.", "Starting game... ", dealer.runGame());
//
//    }

    @Test
    public void checkMessageWhenHandIsDealt(){
        Assert.assertEquals("Checks message recieved after dealing hand", "That's your hand, deal with it!", dealer.dealHand());
    }

    @Test
    public void checkWhoseHandWinsOrIfThereIsATie(){
        Player player = new Player("Sean");
        Assert.assertEquals("Checks to see who wins and returns a string with the winner", "Player wins", dealer.compareHands() );
    }

    @Test
    public void checkQuit(){
        Assert.assertEquals("Checks to see if quit function works", "Returning to main menu", dealer.quit());
        
    }




}

package test.illcoder.casinoRoyale;

import io.illcoder.casinoRoyale.core.Card;
//import io.illcoder.casinoRoyale.core.CardValues;
import io.illcoder.casinoRoyale.core.Deck;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.List;

/**
 * Created by syoung on 9/26/15.
 */
public class ValuesTest {

    //CardValues values = new CardValues();
    Deck deckObj = new Deck();

    @Test
    public void getShuffledDeckTest() {
        List<Card> cardDeck = deckObj.getShuffledDeck();
        System.out.println(cardDeck.get(0).getRank());
        assertEquals("Testing that getShuffledDeck returns an array of cards with 52 members", 52, cardDeck.size());
    }

}

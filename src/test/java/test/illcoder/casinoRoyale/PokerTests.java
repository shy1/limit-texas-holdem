package test.illcoder.casinoRoyale;
import com.oracle.tools.packager.IOUtils;
import io.illcoder.casinoRoyale.core.Player;
import io.illcoder.casinoRoyale.poker.PokerEngine;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
/**
 * Created by syoung on 11.9.15.
 */
public class PokerTests {


    @Test
    public void testPlayerContinue() {
        // initialize input and game classes
        String input = "2";
        final InputStream istream = new ByteArrayInputStream(input.getBytes(Charset.forName("UTF-8")));
        Player aPlayer = new Player("Seth");
        PokerEngine testGame = new PokerEngine(aPlayer, istream);
        assertEquals("Test that continueLoop flag is true before running playerContinue function", true, testGame.getContinueLoop());
        testGame.playerContinue();
        assertEquals("Test that continueLoop flag is false after running playerContinue function", false, testGame.getContinueLoop());

    }
}

package test.illcoder.casinoRoyale;

import io.illcoder.casinoRoyale.core.Player;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by sstrauss on 9/25/15.
 */
public class PlayerTest {
    Player player = new Player("Sean");

    @Test
    public void testGettersForPlayerAttributes(){
        Assert.assertEquals(5, player.getMoney());
        Assert.assertEquals("sean", player.getMoney());
        Assert.assertEquals(5, player.getHandCard(2));
    }
}

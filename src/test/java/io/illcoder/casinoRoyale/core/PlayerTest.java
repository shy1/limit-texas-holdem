package io.illcoder.casinoRoyale.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sstrauss on 9/25/15.
 */
public class PlayerTest {
    Player player = new Player("Sean");
    Player player2 = new Player("Tariq");
    Card card = new Card(Suit.CLUBS, Rank.EIGHT);
    List<Card> hand = new ArrayList<Card>();

    @Test
    public void testGetName() throws Exception {

        Assert.assertEquals("Test getter for name attribute.","Sean", player.getName());
    }

    @Test
    public void testGetMoney() throws Exception {

        Assert.assertEquals("Tests the getter for the money attribute",500, player.getMoney());
    }

    @Test
    public void testAddCard() throws Exception {
        player.addCard(card);
        Assert.assertEquals("Should add 1 card to hand.", 1, player.getHand().size());
        player.addCard(new Card(Suit.DIAMONDS, Rank.JACK));
        Assert.assertEquals("Should add 1 card to hand.", 2, player.getHand().size());

    }

    @Test
    public void testSetName() throws Exception {
        Assert.assertEquals("Sean", player.getName());
        player.setName("Tariq");
        Assert.assertEquals("Tariq", player.getName());
    }

    @Test
    public void testSetMoney() throws Exception {
        Assert.assertEquals(500, player.getMoney());
        player.setMoney(50);
        Assert.assertEquals(50, player.getMoney());
    }

    @Test
    public void testGetHand() throws Exception {

        for (int i = 0; i < 3; i++){
            player.addCard(card);
        }
        Assert.assertEquals("Adds 3 cards to the hand list and then displays hand",null, player.getHand());
    }

    @Test
    public void testGetHandCard() throws Exception {
        player.addCard(card);
        player.addCard(new Card(Suit.DIAMONDS, Rank.JACK));
        Assert.assertEquals("Should display just the first card in hand", card, player.getHandCard(0));

    }


}
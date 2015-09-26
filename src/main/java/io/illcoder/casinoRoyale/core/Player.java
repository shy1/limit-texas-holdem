package io.illcoder.casinoRoyale.core;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by sstrauss on 9/24/15.
 */
public class Player {
    private String name;
    private int money = 500;
    private List<Card> hand = new ArrayList<Card>();



    public Player(String name){
        this.name = name;
    }

    public Player(){

    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public void addCard(Card card) {
        this.hand.add(card);

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<Card> getHand() {
        return hand;
    }


    public Card getHandCard(int _i){
        return hand.get(_i);
    }

    public void testingTest(){

    }


}


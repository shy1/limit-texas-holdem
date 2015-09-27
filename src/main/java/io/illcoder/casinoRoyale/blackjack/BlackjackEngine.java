package io.illcoder.casinoRoyale.blackjack;


import io.illcoder.casinoRoyale.core.Dealer;
import io.illcoder.casinoRoyale.core.GameControl;
import io.illcoder.casinoRoyale.core.Player;

import java.util.Scanner;


/**
 * Created by syoung on 9/22/15.
 */



public class BlackjackEngine {
    Dealer dealer = new Dealer();
    private final int BLACKJACK = 21;
    int wager;

    public int getBLACKJACK() {
        return BLACKJACK;
    }

    Scanner scan = new Scanner(System.in);


    /**
     * Takes players wager and subtracts it from total.
     *
     * @param player
     * @return
     */
    public void takeWager(Player player) {
        System.out.println("Please make your wager  ");
        wager = scan.nextInt();
        if (wager > player.getMoney()){
            System.out.println("We extend no credit here scrub!!");
            GameControl.pause();
            takeWager(player);

        } else {

            player.setMoney(player.getMoney() - wager);


            System.out.println("Your wager of " + " $" + wager + " has been accepted you cheapskate!!");
            System.out.println("$" + player.getMoney());

        }
    }


    /**
     * Check for bust method
     *
     * @param user
     *
     */
    public void checkUserForBust(Player user) {
        if (calcHandTotal(user) > 21) {
            gameOver(user);

        }
    }


    /**
     * call at win condition.
     *
     * @param user
     * @param cpu
     */
    public void youWin(Player user, Player cpu) {
        if (calcHandTotal(user) > calcHandTotal(cpu) && calcHandTotal(user) <= 21) {
            System.out.println("Congratulations you have won!!!");
            user.setMoney(user.getMoney() + (wager * 2));
            runGame(user);
        }
    }

        /**
         * Add a GameOver method
         */

    public void gameOver(Player user) {

        System.out.println("You lose...");
        System.out.println("  ________    _____      _____   ___________ ________   ____   _________________________ \n" +
                " /  _____/   /  _  \\    /     \\  \\_   _____/ \\_____  \\  \\   \\ /   /\\_   _____/\\______   \\\n" +
                "/   \\  ___  /  /_\\  \\  /  \\ /  \\  |    __)_   /   |   \\  \\   Y   /  |    __)_  |       _/\n" +
                "\\    \\_\\  \\/    |    \\/    Y    \\ |        \\ /    |    \\  \\     /   |        \\ |    |   \\\n" +
                " \\______  /\\____|__  /\\____|__  //_______  / \\_______  /   \\___/   /_______  / |____|_  /\n" +
                "        \\/         \\/         \\/         \\/          \\/                    \\/         \\/ ");


        runGame(user);
    }


    /**
     * Calculates the score of players cards
     *
     * @param player userPlayer or cpu Player
     * @return
     */
    public int calcHandTotal(Player player) {
        int sum = 0;
        for (int i = 0; i < player.getHand().size(); i++) {

            sum += player.getHand().get(i).getCardBlackjackValue();
        }
        return sum;
    }

    /**
     * Checks to see if either Player or Computer has BlackJack
     *
     * @param user user player
     * @param cpu  computer
     */
    public void checkBlackjack(Player user, Player cpu) {
        if (calcHandTotal(user) == getBLACKJACK() && calcHandTotal(cpu) == getBLACKJACK()) {
            System.out.println("Push, try again"); /// Needs to redirect back to begining of game and refund money.
        } else if (calcHandTotal(user) == getBLACKJACK()) {
            System.out.println("You have BlackJack!!!");
        } else if (calcHandTotal(cpu) == getBLACKJACK()) {
            System.out.println("You lose");
        }


    }


    /**
     * Gives user the choice to Hit or Stay, by entering 1 0r 2,
     * any other input will calls stay();
     *
     * @param user
     */
    public void userChoice(Player user) {
        int userChoice = scan.nextInt();

        ///Need some help here, needs to loop back until user hits stay.
        switch (userChoice) {
            case 1:
                hit(user);
                userChoice(user);
                checkUserForBust(user);
                break;
            case 2:
                stay();
                break;
            default:
                stay();
                break;
        }
    }


    /**
     * Gives player another card.
     */
    public void hit(Player player) {
        System.out.println("I see you like to live dangerously... ");
        System.out.println("Have another card");
        player.addCard(dealer.dealCard());
        displayHand(player);
        calcHandTotal(player);


    }


    /**
     * Doesn't take a card on turn
     */
    public void stay() {
        System.out.println("I guess you don't have any guts...");
    }

    /**
     * Displays entire hand.
     *
     * @param player
     */
    public void displayHand(Player player) {
        System.out.println("Dealer has: " + player.getHand());
    }

    /**
     * Displays initial hand, only shows 1 up for the computer
     *
     * @param user
     * @param cpu
     */
    public void displayInitialHands(Player user, Player cpu) {
        for (int i = 0; i < user.getHand().size(); i++) {
            System.out.println("You have: " + user.getHandCard(i));
        }
        for (int j = 1; j < cpu.getHand().size(); j++) {
            System.out.println("Dealer is showing:  " + cpu.getHandCard(0));
        }



    }

    /**
     * Shows cpu's hand.
     * @param cpu
     */
    public void showHands(Player cpu){
        System.out.println(cpu.getHand());
    }


    public void dealHands(Player user, Player cpu) {
        user.addCard(dealer.dealCard());
        cpu.addCard(dealer.dealCard());
        user.addCard(dealer.dealCard());
        cpu.addCard(dealer.dealCard());

    }
////////////////////////////////////////

    /**
     * Starts BlackJack game
     *
     * @param user takes player input to get player name and amount of Money
     */
    public void runGame(Player user) {
        Player cpu = new Player();


        System.out.println("Starting BlackJack... ");


        Boolean continueLoop = true;

        while (continueLoop) {
            System.out.println("Welcome to Ultimate ");
            GameControl.pause(1);

            System.out.println("__________ .__                    __          ____.                 __     \n" +
                    "\\______   \\|  |  _____     ____  |  | __     |    |_____     ____  |  | __ \n" +
                    " |    |  _/|  |  \\__  \\  _/ ___\\ |  |/ /     |    |\\__  \\  _/ ___\\ |  |/ / \n" +
                    " |    |   \\|  |__ / __ \\_\\  \\___ |    <  /\\__|    | / __ \\_\\  \\___ |    <  \n" +
                    " |______  /|____/(____  / \\___  >|__|_ \\ \\________|(____  / \\___  >|__|_ \\ \n" +
                    "        \\/            \\/      \\/      \\/                \\/      \\/      \\/ ");

            GameControl.pause(1);
            /**
             * Welcome message and display of initial credit
             */
            System.out.println(user.getName() + ", you have $" + user.getMoney() + "credit, " +
                    "hope your luckier than the dealer!");
            if (user.getMoney() > 0) {
                takeWager(user);

            }
            GameControl.pause(1);

            dealHands(user, cpu);


            displayInitialHands(user, cpu);

            checkBlackjack(user, cpu);

            GameControl.pause();


            System.out.println("Would you like to HIT(1) or STAY(2)");


            /*
            User switch statement.
             */
            userChoice(user);
            checkUserForBust(user);

            GameControl.pause();

            /**
             * Displays cpu's hand before he hits.
             */

            showHands(cpu);

            /**
             * Checks to see if computer has to hit or not
             */
            GameControl.pause();
            if (calcHandTotal(cpu) < 17) {

                hit(cpu);
                checkUserForBust(cpu);

            }

            displayHand(cpu);

            /**
             * Calculates the game total and selects a winner and gives the user money if they win.
             */

            GameControl.pause(2);
            if (calcHandTotal(user) > calcHandTotal(cpu) && calcHandTotal(user) <= 21) {
                System.out.println("Congratulations you have won!!!");
                user.setMoney(user.getMoney() + (wager * 2));
            } else {
                System.out.println("You lose...");
                System.out.println("  ________    _____      _____   ___________ ________   ____   _________________________ \n" +
                        " /  _____/   /  _  \\    /     \\  \\_   _____/ \\_____  \\  \\   \\ /   /\\_   _____/\\______   \\\n" +
                        "/   \\  ___  /  /_\\  \\  /  \\ /  \\  |    __)_   /   |   \\  \\   Y   /  |    __)_  |       _/\n" +
                        "\\    \\_\\  \\/    |    \\/    Y    \\ |        \\ /    |    \\  \\     /   |        \\ |    |   \\\n" +
                        " \\______  /\\____|__  /\\____|__  //_______  / \\_______  /   \\___/   /_______  / |____|_  /\n" +
                        "        \\/         \\/         \\/         \\/          \\/                    \\/         \\/ ");
            }


        }

    }

    public static void main(String[] args){
        BlackjackEngine bj = new BlackjackEngine();
        Player player = new Player("Sean");
        bj.runGame(player);

    }
}



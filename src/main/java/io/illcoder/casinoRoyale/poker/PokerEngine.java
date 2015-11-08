package io.illcoder.casinoRoyale.poker;


import io.illcoder.casinoRoyale.core.*;

import java.util.*;

/**
 * Created by seth on 9/27/2015.
 * PokerEngine contains the methods and variables to run a heads up limit texas hold'em poker game.
 */
public class PokerEngine {
    Dealer dealer;
    Scanner scan;
    GameControl gc;
    Player user;
    Player cpu;
    Player sb;
    Player bb;
    Player board;
    Player burn;
    int smallBlind;
    int bigBlind;
    int bigBet;
    int pot;
    int action;
    int raiseCount;
    boolean playerFolded;
    boolean continueLoop;
    List<Card> sevenCards;

    /**
     * class constructor
     * @param _user - Player object with name and bankroll info
     */
    public PokerEngine (Player _user) {
        dealer = new Dealer();
        scan = new Scanner(System.in);
        smallBlind = 1;
        bigBlind =2;
        bigBet = 4;
        pot = 0;
        raiseCount = 0;
        playerFolded = false;
        continueLoop = true;
        gc = new GameControl();

        this.user = _user;
        this.cpu = new Player("CPU");
        this.board = new Player("BOARD");
        this.burn = new Player("BURN");

        sb = cpu;
        bb = user;
    }

    /**
     * method to welcome the user when a new game is started and begins the first hand
     */
    public void startGameMessage() {
        System.out.println("Welcome to the Poker Game " + user.getName() + ". You currently have $" +
                user.getMoney() + " in chips.\nStarting first hand...\n");

        gc.pause(1);
        nextHand();
    }

    /**
     * method to give the player an option to play another hand or quit the game
     */
    public void playerContinue() {
        System.out.println("\nWould you like to play another hand " + user.getName() + "? You currently have $" +
                user.getMoney() + " in chips.\n\nEnter 1 to play or 2 to quit.");
        int userChoice = scan.nextInt();

        switch (userChoice) {
            case 1:
                nextHand();
                break;
            case 2:
                continueLoop = false;
                break;
            default:
                nextHand();
                break;
        }
    }

    /**
     * method to clear all player/board card data from the previous hand
     */
    public void clearAllHands() {
        sb.getHand().clear();
        bb.getHand().clear();
        board.getHand().clear();
        burn.getHand().clear();
        playerFolded = false;
    }

    /**
     * method to start a new hand
     */
    public void nextHand() {
        clearAllHands();
        dealer.shuffleDeck();

        // alternate player position each new hand (moves dealer button)
        if (sb.getName().equalsIgnoreCase("CPU")) {
            sb = user;
            bb = cpu;
        } else {
            sb = cpu;
            bb = user;
        }

        // collect blinds and deal hole cards
        pot = smallBlind + bigBlind;
        sb.setMoney(sb.getMoney() - smallBlind);
        bb.setMoney(bb.getMoney() - bigBlind);
        dealHoleCards();

        // run preflop round of action
        preflop();

        // proceed to flop round if neither player folded preflop
        if (playerFolded) {
            determineWinner();
            return;
        } else {
            flop();
        }

        // proceed to turn round if neither player folded on the flop
        if (playerFolded) {
            determineWinner();
            return;
        } else {
            turn();
        }

        // proceed to river if neither player folded on the turn
        if (playerFolded) {
            determineWinner();
        } else {
            river();
            determineWinner();
        }

    }

    /**
     * method to show the user's cards, board cards, and amount of money for each player and what is
     * currently in the pot
     */
    public void updateDisplay() {

        if (user.getHand().size() > 0){
            System.out.println("\nYou have: " + user.getHand());
        }
        if (board.getHand().size() > 0) {
            System.out.println("Board: " + board.getHand());
        }
        System.out.println("Pot: $" + pot + " - " + cpu.getName() + ": $" + cpu.getMoney() +
                " - " + user.getName() + ": $" + user.getMoney());
    }

    /**
     * displays the amount of money for each player and what is currently in the pot
     */
    public void displayPot(){
        System.out.println("Pot: $" + pot + " - " + cpu.getName() + ": $" + cpu.getMoney() +
                " - " + user.getName() + ": $" + user.getMoney());
    }

    public void preflop(){
        raiseCount = 0;
        System.out.println("Dealing hole cards...");
        gc.pause();
        updateDisplay();
        sbActionCompleteBlind(smallBlind, bigBlind);

    }

    public void flop(){
        raiseCount = 0;
        System.out.println("Dealing flop...");
        burn.addCard(dealer.dealCard());
        board.addCard(dealer.dealCard());
        board.addCard(dealer.dealCard());
        board.addCard(dealer.dealCard());
        System.out.println(board.getHand());
        gc.pause();
        updateDisplay();
        bbActionNoBet(bigBlind);
    }

    public void turn(){
        raiseCount = 0;
        System.out.println("Dealing turn...");
        burn.addCard(dealer.dealCard());
        board.addCard(dealer.dealCard());
        System.out.println("     [" + board.getHandCard(3) + "]");
        gc.pause();
        updateDisplay();
        bbActionNoBet(bigBet);
    }

    public void river(){
        raiseCount = 0;
        System.out.println("Dealing river...");
        burn.addCard(dealer.dealCard());
        board.addCard(dealer.dealCard());
        System.out.println("     [" + board.getHandCard(4)+ "]");
        gc.pause();
        updateDisplay();
        bbActionNoBet(bigBet);
    }

    /**
     * display messages for various player actions
     * @param _player - the player whose action is being displayed
     */
    public void checkMessage(Player _player) {
        System.out.println("\n" + _player.getName() + " checks.");
    }
    public void callMessage(Player _player) {
        System.out.println("\n" + _player.getName() + " calls.");
    }
    public void raiseMessage(Player _player) {
        System.out.println("\n" + _player.getName() + " raises.");
    }
    public void foldMessage(Player _player) {
        System.out.println("\n" + _player.getName() + " folds.");
    }
    public void betMessage(Player _player) {
        System.out.println("\n" + _player.getName() + " bets.");
    }

    /**
     * handle small blind's action when facing a bet from the big blind player
     * @param prevBetSize
     * @param betSize
     */
    public void sbActionFacingBet(int prevBetSize, int betSize) {

        // if 4 raises have already been made player cannot raise, only call or fold
        if (raiseCount > 3) {
            if (sb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(2) + 1;
            } else {
                System.out.println("\nEnter 1 to CALL, 2 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    pot = pot + prevBetSize;
                    sb.setMoney(sb.getMoney() - prevBetSize);
                    callMessage(sb);
                    break;
                case 2:
                    sb.getHand().clear();
                    playerFolded = true;
                    foldMessage(sb);

                    break;
                default:
                    System.out.println("Invalid input.\n");
                    sbActionFacingBet(prevBetSize, betSize);
                    break;
                }

        } else {
            if (sb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(3) + 1;

            } else {
                System.out.println("\nEnter 1 to CALL, 2 to RAISE, 3 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    pot = pot + prevBetSize;
                    sb.setMoney(sb.getMoney() - prevBetSize);
                    callMessage(sb);
                    break;
                case 2:
                    raiseCount++;
                    pot = pot + prevBetSize + betSize;
                    sb.setMoney(sb.getMoney() - prevBetSize - betSize);
                    raiseMessage(sb);
                    displayPot();
                    bbActionFacingBet(betSize, betSize);
                    break;
                case 3:
                    sb.getHand().clear();
                    foldMessage(sb);
                    playerFolded = true;

                    break;
                default:
                    System.out.println("Invalid input.\n");
                    sbActionFacingBet(prevBetSize, betSize);
                    break;
            }
        }
    }

    /**
     * handle first preflop action from small blind player when he has the option
     * to fold, complete the big blind, or raise
     * @param prevBetSize
     * @param betSize
     */
    public void sbActionCompleteBlind(int prevBetSize, int betSize) {
        //System.out.println(" ");
        if (raiseCount > 3) {
            if (sb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(2) + 1;
            } else {
                System.out.println("\nEnter 1 to CALL, 2 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    pot = pot + (betSize / 2);
                    sb.setMoney(sb.getMoney() - prevBetSize);
                    callMessage(sb);
                    bbActionAfterComplete(betSize);
                    break;
                case 2:
                    sb.getHand().clear();
                    foldMessage(sb);
                    playerFolded = true;

                    break;
                default:
                    System.out.println("Invalid input.\n");
                    sbActionCompleteBlind(prevBetSize, betSize);
                    break;
            }

        } else {
            if (sb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(3) + 1;
            } else {
                System.out.println("\nEnter 1 to CALL, 2 to RAISE, 3 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    pot = pot + prevBetSize;
                    sb.setMoney(sb.getMoney() - prevBetSize);
                    callMessage(sb);
                    bbActionAfterComplete(betSize);
                    break;
                case 2:
                    raiseCount++;
                    pot = pot + prevBetSize + betSize;
                    sb.setMoney(sb.getMoney() - prevBetSize - betSize);
                    raiseMessage(sb);
                    displayPot();
                    bbActionFacingBet(betSize, betSize);
                    break;
                case 3:
                    sb.getHand().clear();
                    foldMessage(sb);
                    playerFolded = true;

                    break;
                default:
                    System.out.println("Invalid input.\n");
                    sbActionCompleteBlind(prevBetSize, betSize);
                    break;
            }
        }
    }

    /**
     * handle the action when the small blind player is facing a bet
     * @param betSize
     */
    public void sbActionNoBet(int betSize){
        if (sb.getName().equalsIgnoreCase("CPU")) {
            Random rand = new Random();
            action = rand.nextInt(2) + 1;
        } else {
            System.out.println("\nEnter 1 to CHECK, 2 to BET, 3 to FOLD.");
            action = scan.nextInt();
        }
        switch (action) {
            case 1:
                checkMessage(sb);
                break;
            case 2:
                pot = pot + betSize;
                sb.setMoney(sb.getMoney() - betSize);
                betMessage(sb);
                displayPot();
                bbActionFacingBet(betSize, betSize);
                break;
            case 3:
                sb.getHand().clear();
                foldMessage(sb);
                playerFolded = true;

                break;
            default:
                System.out.println("Invalid input.\n");
                sbActionNoBet(betSize);
                break;
        }
    }

    /**
     * handle the action when the big blind player is facing a bet
     * @param prevBetSize
     * @param betSize
     */
    public void bbActionFacingBet(int prevBetSize, int betSize) {
        //System.out.println(" ");
        if (raiseCount > 3) {
            if (bb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(2) + 1;
            } else {
                System.out.println("\nEnter 1 to CALL, 2 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    pot = pot + prevBetSize;
                    bb.setMoney(bb.getMoney() - prevBetSize);
                    callMessage(bb);
                    break;
                case 2:
                    bb.getHand().clear();
                    foldMessage(bb);
                    playerFolded = true;

                    break;
                default:
                    System.out.println("Invalid input.\n");
                    bbActionFacingBet(prevBetSize, betSize);
                    break;
            }

        } else {
            if (bb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(3) + 1;
            } else {
                System.out.println("\nEnter 1 to CALL, 2 to RAISE, 3 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    pot = pot + prevBetSize;
                    bb.setMoney(bb.getMoney() - prevBetSize);
                    callMessage(bb);
                    break;
                case 2:
                    raiseCount++;
                    pot = pot + prevBetSize + betSize;
                    bb.setMoney(bb.getMoney() - prevBetSize - betSize);
                    raiseMessage(bb);
                    displayPot();
                    sbActionFacingBet(betSize, betSize);
                    break;
                case 3:
                    bb.getHand().clear();
                    foldMessage(bb);
                    playerFolded = true;

                    break;
                default:
                    System.out.println("Invalid input.\n");
                    bbActionFacingBet(prevBetSize, betSize);
                    break;
            }
        }
    }

    /**
     * handle big blind's action after the small blind completes the blind preflop
     * @param betSize - the size of a bet in the current round
     */
    public void bbActionAfterComplete(int betSize){
        //System.out.println(" ");
        if (bb.getName().equalsIgnoreCase("CPU")) {
            Random rand = new Random();
            action = rand.nextInt(2) + 1;
        } else {
            System.out.println("\nEnter 1 to CHECK, 2 to RAISE, 3 to FOLD.");
            action = scan.nextInt();
        }
        switch (action) {
            case 1:
                checkMessage(bb);
                break;
            case 2:
                raiseCount++;
                pot = pot + betSize;
                bb.setMoney(bb.getMoney() - betSize);
                raiseMessage(bb);
                displayPot();
                sbActionFacingBet(betSize, betSize);
                break;
            case 3:
                bb.getHand().clear();
                foldMessage(bb);
                playerFolded = true;

                break;
            default:
                System.out.println("Invalid input.\n");
                bbActionAfterComplete(betSize);
                break;
        }
    }

    /**
     * handle the big blind's action when not facing a bet from the small blind player
     * @param betSize
     */
    public void bbActionNoBet(int betSize){
        if (bb.getName().equalsIgnoreCase("CPU")) {
            Random rand = new Random();
            action = rand.nextInt(2) + 1;
        } else {
            System.out.println("\nEnter 1 to CHECK, 2 to BET, 3 to FOLD.");
            action = scan.nextInt();
        }
        switch (action) {
            case 1:
                checkMessage(bb);
                sbActionNoBet(betSize);
                break;
            case 2:
                pot = pot + betSize;
                bb.setMoney(bb.getMoney() - betSize);
                betMessage(bb);
                displayPot();
                sbActionFacingBet(betSize, betSize);
                break;
            case 3:
                bb.getHand().clear();
                foldMessage(bb);
                playerFolded = true;

                break;
            default:
                System.out.println("Invalid input.\n");
                bbActionNoBet(betSize);
                break;
        }
    }

    /**
     * method to determine the winner of the hand and distribute the pot to the winning player
     */
    public void determineWinner(){

        // check if either player has folded and no longer has cards
        if (sb.getHand().size() < 1) {
            bb.setMoney(bb.getMoney() + pot);
            System.out.println(bb.getName() + " wins the $" + pot + " pot.");
            return;
        } else if (bb.getHand().size() < 1) {
            sb.setMoney(sb.getMoney() + pot);
            System.out.println(sb.getName() + " wins the $" + pot + " pot.");
            return;
        }

        // if both players still have cards compare their hand strength to determine the winner
        Hand sbHand = getTopCombo(sb);
        Hand bbHand = getTopCombo(bb);

        System.out.println("\n" + board.getName() + ": " + board.getHand());
        System.out.println(sb.getName() + " has: " + sb.getHand() + " - " + sbHand.getRankName());
        System.out.println(bb.getName() + " has: " + bb.getHand()+ " - " + bbHand.getRankName());

        int winner = sbHand.compare(bbHand);
        switch (winner){
            case -1:
                bb.setMoney(bb.getMoney() + pot);
                System.out.println("\n" + bb.getName() + " wins the $" + pot + " pot.");
                break;
            case 0:
                bb.setMoney(bb.getMoney() + (pot/2));
                sb.setMoney(sb.getMoney() + (pot/2));
                System.out.println("\nThe $" + pot + " pot is split.");
                break;
            case 1:
                sb.setMoney(sb.getMoney() + pot);
                System.out.println("\n" + sb.getName() + " wins the $" + pot + " pot.");
                break;
        }

    }

    /**
     * deals 2 hole cards to each player
     */
    public void dealHoleCards() {
        sb.addCard(dealer.dealCard());
        bb.addCard(dealer.dealCard());
        sb.addCard(dealer.dealCard());
        bb.addCard(dealer.dealCard());
    }

    public void quit() {

    }

    /**
     * runs the starting greeting method and then runs the game loop until the player decides to quit
     */
    public void runGame() {
        startGameMessage();

        while (continueLoop) {
            playerContinue();
        }

    }

    /**
     * method to check all of the 5 card combinations of a players 7 card hand (2 hole cards +
     * 5 board cards) during showdown.
     * @param _player
     * @return a Hand object with highest ranking 5 card combination that is possible
     */
    public Hand getTopCombo(Player _player){
        ArrayList handCombos = new ArrayList<Hand>();
        List<Card> sevenCards = new ArrayList<Card>(board.getHand());

        sevenCards.add(_player.getHandCard(0));
        sevenCards.add(_player.getHandCard(1));

        int hLength = sevenCards.size();

        for(int i = 0; i < hLength - 4; i++)
            for(int j = i+1; j < hLength - 3; j++)
                for(int k = j+1; k < hLength - 2; k++)
                    for(int m = k+1; m < hLength - 1; m++)
                        for(int n = m+1; n < hLength; n++){
                            List<Card> cHand = new ArrayList<Card>();
                            cHand.add(sevenCards.get(i));
                            cHand.add(sevenCards.get(j));
                            cHand.add(sevenCards.get(k));
                            cHand.add(sevenCards.get(m));
                            cHand.add(sevenCards.get(n));
                            Hand oHand = new Hand(cHand);
                            handCombos.add(oHand);
                        }
        Hand topHand = (Hand) handCombos.get(0);

        for (int x = 1; x < handCombos.size(); x++){
            Hand tempHand = (Hand) handCombos.get(x);
            if (tempHand.compare(topHand) == 1) {
                topHand = (Hand) handCombos.get(x);
            }
        }
        return topHand;
    }

    public static void main(String[] args) {
        Player aPlayer = new Player("Seth");
        PokerEngine game = new PokerEngine(aPlayer);
        game.runGame();
    }

}

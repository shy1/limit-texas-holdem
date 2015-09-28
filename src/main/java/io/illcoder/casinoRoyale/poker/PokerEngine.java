package io.illcoder.casinoRoyale.poker;

import com.sun.org.apache.xpath.internal.SourceTree;
import io.illcoder.casinoRoyale.core.*;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
/**
 * Created by seth on 9/27/2015.
 * PokerEngine contains the methods and variables to run a heads up limit holdem poker game.
 */
public class PokerEngine {
    Dealer dealer = new Dealer();
    Scanner scan = new Scanner(System.in);
    Player user;
    Player cpu;
    Player sb;
    Player bb;
    Player board;
    Player burn;
    int smallBlind = 1;
    int bigBlind =2;
    int bigBet = 4;
    int pot = 0;
    int action;
    int raiseCount = 0;

    public PokerEngine (Player _user) {
        this.user = _user;
        this.cpu = new Player("CPU");
        this.board = new Player("BOARD");
        this.burn = new Player("BURN");

        sb = cpu;
        bb = user;


    }
    public void startGameMessage() {
        System.out.println("Welcome to the Poker Game " + user.getName() + ". You currently have $" +
                user.getMoney() + " in chips.\nEnter 1 to play or 2 to quit.");
        int userChoice = scan.nextInt();

        switch (userChoice) {
            case 1:
                nextHand();
                break;
            case 2:
                quit();
                break;
            default:
                nextHand();
                break;
        }
    }

    public void clearAllHands() {
        sb.getHand().clear();
        bb.getHand().clear();
        board.getHand().clear();
        burn.getHand().clear();
    }

    public void nextHand() {
        clearAllHands();

        if (sb.getName().equalsIgnoreCase("CPU")) {
            sb = user;
            bb = cpu;
        } else {
            sb = cpu;
            bb = user;
        }
        pot = smallBlind + bigBlind;
        sb.setMoney(sb.getMoney() - smallBlind);
        bb.setMoney(bb.getMoney() - bigBlind);

        dealer.shuffleDeck();
        dealHoleCards();
        preflop();
    }

    public void updateDisplay() {
        System.out.println("Pot: $" + pot + "\n" + cpu.getName() + ": $" + cpu.getMoney() +
        "\n" + user.getName() + ": $" + user.getMoney());

        if (user.getHand().size() > 0){
            System.out.println("You have: " + user.getHand());
        }
        if (board.getHand().size() > 0) {
            System.out.println("Board: " + board.getHand());
        }
    }

    public void preflop(){
        updateDisplay();
        sbActionPreflopComplete(smallBlind, bigBlind);

    }

    public void checkMessage(Player _player) {
        System.out.println(_player.getName() + " checks.\n");
    }

    public void callMessage(Player _player) {
        System.out.println(_player.getName() + " calls.\n");
    }

    public void raiseMessage(Player _player) {
        System.out.println(_player.getName() + " raises.\n");
    }

    public void foldMessage(Player _player) {
        System.out.println(_player.getName() + " folds.\n");
    }

    /**
     * make separate preflop first sbActionFacingBet method for completing the
     * small blind that gives the bb an option to check or raise after the sb
     * calls. when sb calls any other time the action is over.
     * @param prevBetSize
     * @param betSize
     */
    public void sbActionFacingBet(int prevBetSize, int betSize) {
        System.out.println("\n");
        if (raiseCount > 3) {
            if (sb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(2) + 1;
            } else {
                System.out.println("Enter 1 to CALL, 2 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    pot = pot + prevBetSize;
                    sb.setMoney(sb.getMoney() - prevBetSize);
                    callMessage(sb);
                    updateDisplay();
                    break;
                case 2:
                    sb.getHand().clear();
                    foldMessage(sb);
                    determineWinner();
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
                System.out.println("Enter 1 to CALL, 2 to RAISE, 3 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    pot = pot + prevBetSize;
                    sb.setMoney(sb.getMoney() - prevBetSize);
                    callMessage(sb);
                    updateDisplay();
                    break;
                case 2:
                    raiseCount++;
                    pot = pot + prevBetSize + betSize;
                    sb.setMoney(sb.getMoney() - prevBetSize - betSize);
                    raiseMessage(sb);
                    updateDisplay();
                    bbActionFacingBet(betSize, betSize);
                    break;
                case 3:
                    sb.getHand().clear();
                    foldMessage(sb);
                    determineWinner();
                    break;
                default:
                    System.out.println("Invalid input.\n");
                    sbActionFacingBet(prevBetSize, betSize);
                    break;
            }
        }
    }

    public void sbActionPreflopComplete(int prevBetSize, int betSize) {
        System.out.println("\n");
        if (raiseCount > 3) {
            if (sb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(2) + 1;
            } else {
                System.out.println("Enter 1 to CALL, 2 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    pot = pot + (betSize / 2);
                    sb.setMoney(sb.getMoney() - prevBetSize);
                    callMessage(sb);
                    updateDisplay();
                    break;
                case 2:
                    sb.getHand().clear();
                    foldMessage(sb);
                    determineWinner();
                    break;
                default:
                    System.out.println("Invalid input.\n");
                    sbActionPreflopComplete(prevBetSize, betSize);
                    break;
            }

        } else {
            if (sb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(3) + 1;
            } else {
                System.out.println("Enter 1 to CALL, 2 to RAISE, 3 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    pot = pot + prevBetSize;
                    sb.setMoney(sb.getMoney() - prevBetSize);
                    callMessage(sb);
                    updateDisplay();
                    bbActionNoBet(betSize);
                    break;
                case 2:
                    raiseCount++;
                    pot = pot + prevBetSize + betSize;
                    sb.setMoney(sb.getMoney() - prevBetSize - betSize);
                    raiseMessage(sb);
                    updateDisplay();
                    bbActionFacingBet(betSize, betSize);
                    break;
                case 3:
                    sb.getHand().clear();
                    foldMessage(sb);
                    determineWinner();
                    break;
                default:
                    System.out.println("Invalid input.\n");
                    sbActionPreflopComplete(prevBetSize, betSize);
                    break;
            }
        }
    }

    public void sbActionNoBet(int betSize){

    }

    public void bbActionFacingBet(int prevBetSize, int betSize) {
        System.out.println("\n");
        if (raiseCount > 3) {
            if (bb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(2) + 1;
            } else {
                System.out.println("Enter 1 to CALL, 2 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    pot = pot + prevBetSize;
                    bb.setMoney(bb.getMoney() - prevBetSize);
                    callMessage(bb);
                    updateDisplay();
                    break;
                case 2:
                    bb.getHand().clear();
                    foldMessage(bb);
                    determineWinner();
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
                System.out.println("Enter 1 to CALL, 2 to RAISE, 3 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    pot = pot + prevBetSize;
                    bb.setMoney(bb.getMoney() - prevBetSize);
                    callMessage(bb);
                    updateDisplay();
                    break;
                case 2:
                    raiseCount++;
                    pot = pot + prevBetSize + betSize;
                    bb.setMoney(bb.getMoney() - prevBetSize - betSize);
                    raiseMessage(bb);
                    updateDisplay();
                    sbActionFacingBet(betSize, betSize);
                    break;
                case 3:
                    bb.getHand().clear();
                    foldMessage(bb);
                    determineWinner();
                    break;
                default:
                    System.out.println("Invalid input.\n");
                    bbActionFacingBet(prevBetSize, betSize);
                    break;
            }
        }
    }

    public void bbActionNoBet(int betSize){
        if (bb.getName().equalsIgnoreCase("CPU")) {
            Random rand = new Random();
            action = rand.nextInt(2) + 1;
        } else {
            System.out.println("Enter 1 to CHECK, 2 to RAISE, 3 to FOLD.");
            action = scan.nextInt();
        }
        switch (action) {
            case 1:
                checkMessage(bb);
                updateDisplay();
                break;
            case 2:
                raiseCount++;
                pot = pot + betSize;
                bb.setMoney(bb.getMoney() - betSize);
                raiseMessage(bb);
                updateDisplay();
                sbActionFacingBet(betSize, betSize);
                break;
            case 3:
                sb.getHand().clear();
                foldMessage(bb);
                determineWinner();
                break;
            default:
                System.out.println("Invalid input.\n");
                bbActionNoBet(betSize);
                break;
        }
    }

    public void determineWinner(){
        if (sb.getHand().size() < 1) {
            bb.setMoney(bb.getMoney() + pot);
            System.out.println(bb.getName() + " wins the pot.");
        } else if (bb.getHand().size() < 1) {
            sb.setMoney(sb.getMoney() + pot);
            System.out.println(sb.getName() + " wins the pot.");
        }
    }

    public void dealHoleCards() {
        sb.addCard(dealer.dealCard());
        bb.addCard(dealer.dealCard());
        sb.addCard(dealer.dealCard());
        bb.addCard(dealer.dealCard());
    }

    public void quit() {

    }

    public void runGame() {
        startGameMessage();

    }

    public static void main(String[] args) {
        Player aPlayer = new Player("Seth");
        PokerEngine game = new PokerEngine(aPlayer);
        game.runGame();
    }
}

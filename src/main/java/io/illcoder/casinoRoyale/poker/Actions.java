package io.illcoder.casinoRoyale.poker;

import io.illcoder.casinoRoyale.core.GameControl;
import io.illcoder.casinoRoyale.core.Player;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by syoung on 11.11.15.
 */
public class Actions {
    private PokerEngine game;
    private Scanner scan;


    private int action;









    public Actions(PokerEngine pokerEngine) {
        this.game = pokerEngine;
        this.scan = game.getScan();

    }


    /**
     * handle small blind's action when facing a bet from the big blind player
     * @param prevBetSize
     * @param betSize
     */
    public void sbActionFacingBet(int prevBetSize, int betSize) {

        // if 4 raises have already been made player cannot raise, only call or fold
        if (game.raiseCount > 3) {
            if (game.sb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(2) + 1;
            } else {
                System.out.println("\nEnter 1 to CALL, 2 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    game.pot = game.pot + prevBetSize;
                    game.sb.setMoney(game.sb.getMoney() - prevBetSize);
                    game.callMessage(game.sb);
                    break;
                case 2:
                    game.sb.getHand().clear();
                    game.playerFolded = true;
                    game.foldMessage(game.sb);

                    break;
                default:
                    System.out.println("Invalid input.\n");
                    sbActionFacingBet(prevBetSize, betSize);
                    break;
            }

        } else {
            if (game.sb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(3) + 1;

            } else {
                System.out.println("\nEnter 1 to CALL, 2 to RAISE, 3 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    game.pot = game.pot + prevBetSize;
                    game.sb.setMoney(game.sb.getMoney() - prevBetSize);
                    game.callMessage(game.sb);
                    break;
                case 2:
                    game.raiseCount++;
                    game.pot = game.pot + prevBetSize + betSize;
                    game.sb.setMoney(game.sb.getMoney() - prevBetSize - betSize);
                    game.raiseMessage(game.sb);
                    game.displayPot();
                    bbActionFacingBet(betSize, betSize);
                    break;
                case 3:
                    game.sb.getHand().clear();
                    game.foldMessage(game.sb);
                    game.playerFolded = true;

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
        if (game.raiseCount > 3) {
            if (game.sb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(2) + 1;
            } else {
                System.out.println("\nEnter 1 to CALL, 2 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    game.pot = game.pot + (betSize / 2);
                    game.sb.setMoney(game.sb.getMoney() - prevBetSize);
                    game.callMessage(game.sb);
                    bbActionAfterComplete(betSize);
                    break;
                case 2:
                    game.sb.getHand().clear();
                    game.foldMessage(game.sb);
                    game.playerFolded = true;

                    break;
                default:
                    System.out.println("Invalid input.\n");
                    sbActionCompleteBlind(prevBetSize, betSize);
                    break;
            }

        } else {
            if (game.sb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(3) + 1;
            } else {
                System.out.println("\nEnter 1 to CALL, 2 to RAISE, 3 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    game.pot = game.pot + prevBetSize;
                    game.sb.setMoney(game.sb.getMoney() - prevBetSize);
                    game.callMessage(game.sb);
                    bbActionAfterComplete(betSize);
                    break;
                case 2:
                    game.raiseCount++;
                    game.pot = game.pot + prevBetSize + betSize;
                    game.sb.setMoney(game.sb.getMoney() - prevBetSize - betSize);
                    game.raiseMessage(game.sb);
                    game.displayPot();
                    bbActionFacingBet(betSize, betSize);
                    break;
                case 3:
                    game.sb.getHand().clear();
                    game.foldMessage(game.sb);
                    game.playerFolded = true;

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
        if (game.sb.getName().equalsIgnoreCase("CPU")) {
            Random rand = new Random();
            action = rand.nextInt(2) + 1;
        } else {
            System.out.println("\nEnter 1 to CHECK, 2 to BET, 3 to FOLD.");
            action = scan.nextInt();
        }
        switch (action) {
            case 1:
                game.checkMessage(game.sb);
                break;
            case 2:
                game.pot = game.pot + betSize;
                game.sb.setMoney(game.sb.getMoney() - betSize);
                game.betMessage(game.sb);
                game.displayPot();
                bbActionFacingBet(betSize, betSize);
                break;
            case 3:
                game.sb.getHand().clear();
                game.foldMessage(game.sb);
                game.playerFolded = true;

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
        if (game.raiseCount > 3) {
            if (game.bb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(2) + 1;
            } else {
                System.out.println("\nEnter 1 to CALL, 2 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    game.pot = game.pot + prevBetSize;
                    game.bb.setMoney(game.bb.getMoney() - prevBetSize);
                    game.callMessage(game.bb);
                    break;
                case 2:
                    game.bb.getHand().clear();
                    game.foldMessage(game.bb);
                    game.playerFolded = true;

                    break;
                default:
                    System.out.println("Invalid input.\n");
                    bbActionFacingBet(prevBetSize, betSize);
                    break;
            }

        } else {
            if (game.bb.getName().equalsIgnoreCase("CPU")) {
                Random rand = new Random();
                action = rand.nextInt(3) + 1;
            } else {
                System.out.println("\nEnter 1 to CALL, 2 to RAISE, 3 to FOLD.");
                action = scan.nextInt();
            }
            switch (action) {
                case 1:
                    game.pot = game.pot + prevBetSize;
                    game.bb.setMoney(game.bb.getMoney() - prevBetSize);
                    game.callMessage(game.bb);
                    break;
                case 2:
                    game.raiseCount++;
                    game.pot = game.pot + prevBetSize + betSize;
                    game.bb.setMoney(game.bb.getMoney() - prevBetSize - betSize);
                    game.raiseMessage(game.bb);
                    game.displayPot();
                    sbActionFacingBet(betSize, betSize);
                    break;
                case 3:
                    game.bb.getHand().clear();
                    game.foldMessage(game.bb);
                    game.playerFolded = true;

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
        if (game.bb.getName().equalsIgnoreCase("CPU")) {
            Random rand = new Random();
            action = rand.nextInt(2) + 1;
        } else {
            System.out.println("\nEnter 1 to CHECK, 2 to RAISE, 3 to FOLD.");
            action = scan.nextInt();
        }
        switch (action) {
            case 1:
                game.checkMessage(game.bb);
                break;
            case 2:
                game.raiseCount++;
                game.pot = game.pot + betSize;
                game.bb.setMoney(game.bb.getMoney() - betSize);
                game.raiseMessage(game.bb);
                game.displayPot();
                sbActionFacingBet(betSize, betSize);
                break;
            case 3:
                game.bb.getHand().clear();
                game.foldMessage(game.bb);
                game.playerFolded = true;

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
        if (game.bb.getName().equalsIgnoreCase("CPU")) {
            Random rand = new Random();
            action = rand.nextInt(2) + 1;
        } else {
            System.out.println("\nEnter 1 to CHECK, 2 to BET, 3 to FOLD.");
            action = scan.nextInt();
        }
        switch (action) {
            case 1:
                game.checkMessage(game.bb);
                sbActionNoBet(betSize);
                break;
            case 2:
                game.pot = game.pot + betSize;
                game.bb.setMoney(game.bb.getMoney() - betSize);
                game.betMessage(game.bb);
                game.displayPot();
                sbActionFacingBet(betSize, betSize);
                break;
            case 3:
                game.bb.getHand().clear();
                game.foldMessage(game.bb);
                game.playerFolded = true;

                break;
            default:
                System.out.println("Invalid input.\n");
                bbActionNoBet(betSize);
                break;
        }
    }

}

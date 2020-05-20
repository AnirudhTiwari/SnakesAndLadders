package com.company;

import com.company.model.*;

import java.util.List;

public class SnakesAndLadders {
    private final List<Player> playerList;
    private final Dice dice;
    private final Board board;
    private final int startPosition;
    private final int endPosition;

    public SnakesAndLadders(List<Player> playerList, Dice dice, Board board, int startPosition, int endPosition) {
        this.playerList = playerList;
        this.dice = dice;
        this.board = board;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    /**
     * Main program to be invoked to start the Snakes and Ladders Game.
     * The players will take turns to roll a dice and based on the move, they'll change their position.
     * For any given move, their position can change if they encounter head of a snake or start of a ladder.
     * The first one to reach the endPosition wins.
     */
    public void play() {
        System.out.println();
        System.out.println("STARTING SNAKES AND LADDERS");
        boolean flag = true;

        while (flag) {
            for (Player player : playerList) {
                final int number = dice.rollDice();

                move(player, number);

                if (player.getPosition() == endPosition) {
                    final String winMessage = String.format("%s WON!!", player.getName());
                    System.out.println(winMessage);
                    flag = false;
                    break;
                }
            }
        }
        System.out.println("GAME OVER");
    }


    private void move(final Player player, final int number) {
        final int startPosition = player.getPosition();
        int endPosition = startPosition + number;

        if (endPosition > this.endPosition) {
            final String notMoveMsg = String.format("%s rolled %d but can't move", player.getName(), number);
            System.out.println(notMoveMsg);
            return;
        }

        final String diceRollMsg = String.format("%s rolled %d and moved from %d to %d", player.getName(), number,
                startPosition, endPosition);

        System.out.println(diceRollMsg);

        while (player.getPosition() != endPosition) {
            player.setPosition(endPosition);

            //If snake at given end position, then endPosition1 will be different. Player would need to move.
            final int endPosition1 = checkIfAtSnakesHead(endPosition);

            if (endPosition1 != endPosition) {
                endPosition = endPosition1;
                continue;
            }

            //If ladder at given end position, then endPosition2 will be different. Player would need to move.
            final int endPosition2 = checkIfAtLaddersStart(endPosition);

            if (endPosition2 != endPosition) {
                endPosition = endPosition2;
            }
        }
    }

    private int checkIfAtLaddersStart(final int startPosition) {
        for (Ladder ladder : this.board.getLadders()) {
            if (ladder.getStart() == startPosition) {
                final String msg = String.format("======Player climbed ladder from %d to %d======", ladder.getStart(),
                        ladder.getEnd());
                System.out.println(msg);
                return ladder.getEnd();
            }
        }
        return startPosition;
    }

    private int checkIfAtSnakesHead(final int startPosition) {
        for (Snake snake : this.board.getSnakes()) {
            if (snake.getHead() == startPosition) {
                final String msg = String.format("======Oops! Snake bit player at %d and will go down to %d======",
                        snake.getHead(), snake.getTail());
                System.out.println(msg);
                return snake.getTail();
            }
        }
        return startPosition;
    }
}

package com.company.model;

import java.util.List;

public class Board {
    public List<Snake> getSnakes() {
        return snakes;
    }

    public List<Ladder> getLadders() {
        return ladders;
    }

    private final List<Snake> snakes;
    private final List<Ladder> ladders;

    public Board(List<Snake> snakes, List<Ladder> ladders) {
        this.snakes = snakes;
        this.ladders = ladders;
    }
}

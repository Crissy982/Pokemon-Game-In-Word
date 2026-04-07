package BattleEngine;

import BattleEngine.Action.Action;
import Entity.Move.Move;
import Entity.Pokemon;

import java.util.PriorityQueue;
import java.util.Random;

public class Battle {
    private PriorityQueue<Action> actionQueue;
    private Side playerSide;
    private Side opponentSide;
    private Field field;
    private Weather weather;
    private static final String INDEX_HIT_RATE = "HIT_RATE";
    private static final String INDEX_EVASION_RATE = "EVASION_RATE";
    private static final Random RANDOM = new Random(561234557L);
    public Battle(Side playerSide,
                  Side opponentSide,
                  Field field,
                  Weather weather) {
        this.playerSide = playerSide;
        this.opponentSide = opponentSide;
        this.field = field;
        this.weather = weather;
        this.actionQueue = new PriorityQueue<Action>(/**
                new Comparator<Action>() {
                    @Override
                    public int compare(Action action1, Action action2) {

                        int priority1 = action1.getActMove().data.priority();
                        int priority2 = action2.getActMove().data.priority();
                        if (priority1 == priority2) {
                            int speed1 = action1.getActPokemon().getSpeed();
                            int speed2 = action2.getActPokemon().getSpeed();
                            return speed2 - speed1;
                        }
                        return priority2 - priority1;

                    }
                }
                **/
        );
    }

    private boolean isHit(Pokemon actor, Move move, Pokemon target) {
        if (move.data.accuracy() == null)
            return true;
        int modifierPokemon = actor.getStatChanges().get(INDEX_HIT_RATE).modifier()
                - target.getStatChanges().get(INDEX_EVASION_RATE).modifier();
        modifierPokemon = Math.max(-6, Math.min(6, modifierPokemon));
        double modifier;
        if (modifierPokemon < 0) {
            modifier = 3.0 / (3 - modifierPokemon);
        }
        else {
            modifier = (3.0 + modifierPokemon) / 3.0;
        }
        int hitNum = Math.min((int)( move.data.accuracy()
                * modifier), 100);
        return RANDOM.nextInt(100) < hitNum;
    }
}

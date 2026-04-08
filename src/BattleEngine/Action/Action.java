package BattleEngine.Action;

import BattleEngine.Side;
import Entity.Pokemon;

public abstract class Action {
    protected static final int ESCAPE_PRIORITY = 10;
    protected static final int SWITCH_PRIORITY = 9;
    protected static final int USE_ITEM_PRIORITY = 8;
    protected final Pokemon actPokemon;
    protected final Side actSide;
    protected int priority = 0;
    protected int speed = 0;

    public Action (Side actSide, Pokemon actPokemon) {
        this.actSide = actSide;
        this.actPokemon = actPokemon;
        this.speed = actPokemon.getSpeed();
    }

    public Pokemon getActPokemon() {return actPokemon;}

    public Side getActSide() {
        return actSide;
    }

    public int getPriority() {
        return priority;
    }

    public int getSpeed() {
        return speed;
    }

    public abstract void act();
}

package BattleEngine.Action;

import BattleEngine.Side;
import Entity.Player;
import Entity.Pokemon;

public abstract class Action {
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

    public abstract void act();
}

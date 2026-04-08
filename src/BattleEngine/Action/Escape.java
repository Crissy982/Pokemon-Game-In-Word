package BattleEngine.Action;

import BattleEngine.Side;
import Entity.Player;
import Entity.Pokemon;

public class Escape extends Action{

    private final Player actPlayer;
    public Escape(Side actSide, Pokemon actPokemon) {
        super(actSide, actPokemon);
        this.actPlayer = actPokemon.getOwner();
        this.priority = ESCAPE_PRIORITY;
    }

    @Override
    public void act() {
        actSide.escape();
    }
}

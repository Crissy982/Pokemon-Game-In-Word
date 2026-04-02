package BattleEngine;

import Entity.Item;
import Entity.Move.Move;
import Entity.Pokemon;

public class Action {
    private final Pokemon actPokemon;
    private final Move actMove;
    private final Pokemon targetPokemon;

    public Action(Pokemon actPokemon, Move actMove){
        this.actPokemon = actPokemon;
        this.actMove = actMove;
        targetPokemon = null;
    }

    public Action(Pokemon actPokemon, Move actMove, Pokemon targetPokemon) {
        this.actPokemon = actPokemon;
        this.actMove = actMove;
        this.targetPokemon = targetPokemon;
    }

    public Pokemon getActPokemon(){return actPokemon;}

    public Move getActMove(){return actMove;}

    public Pokemon getTargetPokemon(){return targetPokemon;}
}

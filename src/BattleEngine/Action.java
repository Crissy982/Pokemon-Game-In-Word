package BattleEngine;

import Entity.Move.Move;
import Entity.Pokemon;

public class Action {
    private final Pokemon actPokemon;
    private final Move actMove;

    public Action(Pokemon actPokemon, Move actMove){
        this.actPokemon = actPokemon;
        this.actMove = actMove;
    }

    public Pokemon getActPokemon(){return actPokemon;}

    public Move getActMove(){return actMove;}
}

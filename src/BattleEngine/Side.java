package BattleEngine;

import BattleEngine.Action.Escape;
import Entity.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class Side {
    private List<Pokemon> activePokemons;
    private boolean canEsacpe = true;
    private boolean doEscaped = false;

    public Side (List<Pokemon> pokemons) {
        activePokemons = new ArrayList<>(pokemons);
    }

    public List<Pokemon> getActivePokemons(){
        return new ArrayList<>(activePokemons);
    }

    public boolean escape() {
        if (canEsacpe) {
            doEscaped = true;
            System.out.println("Got away safely!");
        }
        else {
            System.out.println("Couldn't escape!");
        }
        return doEscaped;
    }
}

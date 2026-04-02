package BattleEngine;

import Entity.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class Side {
    private List<Pokemon> activePokemons;

    public Side (List<Pokemon> pokemons) {
        activePokemons = new ArrayList<>(pokemons);
    }

    public List<Pokemon> getActivePokemons(){
        return new ArrayList<>(activePokemons);
    }
}

package BattleEngine;

import BattleEngine.Action.Escape;
import Entity.Player;
import Entity.Pokemon;

import java.util.*;

public class Side {
    private List<Pokemon> activePokemons;
    private boolean canEsacpe = true;
    private boolean doEscaped = false;
    public final int randomTier;

    public Side (List<Pokemon> pokemons) {
        activePokemons = new ArrayList<>(pokemons);
        randomTier = new Random().nextInt(500);
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

    public boolean doEscaped() {
        return doEscaped;
    }

    public boolean areAllFainted() {
        Set<Player> playersChecked = new HashSet<>();
        for (Pokemon pokemon : activePokemons) {
            if (!pokemon.isFainted())
                return false;
            playersChecked.add(pokemon.getOwner());
        }
        for (Player currPlayer : playersChecked) {
            for (Pokemon pokemon : currPlayer.getTeam()) {
                if (!pokemon.isFainted())
                    return false;
            }
        }
        return true;
    }
}

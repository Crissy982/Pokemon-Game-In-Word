package BattleEngine.Action;

import BattleEngine.Side;
import Entity.Player;
import Entity.Pokemon;

public class Switch extends Action {
    private final Player actPlayer;
    private static final int SWITCH_PRIORITY = 6;
    private final int switchID;
    public Switch(Side actSide, Player actPlayer, Pokemon activePokemon, int swtichID) {
        super(actSide, activePokemon);
        this.actPlayer = actPlayer;
        this.switchID = swtichID;
        this.priority = SWITCH_PRIORITY;
    }

    @Override
    public void act () {
        actPlayer.switchPokemon(switchID);
        actSide.getActivePokemons().remove(actPokemon);
        actSide.getActivePokemons().add(actPlayer.getActivePokemon());
    }

    public Player getActPlayer() {
        return actPlayer;
    }
}

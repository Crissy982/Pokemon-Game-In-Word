package BattleEngine.Action;

import BattleEngine.Side;
import Entity.Item;
import Entity.Player;
import Entity.Pokemon;

public class UseItem extends Action {
    private final Item actItem;
    private final Player actPlayer;
    public UseItem(Item actItem,
                   Pokemon actPokemon,
                   Side actSide) {
        super(actSide, actPokemon);
        this.actItem = actItem;
        this.actPlayer = actPokemon.getOwner();
        this.priority = USE_ITEM_PRIORITY;
    }

    @Override
    public void act() {
        actPlayer.useItem(actItem,actPokemon);
    }
}

package Entity;

import BattleEngine.*;

import BattleEngine.Action.*;
import Entity.Move.Move;

import java.util.*;

public abstract class Player {
    private String name;
    private String gender;
    private int age;
    private final int playerID;
    private List<Pokemon> team;
    private Pokemon activePokemon;
    private Bag bag;
    private final Scanner scanner = new Scanner(System.in);

    protected Player(String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        bag = new Bag();
        team = new ArrayList<Pokemon>();

        // TODO: Initialize PlayerID Data Field
        this.playerID = 0;
    }

    public void switchPokemon(int teamIndex) {
        Pokemon pokemonTemp = activePokemon;
        activePokemon = team.get(teamIndex);
        team.add(teamIndex, pokemonTemp);
    }

    public void sendPokemon(Pokemon opponent, Field field, Weather weather) {
        /*Side playerSide = new Side(getActivePokemon(),);
        Battle newBattle = new Battle(activePokemon, opponent, field, weather);*/
    }

    // Getter Methods
    public Pokemon getActivePokemon() {
        return activePokemon;
    }
    public List<Pokemon> getTeam() {return team;}

    public abstract void addPokemon(Pokemon pokemon);

    public void useItem(Item item, Pokemon pokemon) {}

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Player))
            return false;
        if (this.playerID == ((Player) o).playerID)
            return true;
        return false;
    }

    public Action chooseAction(Map<Integer, ActionType> actionMap, Battle battle) {
        int choice = scanner.nextInt();
        switch (actionMap.get(choice)) {
            case UseMove:
                int choiceMove;
                Move move ;
                // Check if the move is null
                do {
                    choiceMove = scanner.nextInt();
                    move = activePokemon.getmoveMapNow().get(choiceMove);
                } while (move == null);
                //TODO: Judge the move target type
                int choiceTarget;
                Pokemon targetPokemon;
                do {
                    choiceTarget = scanner.nextInt();
                } while (choiceTarget <= 0 || choiceTarget - 1 >= battle.getOpponentSide().getActivePokemons().size());
                targetPokemon = battle.getOpponentSide()
                        .getActivePokemons()
                        .get(choiceTarget - 1);

                Side currentSide = battle.getPlayerSide()
                        .getActivePokemons()
                        .contains(activePokemon)
                        ? battle.getPlayerSide() : battle.getOpponentSide();

                return new UseMove(currentSide,activePokemon,move,targetPokemon);

            case Switch:
                int choiceSwitch;
                do {
                    choiceSwitch = scanner.nextInt();
                } while (choiceSwitch < 0 || choiceSwitch > team.size() - 1);
                switchPokemon(choiceSwitch);
                currentSide = battle.getPlayerSide()
                        .getActivePokemons()
                        .contains(activePokemon)
                        ? battle.getPlayerSide() : battle.getOpponentSide();

                return new Switch(currentSide, activePokemon, choiceSwitch);

            case UseItem:
                int choiceItem;
                Item itemUse;
                currentSide = battle.getPlayerSide()
                        .getActivePokemons()
                        .contains(activePokemon)
                        ? battle.getPlayerSide() : battle.getOpponentSide();
                do {
                    choiceItem = scanner.nextInt();
                } while (choiceItem < 0 || choiceItem > bag.getCountItem() - 1);
                itemUse = bag.getItem(choiceItem);
                return new UseItem(itemUse, activePokemon, currentSide);

            case Escape:
                // TODO: Refactor Side and Pokemon in battles
                if(battle.getPlayerSide().getActivePokemons().contains(activePokemon)){
                    return new Escape(battle.getPlayerSide(), activePokemon);
                } else {
                    return new Escape(battle.getOpponentSide(), activePokemon);
                }

            default:
                return chooseAction(actionMap, battle);
        }
    }
}

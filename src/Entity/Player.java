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
        activePokemon = team.get(teamIndex);
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
                int choiceMove = scanner.nextInt();
                Move move = activePokemon.getmoveMapNow().get(choiceMove);
                //TODO: Judge the move target type
                int choiceTarget = scanner.nextInt();
                Pokemon targetPokemon = battle.getOpponentSide()
                        .getActivePokemons()
                        .get(choiceTarget - 1);
                Side currentSide = battle.getPlayerSide()
                        .getActivePokemons()
                        .contains(activePokemon)
                        ? battle.getPlayerSide() : battle.getOpponentSide();
                return new UseMove(currentSide,activePokemon,move,targetPokemon);
        }
        return null;
    }
}

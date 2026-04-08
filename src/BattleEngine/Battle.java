package BattleEngine;

public class Battle {
    private final Side playerSide;
    private final Side opponentSide;
    public Battle(Side playerSide,
                  Side opponentSide) {
        this.playerSide = playerSide;
        this.opponentSide = opponentSide;
    }

    public Side getPlayerSide() {
        return playerSide;
    }

    public Side getOpponentSide() {
        return opponentSide;
    }
}

package uk.ac.mmu.game.applicationcode.domainmodel.rules;

import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;

public class ExactEndRule implements EndRule {
    @Override
    public boolean canWin(Player player, int diceRoll) {
        return player.getDistanceToEnd() == diceRoll;
    }

    @Override
    public boolean processMove(Player player, int diceRoll) {
        if (player.wouldOvershootEnd(diceRoll)) {
            player.incrementTurnCountWithoutMoving();
            return false;
        }
        player.move(diceRoll);
        return true;
    }
}
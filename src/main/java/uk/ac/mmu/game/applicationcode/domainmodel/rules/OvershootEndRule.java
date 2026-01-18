package uk.ac.mmu.game.applicationcode.domainmodel.rules;

import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;

public class OvershootEndRule implements EndRule {
    @Override
    public boolean canWin(Player player, int diceRoll) {
        return player.wouldReachEnd(diceRoll);
    }

    @Override
    public boolean processMove(Player player, int diceRoll) {
        player.move(diceRoll);
        return true;
    }
}
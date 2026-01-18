package uk.ac.mmu.game.applicationcode.domainmodel.rules;

import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;

public interface EndRule {
    boolean canWin(Player player, int diceRoll);

    boolean processMove(Player player, int diceRoll);
}
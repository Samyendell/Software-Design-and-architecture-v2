package uk.ac.mmu.game.applicationcode.domainmodel.rules;

import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;

/**
 * Strategy Pattern: Interface for end position rules.
 * Open/Closed Principle: New rules can be added without modifying existing code.
 */
public interface EndRule {
  boolean canWin(Player player, int diceRoll);
  boolean processMove(Player player, int diceRoll);
}
package uk.ac.mmu.game.applicationcode.domainmodel.factory;

import uk.ac.mmu.game.applicationcode.domainmodel.Game;
import uk.ac.mmu.game.applicationcode.domainmodel.GameConfiguration;

public interface GameFactory {
  Game createGame();
  GameConfiguration configuration();
}

package uk.ac.mmu.game.applicationcode.ports.driving.play;

import uk.ac.mmu.game.applicationcode.domainmodel.dice.DiceRoller;
import uk.ac.mmu.game.applicationcode.domainmodel.factory.GameFactory;

public interface Provided {
  String play(String label, GameFactory factory, DiceRoller dice);
}

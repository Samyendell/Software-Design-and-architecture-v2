package uk.ac.mmu.game.applicationcode.domainmodel.factory;

import uk.ac.mmu.game.applicationcode.domainmodel.Game;

// Factory Method: createGame() is fixed, delegates creation to factoryMethod()
public abstract class AbstractGameFactory implements GameFactory {

  @Override
  public final Game createGame() {
    return factoryMethod();
  }

  protected abstract Game factoryMethod();
}

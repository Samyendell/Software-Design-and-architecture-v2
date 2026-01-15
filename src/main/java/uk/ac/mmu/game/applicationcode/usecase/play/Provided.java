package uk.ac.mmu.game.applicationcode.usecase.play;

/**
 * Port: What the Play use case provides to the outside world.
 * This is the interface that infrastructure will call.
 */
public interface Provided {
  PlayResponse execute(PlayRequest request);
}
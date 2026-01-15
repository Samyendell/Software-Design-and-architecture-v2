package uk.ac.mmu.game.applicationcode.domainmodel.rules;

public final class GameRules {
  private final EndRule endRule;
  private final HitRule hitRule;

  public GameRules(EndRule endRule, HitRule hitRule) {
    this.endRule = endRule;
    this.hitRule = hitRule;
  }

  public EndRule getEndRule() {
    return endRule;
  }

  public HitRule getHitRule() {
    return hitRule;
  }
}

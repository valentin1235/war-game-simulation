package wargame;

public interface ICollisionEventListener {
    public AttackIntent bumpedAndGetAttackIntentOrNull(Unit unit);
}

package academy.pocu.comp2500.assignment3;

public interface ICollisionEventListener {
    public AttackIntent bumpedAndGetAttackIntentOrNull(Unit unit);
}

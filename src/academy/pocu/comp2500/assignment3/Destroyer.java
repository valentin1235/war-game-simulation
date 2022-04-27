package academy.pocu.comp2500.assignment3;

public final class Destroyer extends Unit {
    public Destroyer(IntVector2D position) {
        super(position, 'D', UnitType.AIR, 1000000000, 1000000000, 1000000000, 1000000000, true, true, position);
    }

    @Override
    public void onSpawn() {
    }

    @Override
    public AttackIntent attack() {
        return new AttackIntent(super.attackingPoint, this);
    }

    @Override
    public void onAttacked(int damage) {
        this.hp = Math.max(0, this.hp - 1);
    }
}

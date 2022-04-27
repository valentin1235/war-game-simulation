package academy.pocu.comp2500.assignment3;

public class Mine extends Unit implements ICollisionEventListener {
    protected int onTouchBombCnt;

    public Mine(IntVector2D position, int onTouchBombCnt) {
        this(position, onTouchBombCnt, 'N', 0, 0, 10);
    }

    @Override
    public void onSpawn() {
        this.simulationManager.registerCollisionEventListener(this);
    }

    protected Mine(IntVector2D position, int onTouchBombCnt, char symbol, int vision, int aoe, int ap) {
        super(position, symbol, UnitType.LAND, vision, aoe, ap, 1, false, true, position);
        this.onTouchBombCnt = onTouchBombCnt;
    }

    @Override
    public AttackIntent bumpedAndGetAttackIntentOrNull(Unit unit) {
        AttackIntent attackIntent = null;
        if (!this.position.equals(unit.getPosition()) || unit.getType() != UnitType.LAND) {
            return null;
        }
        if (this.onTouchBombCnt >= 1) {
            this.onTouchBombCnt--;
        }
        if (this.onTouchBombCnt == 0) {
            attackIntent = new AttackIntent(unit.getPosition(), this);
            this.hp = 0;
        }

        return attackIntent;
    }
}

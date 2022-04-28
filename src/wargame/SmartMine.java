package wargame;

import java.util.ArrayList;

public final class SmartMine extends Mine implements IThinkable {
    private final int sensibleBombCnt;

    public SmartMine(IntVector2D position, int onTouchBombCnt, int sensibleBombCnt) {
        super(position, onTouchBombCnt, 'A', 1, 1, 15);
        this.sensibleBombCnt = sensibleBombCnt;
    }

    @Override
    public void onSpawn() {
        this.simulationManager.registerThinkable(this);
        this.simulationManager.registerCollisionEventListener(this);
    }

    @Override
    public AttackIntent attack() {
        if (this.attackingPoint == null) {
            return null;
        }
        return new AttackIntent(super.attackingPoint, this);
    }

    @Override
    public IntVector2D findAttackingPointOrNull(ArrayList<Unit> units) {
        if (units.size() >= this.sensibleBombCnt) {
            for (Unit unit : units) {
                System.out.println(String.format("%s sensed", unit.getSymbol()));
            }

            this.hp = 0;
            return this.position;
        } else {
            this.attackingPoint = null;
            return null;
        }
    }

    @Override
    public boolean isAttackable(Unit unit) {
        return this.isVisible(unit);
    }

    @Override
    public boolean isVisible(Unit unit) {
        if (unit == this || unit.getType() != UnitType.LAND || unit.getSymbol() == 'N' || unit.getSymbol() == 'A') {
            return false;
        }
        if (unit.getPosition().getX() == this.position.getX() && unit.getPosition().getY() == this.position.getY()) {
            return true;
        } else if (unit.getPosition().getX() == this.position.getX()
                && unit.getPosition().getY() == this.position.getY() - 1) {
            return true;
        } else if (unit.getPosition().getX() == this.position.getX() + 1
                && unit.getPosition().getY() == this.position.getY() - 1) {
            return true;
        } else if (unit.getPosition().getX() == this.position.getX() + 1
                && unit.getPosition().getY() == this.position.getY()) {
            return true;
        } else if (unit.getPosition().getX() == this.position.getX() + 1
                && unit.getPosition().getY() == this.position.getY() + 1) {
            return true;
        } else if (unit.getPosition().getX() == this.position.getX()
                && unit.getPosition().getY() == this.position.getY() + 1) {
            return true;
        } else if (unit.getPosition().getX() == this.position.getX() - 1
                && unit.getPosition().getY() == this.position.getY() + 1) {
            return true;
        } else if (unit.getPosition().getX() == this.position.getX() - 1
                && unit.getPosition().getY() == this.position.getY()) {
            return true;
        } else if (unit.getPosition().getX() == this.position.getX() - 1
                && unit.getPosition().getY() == this.position.getY() - 1) {
            return true;
        }
        return false;
    }
}

package wargame;

import java.util.ArrayList;

public class AttackIntent {
    private final IntVector2D attackingPoint;
    private final Unit attackFrom;

    AttackIntent(IntVector2D attackingPoint, Unit attackFrom) {
        this.attackingPoint = attackingPoint;
        this.attackFrom = attackFrom;
    }

    private int getDamage(int ap, int distance, int aoe) {
        double divided = (double) distance / (aoe + 1);
        double subtracted = (double) 1 - divided;
        double damage = (double) ap * subtracted;

        return (int) damage;
    }

    private void logger(String funcName, String type, char fromSymbol, int fromX, int fromY, char toSymbol, int toX,
            int toY, int damage) {
        System.out.println(String.format("* AttackIndent.%s / %s attack from (%s)x%sy%s to (%s)x%sy%s (d)%s", funcName,
                type, fromSymbol, fromX, fromY, toSymbol, toX, toY, damage));
    }

    private void loggingSeperator() {
        System.out.println("------------------------------");
    }

    private void splashAttack(Unit object, IntVector2D attackingPoint) {
        if (object == this.attackFrom) {
            return;
        }

        IntVector2D objPosition = object.getPosition();
        final int splashDamage = this.getDamage(attackFrom.ap, 1, attackFrom.getAoe());
        // 타겟 포인트
        if (attackingPoint.equals(object.getPosition()) && this.isAttackable(object)) {
            this.directAttack(object);
        }

        for (int x = -this.attackFrom.getAoe(); x <= this.attackFrom.getAoe(); x++) {
            for (int y = -this.attackFrom.getAoe(); y <= this.attackFrom.getAoe(); y++) {
                if (!(x == 0 && y == 0) && attackingPoint.getX() + x == objPosition.getX()
                        && attackingPoint.getY() + y == objPosition.getY() && this.isAttackable(object)) {
                    logger("splashAttack", "splash", this.attackFrom.getSymbol(), this.attackFrom.getPosition().getX(),
                            this.attackFrom.getPosition().getY(), object.getSymbol(), object.getPosition().getX(),
                            object.getPosition().getY(), splashDamage);
                    object.onAttacked(splashDamage);
                }
            }
        }
    }

    private void directAttack(Unit object) {
        if (object == this.attackFrom || !this.isAttackable(object)) {
            return;
        }
        final int directDamage = this.getDamage(attackFrom.ap, 0, attackFrom.getAoe());
        logger("directAttack", "direct", this.attackFrom.getSymbol(), this.attackFrom.getPosition().getX(),
                this.attackFrom.getPosition().getY(), object.getSymbol(), object.getPosition().getX(),
                object.getPosition().getY(), directDamage);
        object.onAttacked(directDamage);
    }

    public void realize(ArrayList<Unit> units) {
        for (Unit object : units) {
            if (object != attackFrom && attackFrom.getAoe() == 0 && attackingPoint.equals(object.getPosition())
                    && this.isAttackable(object)) {
                this.directAttack(object);
            }

            if (object != attackFrom && attackFrom.getAoe() == 1 && this.isAttackable(object)) {
                this.splashAttack(object, attackingPoint);
                loggingSeperator();
            }
        }
    }

    private boolean isAttackable(Unit object) {
        switch (this.attackFrom.getSymbol()) {
            case 'T':
            case 'N':
            case 'A':
                if (object.getType() == UnitType.AIR) {
                    return false;
                }
                break;
            case 'U':
                if (object.getType() == UnitType.LAND) {
                    return false;
                }
        }
        return true;
    }
}

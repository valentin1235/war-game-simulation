package wargame;

import java.util.ArrayList;
import java.util.Comparator;

public final class Turret extends Unit implements IThinkable {
    public Turret(IntVector2D position) {
        super(position, 'U', UnitType.LAND, 2, 0, 7, 99, true, false, position);
    }

    @Override
    public void onSpawn() {
        this.simulationManager.registerThinkable(this);
    }

    @Override
    public AttackIntent attack() {
        return new AttackIntent(super.attackingPoint, this);
    }

    @Override
    public IntVector2D findAttackingPointOrNull(ArrayList<Unit> units) {
        ArrayList<Unit> targets = new ArrayList<>();

        // 1. 가장 약한 유닛(HP가 가장 낮은 유닛)이 있는 타일을 공격
        units.sort(Comparator.comparing(Unit::getHp));
        targets.add(units.get(0));
        for (Unit unit : units) {
            if (targets.get(targets.size() - 1).getHp() == unit.getHp()) {
                targets.add(unit);
            } else {
                break;
            }
        }

        // 2. 자신의 위치에 유닛이 있다면 그 타일을 공격. 그렇지 않을 경우 북쪽(위쪽)에 유닛이 있다면 그 타일을 공격. 그렇지 않을 경우 시계
        // 방향으로 검색하다 찾은 유닛의 타일을 공격
        if (targets.size() > 1) {
            boolean isContinue = true;
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (targets.get(i).position.getY() == this.position.getY()
                        && targets.get(i).position.getX() == this.position.getX()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (targets.get(i).position.getY() == this.position.getY() - 1
                        && targets.get(i).position.getX() == this.position.getX()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (targets.get(i).position.getY() == this.position.getY() - 1
                        && targets.get(i).position.getX() == this.position.getX() + 1) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (targets.get(i).position.getY() == this.position.getY()
                        && targets.get(i).position.getX() == this.position.getX() + 1) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (targets.get(i).position.getY() == this.position.getY() + 1
                        && targets.get(i).position.getX() == this.position.getX() + 1) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (targets.get(i).position.getY() == this.position.getY() + 1
                        && targets.get(i).position.getX() == this.position.getX()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (targets.get(i).position.getY() == this.position.getY() + 1
                        && targets.get(i).position.getX() == this.position.getX() - 1) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (targets.get(i).position.getY() == this.position.getY()
                        && targets.get(i).position.getX() == this.position.getX() - 1) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (targets.get(i).position.getY() == this.position.getY() - 1
                        && targets.get(i).position.getX() == this.position.getX() - 1) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
        }

        IntVector2D targetPosition = targets.get(targets.size() - 1).getPosition();
        return new IntVector2D(targetPosition.getX(), targetPosition.getY());
    }

    @Override
    public boolean isAttackable(Unit unit) {
        if (this == unit || unit.getType() != UnitType.AIR || unit.getSymbol() == 'N' || unit.getSymbol() == 'A') {
            return false;
        }

        if (this.position.equals(unit.getPosition())) {
            return true;
        } else if (this.position.getX() == unit.position.getX() && this.position.getY() - 1 == unit.position.getY()) {
            return true;
        } else if (this.position.getX() + 1 == unit.position.getX()
                && this.position.getY() - 1 == unit.position.getY()) {
            return true;
        } else if (this.position.getX() + 1 == unit.position.getX() && this.position.getY() == unit.position.getY()) {
            return true;
        } else if (this.position.getX() + 1 == unit.position.getX()
                && this.position.getY() + 1 == unit.position.getY()) {
            return true;
        } else if (this.position.getX() == unit.position.getX() && this.position.getY() + 1 == unit.position.getY()) {
            return true;
        } else if (this.position.getX() - 1 == unit.position.getX()
                && this.position.getY() + 1 == unit.position.getY()) {
            return true;
        } else if (this.position.getX() - 1 == unit.position.getX() && this.position.getY() == unit.position.getY()) {
            return true;
        } else if (this.position.getX() - 1 == unit.position.getX()
                && this.position.getY() - 1 == unit.position.getY()) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isVisible(Unit unit) {
        if (this == unit || unit.getType() != UnitType.AIR || unit.getSymbol() == 'N' || unit.getSymbol() == 'A') {
            return false;
        }
        return Math.abs(this.getDistance(unit.getPosition())[0]) <= 2
                && Math.abs(this.getDistance(unit.getPosition())[1]) <= 2;
    }
}

package wargame;

import java.util.ArrayList;
import java.util.Comparator;

public final class Marine extends Unit implements IMovable, IThinkable {
    public Marine(IntVector2D position) {
        super(position, 'M', UnitType.LAND, 2, 0, 6, 35, true, true, position);
    }

    @Override
    public void onSpawn() {
        this.simulationManager.registerMovable(this);
        this.simulationManager.registerThinkable(this);
    }

    @Override
    public AttackIntent attack() {
        if (this.actionType != ActionType.ATTACK) {
            return null;
        }
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
            this.findAttackingPointByPosition(targets);
        }

        IntVector2D targetPosition = targets.get(targets.size() - 1).getPosition();
        return new IntVector2D(targetPosition.getX(), targetPosition.getY());
    }

    private void findLowestHp(ArrayList<Unit> targets) {
        targets.sort(Comparator.comparing(Unit::getHp));
        final int orignalSize = targets.size();
        for (int i = orignalSize - 1; i >= 1; i--) {
            if (targets.get(i).getHp() > targets.get(0).getHp()) {
                targets.remove(i);
            }
        }
    }

    @Override
    public IntVector2D findMovingPointOrNull(ArrayList<Unit> units) {
        ArrayList<Unit> targets = new ArrayList<>();
        // 1. 가장 가까이 있는 유닛 쪽으로 이동. 가장 가까운 유닛은 맨해튼 거리를 사용하여 판단합니다.
        units.sort(Comparator.comparing(o -> o.getAbsoluteDistance(this.position)));
        targets.add(units.get(0));

        for (int i = 1; i < units.size(); i++) {
            if (targets.get(targets.size() - 1).getAbsoluteDistance(this.position) == units.get(i)
                    .getAbsoluteDistance(this.position)) {
                targets.add(units.get(i));
            } else {
                break;
            }
        }

        // 2. 가장 약한 유닛 쪽으로 이동
        if (targets.size() > 1) {
            this.findLowestHp(targets);
        }

        // 3. 북쪽에 있는 유닛 쪽으로 이동, 북쪽에 유닛이 없다면 시계 방향으로 검색하다 찾은 유닛 쪽으로 이동
        if (targets.size() > 1) {
            this.findMovingPointByPosition(targets);
        }

        loggerMovingPosition(this.position.getX(), this.position.getY(),
                targets.get(targets.size() - 1).getPosition().getX(),
                targets.get(targets.size() - 1).getPosition().getY(), "findMovingPointOrNull");

        IntVector2D targetPosition = targets.get(targets.size() - 1).getPosition();
        return new IntVector2D(targetPosition.getX(), targetPosition.getY());
    }

    @Override
    public boolean isAttackable(Unit unit) {
        if (this == unit || unit.getSymbol() == 'N' || unit.getSymbol() == 'A') {
            return false;
        }
        if (unit.position.getY() == this.position.getY() && unit.position.getX() == this.position.getX()) {
            return true;
        } else if (unit.position.getY() == this.position.getY() - 1 && unit.position.getX() == this.position.getX()) {
            return true;
        } else if (unit.position.getY() == this.position.getY() && unit.position.getX() == this.position.getX() + 1) {
            return true;
        } else if (unit.position.getY() == this.position.getY() + 1 && unit.position.getX() == this.position.getX()) {
            return true;
        } else if (unit.position.getY() == this.position.getY() && unit.position.getX() == this.position.getX() - 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isVisible(Unit unit) {
        if (this == unit || unit.getSymbol() == 'N' || unit.getSymbol() == 'A') {
            return false;
        }
        return Math.abs(this.getDistance(unit.getPosition())[0]) <= 2
                && Math.abs(this.getDistance(unit.getPosition())[1]) <= 2;
    }

    @Override
    public void move() {
        loggerMovingPosition(this.position.getX(), this.position.getY(), this.movingPoint.getX(),
                this.movingPoint.getY(), "move");
        if (this.actionType == ActionType.MOVE) {
            if (this.getDistance(this.movingPoint)[1] < 0) {
                this.getPosition().setY(this.getPosition().getY() - 1);
            } else if (this.getDistance(this.movingPoint)[1] > 0) {
                this.getPosition().setY(this.getPosition().getY() + 1);
            } else if (this.getDistance(this.movingPoint)[0] < 0) {
                this.getPosition().setX(this.getPosition().getX() - 1);
            } else if (this.getDistance(this.movingPoint)[0] > 0) {
                this.getPosition().setX(this.getPosition().getX() + 1);
            }
        }
    }

    private void loggerMovingPosition(int myX, int myY, int targetX, int targetY, String funcName) {
        System.out.println(String.format("* Marin.%s / moving from me-x%sy%s to target-x%sy%s", funcName, myX, myY,
                targetX, targetY));
    }
}

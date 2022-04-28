package wargame;

import java.util.ArrayList;
import java.util.Comparator;

public final class Wraith extends Unit implements IMovable, IThinkable {
    private boolean isProtected;
    private final IntVector2D initialPosition;
    private int onAttackCnt;

    public Wraith(IntVector2D position) {
        super(position, 'W', UnitType.AIR, 4, 0, 6, 80, true, true, position);
        this.isProtected = false;
        this.initialPosition = new IntVector2D(position.getX(), position.getY());
    }

    @Override
    public void onSpawn() {
        this.simulationManager.registerMovable(this);
        this.simulationManager.registerThinkable(this);
    }

    @Override
    public void onAttacked(int damage) {
        if (this.onAttackCnt == 0 && !this.isProtected) {
            this.isProtected = true;
        }

        if (!this.isProtected) {
            this.hp = Math.max(0, this.hp - damage);
        } else {
            System.out.println("@ shield activated");
        }

        this.onAttackCnt++;
    }

    @Override
    public void update() {
        if (this.onAttackCnt > 0 && isProtected) {
            this.isProtected = false;
            System.out.println("@ shield gone");
        }
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

        // 1. 공중 유닛들을 공격할 후보로 선택
        for (Unit unit : units) {
            if (unit.getType() == UnitType.AIR) {
                targets.add(unit);
            }
        }
        if (targets.size() == 1) {
            IntVector2D position = targets.get(0).getPosition();
            return new IntVector2D(position.getX(), position.getY());
        }

        // 1-2. 공중유닛 중 가장 약한 유닛이 있는 타일을 공격
        if (targets.size() > 1) {
            this.findLowestHp(targets);
        }

        // 1-3. 공중유닛 중 자신의 위치에 유닛이 있다면 그 타일을 공격. 그렇지 않을 경우 북쪽(위쪽)에 유닛이 있다면 그 타일을 공격. 그렇지
        // 않을 경우 시계 방향으로 검색하다 찾은 유닛의 타일을 공격
        if (targets.size() > 1) {
            this.findAttackingPointByPosition(targets);
        }

        // 2. 선택할 공중 유닛이 없다면 지상 유닛들을 선택
        if (targets.isEmpty()) {
            units.sort(Comparator.comparing(Unit::getHp));
            targets.add(units.get(0));

            // 2-1 가장 약한 유닛이 있는 타일을 공격
            for (int i = 1; i < units.size(); i++) {
                if (targets.get(targets.size() - 1).getHp() == units.get(i).getHp()) {
                    targets.add(units.get(i));
                } else {
                    break;
                }
            }
        }

        // 2-2 지상유닛 중 자신의 위치에 유닛이 있다면 그 타일을 공격. 그렇지 않을 경우 북쪽(위쪽)에 유닛이 있다면 그 타일을 공격. 그렇지
        // 않을 경우 시계 방향으로 검색하다 찾은 유닛의 타일을 공격
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

    private void findShortestDistance(ArrayList<Unit> targets) {
        targets.sort(Comparator.comparing(o -> this.getAbsoluteDistance(o.getPosition())));
        final int orignalSize = targets.size();
        for (int i = orignalSize - 1; i >= 1; i--) {
            if (this.getAbsoluteDistance(targets.get(i).getPosition()) > this
                    .getAbsoluteDistance(targets.get(0).getPosition())) {
                targets.remove(i);
            }
        }
    }

    @Override
    public IntVector2D findMovingPointOrNull(ArrayList<Unit> units) {
        // 1. 공중 유닛들을 따라갈 후보로 선택
        ArrayList<Unit> targets = new ArrayList<>();
        for (Unit unit : units) {
            if (unit.getType() == UnitType.AIR) {
                targets.add(unit);
            }
        }
        if (targets.size() == 1) {
            IntVector2D position = targets.get(0).getPosition();
            return new IntVector2D(position.getX(), position.getY());
        }

        // 1-1 공중 유닛 중 가장 가까이 있는 유닛 쪽으로 이동
        if (targets.size() > 1) {
            this.findShortestDistance(targets);
            if (targets.size() == 1) {
                IntVector2D position = targets.get(0).getPosition();
                return new IntVector2D(position.getX(), position.getY());
            }
        }

        // 1-2 공중 유닛 중 가장 약한 유닛 쪽으로 이동
        if (targets.size() > 1) {
            this.findLowestHp(targets);
        }

        // 1-3 공중 유닛 중 북쪽에 있는 유닛 쪽으로 이동. 북쪽에 유닛이 없다면 시계 방향으로 검색하다 찾은 유닛 쪽으로 이동
        if (targets.size() > 1) {
            this.findMovingPointByPosition(targets);
        }

        // 2. 선택할 공중 유닛이 없다면 지상 유닛들을 선택
        if (targets.isEmpty()) {
            units.sort(Comparator.comparing(o -> o.getAbsoluteDistance(this.position)));
            targets.add(units.get(0));

            // 2-1 지상유닛 중 가장 가까이 있는 유닛 쪽으로 이동
            for (int i = 1; i < units.size(); i++) {
                if (targets.get(targets.size() - 1).getAbsoluteDistance(this.position) == units.get(i)
                        .getAbsoluteDistance(this.position)) {
                    targets.add(units.get(i));
                } else {
                    break;
                }
            }
        }

        // 2-2 지상유닛 중 가장 약한 유닛 쪽으로 이동
        if (targets.size() > 1) {
            this.findLowestHp(targets);
        }

        // 2-3 지상유닛 중 북쪽에 있는 유닛 쪽으로 이동. 북쪽에 유닛이 없다면 시계 방향으로 검색하다 찾은 유닛 쪽으로 이동
        if (targets.size() > 1) {
            this.findMovingPointByPosition(targets);
        }

        IntVector2D targetPosition = targets.get(targets.size() - 1).getPosition();
        System.out.println(String.format("in find moving point, myPosition(%s, %s), targetPosition(%s, %s)",
                this.position.getX(), this.position.getY(), targetPosition.getX(), targetPosition.getY()));
        return new IntVector2D(targetPosition.getX(), targetPosition.getY());
    }

    @Override
    public boolean isAttackable(Unit unit) {
        if (this == unit || unit.getType() == UnitType.UNKNOWN || unit.getSymbol() == 'N' || unit.getSymbol() == 'A') {
            return false;
        }
        if (unit.position.getY() == this.position.getY() && unit.position.getX() == this.position.getX()) {
            return true;
        } else if (unit.position.getY() == this.position.getY() + 1 && unit.position.getX() == this.position.getX()) {
            return true;
        } else if (unit.position.getY() == this.position.getY() && unit.position.getX() == this.position.getX() + 1) {
            return true;
        } else if (unit.position.getY() == this.position.getY() - 1 && unit.position.getX() == this.position.getX()) {
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
        return Math.abs(this.getDistance(unit.getPosition())[0]) <= 4
                && Math.abs(this.getDistance(unit.getPosition())[1]) <= 4;
    }

    @Override
    public void move() {
        if (this.actionType == ActionType.MOVE) {
            System.out.println(String.format("in actual move, myPosition(%s, %s), movingPoint(%s, %s)",
                    this.position.getX(), this.position.getY(), this.movingPoint.getX(), this.movingPoint.getY()));
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

    @Override
    public void rest() {
        if (this.movingPoint != this.initialPosition) {
            this.setMovingPoint(this.initialPosition);
        }
        this.actionType = ActionType.MOVE;
    }
}

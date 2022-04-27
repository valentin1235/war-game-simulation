package academy.pocu.comp2500.assignment3;

import java.util.ArrayList;
import java.util.Comparator;

public final class Tank extends Unit implements IMovable, IThinkable {
    private TankMode mode;
    private boolean isTransforming;

    public Tank(IntVector2D position) {
        super(position, 'T', UnitType.LAND, 3, 1, 8, 85, false, true, new IntVector2D(16, position.getY()));
        this.mode = TankMode.TANK;
        this.isTransforming = false;
    }

    @Override
    public void onSpawn() {
        this.simulationManager.registerMovable(this);
        this.simulationManager.registerThinkable(this);
    }

    @Override
    public AttackIntent attack() {
        AttackIntent attackIntent = null;
        switch (this.mode) {
            case TANK:
                break;
            case SIEGE:
                if (this.actionType == ActionType.ATTACK && !isTransforming
                        && !this.attackingPoint.equals(this.position)) {
                    attackIntent = new AttackIntent(this.attackingPoint, this);
                }
                break;
            default:
                assert false;
        }
        return attackIntent;
    }

    @Override
    public IntVector2D findAttackingPointOrNull(ArrayList<Unit> units) {
        // 1. 현재 공성 모드가 아닌 경우 공성 모드로 변경
        if (this.mode == TankMode.TANK) {
            this.mode = TankMode.SIEGE;
            this.isTransforming = true;
            return null;
        } else {
            this.isTransforming = false;
        }
        ArrayList<Unit> targets = new ArrayList<>();

        // 2. 가장 약한 유닛(HP가 가장 낮은 유닛)이 있는 타일을 공격
        units.sort(Comparator.comparing(Unit::getHp));
        targets.add(units.get(0));
        for (Unit unit : units) {
            if (targets.get(targets.size() - 1).getHp() == unit.getHp()) {
                targets.add(unit);
            } else {
                break;
            }
        }

        // 3. 북쪽에 유닛이 있다면 그 타일을 공격. 그렇지 않을 경우 시계 방향으로 검색하다가 찾은 유닛의 타일을 공격
        if (targets.size() > 1) {
            boolean isContinue = true;
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (this.getPosition().getX() == targets.get(i).getPosition().getX()
                        && this.getPosition().getY() - 2 == targets.get(i).getPosition().getY()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (this.getPosition().getX() + 1 == targets.get(i).getPosition().getX()
                        && this.getPosition().getY() - 2 == targets.get(i).getPosition().getY()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (this.getPosition().getX() + 2 == targets.get(i).getPosition().getX()
                        && this.getPosition().getY() - 1 == targets.get(i).getPosition().getY()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (this.getPosition().getX() + 2 == targets.get(i).getPosition().getX()
                        && this.getPosition().getY() == targets.get(i).getPosition().getY()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (this.getPosition().getX() + 2 == targets.get(i).getPosition().getX()
                        && this.getPosition().getY() + 1 == targets.get(i).getPosition().getY()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (this.getPosition().getX() + 1 == targets.get(i).getPosition().getX()
                        && this.getPosition().getY() + 2 == targets.get(i).getPosition().getY()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (this.getPosition().getX() == targets.get(i).getPosition().getX()
                        && this.getPosition().getY() + 2 == targets.get(i).getPosition().getY()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (this.getPosition().getX() - 1 == targets.get(i).getPosition().getX()
                        && this.getPosition().getY() + 2 == targets.get(i).getPosition().getY()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (this.getPosition().getX() - 2 == targets.get(i).getPosition().getX()
                        && this.getPosition().getY() + 1 == targets.get(i).getPosition().getY()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (this.getPosition().getX() - 2 == targets.get(i).getPosition().getX()
                        && this.getPosition().getY() == targets.get(i).getPosition().getY()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (this.getPosition().getX() - 2 == targets.get(i).getPosition().getX()
                        && this.getPosition().getY() - 1 == targets.get(i).getPosition().getY()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
            for (int i = 0; i < targets.size() && isContinue; i++) {
                if (this.getPosition().getX() - 1 == targets.get(i).getPosition().getX()
                        && this.getPosition().getY() - 2 == targets.get(i).getPosition().getY()) {
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
    public IntVector2D findMovingPointOrNull(ArrayList<Unit> units) {
        if (this.mode == TankMode.TANK) {
            this.mode = TankMode.SIEGE;
            this.isTransforming = true;
        } else {
            this.isTransforming = false;
        }
        return null;
    }

    @Override
    public void onAttacked(int damage) {
        switch (this.mode) {
            case TANK:
                this.hp = Math.max(0, this.hp - damage);
                break;
            case SIEGE:
                this.hp = Math.max(0, this.hp - damage * 2);
                break;
            default:
                assert false;
        }

    }

    @Override
    public boolean isAttackable(Unit unit) {
        if (this == unit || unit.getType() != UnitType.LAND || unit.getSymbol() == 'N' || unit.getSymbol() == 'A') {
            return false;
        }

        if (unit.position.getY() == this.position.getY() + 2 && unit.position.getX() == this.position.getX() - 1) {
            return true;
        } else if (unit.position.getY() == this.position.getY() + 2 && unit.position.getX() == this.position.getX()) {
            return true;
        } else if (unit.position.getY() == this.position.getY() + 2
                && unit.position.getX() == this.position.getX() + 1) {
            return true;
        } else if (unit.position.getY() == this.position.getY() + 1
                && unit.position.getX() == this.position.getX() + 2) {
            return true;
        } else if (unit.position.getY() == this.position.getY() && unit.position.getX() == this.position.getX() + 2) {
            return true;
        } else if (unit.position.getY() == this.position.getY() - 1
                && unit.position.getX() == this.position.getX() + 2) {
            return true;
        } else if (unit.position.getY() == this.position.getY() - 2
                && unit.position.getX() == this.position.getX() + 1) {
            return true;
        } else if (unit.position.getY() == this.position.getY() - 2 && unit.position.getX() == this.position.getX()) {
            return true;
        } else if (unit.position.getY() == this.position.getY() - 2
                && unit.position.getX() == this.position.getX() - 1) {
            return true;
        } else if (unit.position.getY() == this.position.getY() - 1
                && unit.position.getX() == this.position.getX() - 2) {
            return true;
        } else if (unit.position.getY() == this.position.getY() && unit.position.getX() == this.position.getX() - 2) {
            return true;
        } else if (unit.position.getY() == this.position.getY() + 1
                && unit.position.getX() == this.position.getX() - 2) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isVisible(Unit unit) {
        if (this == unit || unit.getType() != UnitType.LAND || unit.getSymbol() == 'N' || unit.getSymbol() == 'A') {
            return false;
        } else {
            return Math.abs(this.getDistance(unit.getPosition())[0]) <= 3
                    && Math.abs(this.getDistance(unit.getPosition())[1]) <= 3;
        }
    }

    @Override
    public void move() {
        switch (this.mode) {
            case SIEGE:
                break;
            case TANK:
                if (!isTransforming) {
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
                break;
            default:
                assert false;
        }
    }

    @Override
    public void rest() {
        switch (this.mode) {
            case SIEGE:
                this.mode = TankMode.TANK;
                this.isTransforming = true;
                break;
            case TANK:
                this.isTransforming = false;
                if (this.position.getX() == 15) {
                    this.movingPoint.setX(0);
                } else if (this.position.getX() == 0) {
                    this.movingPoint.setX(15);
                }
                break;
            default:
                assert false;

        }
    }

    private void loggerMode(String direction, boolean shouldStop) {
        System.out.println(String.format("%s에 적발견? %s", direction, shouldStop));
    }
}
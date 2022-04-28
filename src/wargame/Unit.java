package wargame;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class Unit {
    protected SimulationManager simulationManager;
    protected IntVector2D position;
    protected char symbol;
    protected UnitType type;
    protected int vision;
    private int aoe;
    protected int ap;
    protected int hp;
    protected boolean airAttackable;
    protected boolean landAttackable;
    protected IntVector2D attackingPoint;
    protected IntVector2D movingPoint;
    protected ActionType actionType;

    protected Unit(IntVector2D position, char symbol, UnitType type, int vision, int aoe, int ap, int hp,
            boolean airAttackable, boolean landAttackable, IntVector2D movingPoint) {
        this.position = position;
        this.symbol = symbol;
        this.type = type;
        this.vision = vision;
        this.aoe = aoe;
        this.ap = ap;
        this.hp = hp;
        this.airAttackable = airAttackable;
        this.landAttackable = landAttackable;
        this.attackingPoint = position;
        this.movingPoint = movingPoint;
        this.actionType = ActionType.REST;
    }

    public int getAoe() {
        return this.aoe;
    }

    public void setSimulationManager(SimulationManager simulationManager) {
        this.simulationManager = simulationManager;
    }

    public IntVector2D getPosition() {
        return position;
    }

    public int getHp() {
        return this.hp;
    }

    public AttackIntent attack() {
        return null;
    }

    public void onAttacked(int damage) {
        this.hp = Math.max(0, this.hp - damage);
    }

    public abstract void onSpawn();

    public char getSymbol() {
        return this.symbol;
    }

    public UnitType getType() {
        return this.type;
    }

    public void setAttackPoint(IntVector2D attackingPoint) {
        this.attackingPoint = attackingPoint;
    }

    public void setMovingPoint(IntVector2D movingPoint) {
        this.movingPoint = movingPoint;
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public IntVector2D findMovingPointOrNull(ArrayList<Unit> units) {
        return null;
    }

    public void rest() {
    }

    public IntVector2D getMovingPoint() {
        return this.movingPoint;
    }

    protected int getAbsoluteDistance(IntVector2D position) {
        int[] distance = getDistance(position);
        return Math.abs(distance[0]) + Math.abs(distance[1]);
    }

    protected int[] getDistance(IntVector2D position) {
        int[] result = new int[2];
        result[0] = position.getX() - this.position.getX();
        result[1] = position.getY() - this.position.getY();

        return result;
    }

    protected void findMovingPointByPosition(ArrayList<Unit> targets) {
        boolean isContinue = true;

        // 정 북쪽 탐색
        for (int i = 0; i < targets.size() && isContinue; i++) {
            IntVector2D p = targets.get(i).position;
            for (int y = 1; y <= this.vision && isContinue; y++) {
                if (this.position.getX() == p.getX() && this.position.getY() - y == p.getY()) {
                    targets.add(targets.get(i));
                    isContinue = false;
                    break;
                }
            }
        }
        loggerDirection("findMovingPointByPosition", "정북쪽", !isContinue);
        if (!isContinue) {
            return;
        }
        for (Unit unit : targets) {
            System.out.println(String.format("P(%s,%s) A%s", unit.getPosition().getX(), unit.getPosition().getY(),
                    this.getAngle(this.getPosition(), unit.getPosition())));
        }
        targets.sort(Comparator.comparing(o -> this.getAngle(this.getPosition(), o.getPosition()),
                Comparator.reverseOrder()));
    }

    private double getAngle(IntVector2D me, IntVector2D target) {
        double dy = target.getY() - me.getY();
        double dx = target.getX() - me.getX();
        double angle = Math.atan(dy / dx) * (180.0 / Math.PI);
        if (dx < 0.0 && dy > 0.0) {
            // 3사분면
            angle += 270;
            this.loggerAngle("getAngle", target.getX(), target.getY(), angle);
        } else if (dx > 0 && dy < 0.0) {
            // 1사분면
            angle += 90;
            this.loggerAngle("getAngle", target.getX(), target.getY(), angle);
        } else if (dx < 0.0 && dy < 0.0) {
            // 2사분면
            angle += 270;
            this.loggerAngle("getAngle", target.getX(), target.getY(), angle);
        } else if (dx > 0.0 && dy > 0.0) {
            // 4사분면
            angle += 90;
            this.loggerAngle("getAngle", target.getX(), target.getY(), angle);
        } else {
            if (String.format("%s", angle).equals("90.0")) {
                angle = 180.0;
            } else if (String.format("%s", angle).equals("-0.0")) {
                angle = 270.0;
            } else if (String.format("%s", angle).equals("0.0")) {
                angle = 90.0;
            }
        }

        return angle;
    }

    public void update() {
    }

    public void findAttackingPointByPosition(ArrayList<Unit> targets) {
        boolean isContinue = true;
        for (int i = 0; i < targets.size() && isContinue; i++) {
            Unit unit = targets.get(i);
            if (unit.position.getY() == this.position.getY() && unit.position.getX() == this.position.getX()) {
                targets.add(unit);
                isContinue = false;
                break;
            }
        }
        for (int i = 0; i < targets.size() && isContinue; i++) {
            Unit unit = targets.get(i);
            if (unit.position.getY() == this.position.getY() - 1 && unit.position.getX() == this.position.getX()) {
                targets.add(unit);
                isContinue = false;
                break;
            }
        }
        for (int i = 0; i < targets.size() && isContinue; i++) {
            Unit unit = targets.get(i);
            if (unit.position.getY() == this.position.getY() && unit.position.getX() == this.position.getX() + 1) {
                targets.add(unit);
                isContinue = false;
                break;
            }
        }
        for (int i = 0; i < targets.size() && isContinue; i++) {
            Unit unit = targets.get(i);
            if (unit.position.getY() == this.position.getY() + 1 && unit.position.getX() == this.position.getX()) {
                targets.add(unit);
                isContinue = false;
                break;
            }
        }
        for (int i = 0; i < targets.size() && isContinue; i++) {
            Unit unit = targets.get(i);
            if (unit.position.getY() == this.position.getY() && unit.position.getX() == this.position.getX() - 1) {
                targets.add(unit);
                isContinue = false;
                break;
            }
        }
    }

    // logger
    protected void loggerDirection(String funcName, String direction, boolean shouldStop) {
        System.out.println(String.format("* Unit.%s / %s에 적발견? %s", funcName, direction, shouldStop));
    }

    private void loggerAngle(String funcName, int x, int y, double angle) {
        System.out.println(
                String.format("* Unit.%s / target(%s,%s) dx > 0.0 && dy > 0.0, 4사분면 %s", funcName, x, y, angle));
    }
}

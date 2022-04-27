package academy.pocu.comp2500.assignment3;

import java.util.ArrayList;

public final class SimulationManager {
    private static SimulationManager instance;
    private final ArrayList<Unit> units;
    private final ArrayList<IMovable> movables;
    private final ArrayList<ITinkable> thinkables;
    private final ArrayList<ICollisionEventListener> collisionEventListeners;

    private SimulationManager() {
        this.units = new ArrayList<>();
        this.movables = new ArrayList<>();
        this.thinkables = new ArrayList<>();
        this.collisionEventListeners = new ArrayList<>();
    }

    public static SimulationManager getInstance() {
        if (instance == null) {
            instance = new SimulationManager();
        }
        return instance;
    }

    public ArrayList<Unit> getUnits() {
        return this.units;
    }

    public void spawn(Unit unit) {
        unit.setSimulationManager(this);
        unit.onSpawn();
        this.units.add(unit);
    }

    public void registerThinkable(ITinkable thinkable) {
        this.thinkables.add(thinkable);
    }

    public void registerMovable(IMovable movable) {
        this.movables.add(movable);
    }

    public void registerCollisionEventListener(ICollisionEventListener listener) {
        this.collisionEventListeners.add(listener);
    }

    public void update() {
        final ArrayList<AttackIntent> attackIntents = new ArrayList<>();

        // 1. 각 유닛들이 이번 프레임에서 할 행동(선택지: 공격, 이동, 아무것도 안 함)을 결정
        for (ITinkable thinkable : this.thinkables) {
            final ArrayList<Unit> victims = new ArrayList<>();
            final ArrayList<Unit> visibles = new ArrayList<>();
            Unit unit = (Unit) thinkable;
            for (Unit object : this.units) {
                if (thinkable.isAttackable(object) && thinkable.isVisible(object)) {
                    victims.add(object);
                } else if (!thinkable.isAttackable(object) && thinkable.isVisible(object)) {
                    visibles.add(object);
                }
            }

            if (victims.size() > 0) {
                unit.setActionType(ActionType.ATTACK);
                final IntVector2D attackingPointOrNull = thinkable.findAttackingPointOrNull(victims);
                if (attackingPointOrNull != null) {
                    unit.setAttackPoint(attackingPointOrNull);
                }
            }
            if (victims.size() == 0 && visibles.size() > 0) {
                unit.setActionType(ActionType.MOVE);
                final IntVector2D movingPointOrNull = unit.findMovingPointOrNull(visibles);
                if (movingPointOrNull != null) {
                    unit.setMovingPoint(movingPointOrNull);
                }
            }
            if (victims.size() == 0 && visibles.size() == 0) {
                unit.setActionType(ActionType.REST);
                unit.rest();
            }
        }

        // 2. 움직일 수 있는 각 유닛에게 이동할 기회를 줌
        for (IMovable movable : this.movables) {
            movable.move();
            Unit unit = (Unit) movable;
            this.loggingMove("update", unit.getPosition().getX(), unit.getPosition().getY(), unit.getSymbol(), unit.getMovingPoint().getX(), unit.getMovingPoint().getY());
        }

        // 3. 충돌 처리
        for (Unit unit : this.units) {
            for (ICollisionEventListener listener : this.collisionEventListeners) {
                Unit listenerUnit = (Unit) listener;
                if (listenerUnit.getHp() != 0 && unit != listenerUnit && unit.getType() != UnitType.AIR && listenerUnit.getPosition().equals(unit.getPosition())) {
                    final AttackIntent attackIntent = listener.bumpedAndGetAttackIntentOrNull(unit);
                    if (attackIntent != null) {
                        attackIntents.add(attackIntent);
                    }
                }
            }
        }

        // 4. 각 유닛에게 공격할 기회를 줌
        for (Unit attacker : this.units) {
            if (attacker.getSymbol() == 'D') {
                for (Unit unit : this.units) {
                    unit.onAttacked(attacker.ap);
                }
            }
            if (attacker.getActionType() == ActionType.ATTACK) {
                attackIntents.add(attacker.attack());
            }
        }

        // 5. 피해입힐 타일에 데미지 전달
        for (AttackIntent attackIntent : attackIntents) {
            if (attackIntent != null) {
                attackIntent.realize(this.units);
            }
        }

        // 6. 죽은 유닛들을 모두 게임에서 제거함
        int originalSize = this.units.size();
        for (int i = originalSize - 1; i >= 0; i--) {
            if (this.units.get(i).getHp() <= 0) {
                this.thinkables.remove(this.units.get(i));
                this.movables.remove(this.units.get(i));
                this.collisionEventListeners.remove(this.units.get(i));
                this.units.remove(i);
            }
        }

        // 레이스 방패 활성화 되어있으면 제거 or 탱크 모드 변경
        for (Unit unit : this.units) {
            unit.update();
        }
    }

    private void loggingSeperator() {
        System.out.println("------------------------------");
    }

    private void loggingDeath(int x, int y, char symbol, int hp) {
        System.out.println(String.format("* (%s)x%sy%shp%s", symbol, x, y, hp));
    }

    private void loggingMove(String funcName, int x, int y, char symbol, int dx, int dy) {
        System.out.println(String.format("* SimulationManager.%s / moved to (%s)x%sy%s till (%s)x%sy%s", funcName, symbol, x, y, symbol, dx, dy));
    }
}

package wargame.app;

import wargame.Destroyer;
import wargame.SimulationManager;
import wargame.IntVector2D;
import wargame.Marine;
import wargame.Mine;
import wargame.SmartMine;
import wargame.Tank;
import wargame.Turret;
import wargame.Unit;
import wargame.Wraith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Program {

    public static void main(String[] args) {
        // MarinWeakestAtEast();
        // MarinWeakestAtSouth();
        // MarinHpAttackTest();
        // MarinDirectionMoveTest();
        // TurretWraithTest();
        // TankAndTurret();
        // WraithBackToInitialPosition();
        // TankNotAttackWraith();
        // MineGrountUnit();
        // MineAirUnit();
        // TurretNoAirNoAction();
        // SmartMineBombSensor();

        // SimulationTest0_0();
        // SimulationTest0_1();
        // SimulationTest0_2();
        SimulationTest0_3();
        // SimulationTest0_4();
    }

    public static void continueOnEnter() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Press enter to continue");
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void OnlyMarinTest() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit marine1 = new Marine(new IntVector2D(6, 6));
        Unit marine2 = new Marine(new IntVector2D(4, 5));
        Unit marine3 = new Marine(new IntVector2D(5, 6));
        Unit marine4 = new Marine(new IntVector2D(8, 7));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(marine1);
        units.add(marine2);
        units.add(marine3);
        units.add(marine4);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    private static void OnlyWraithTest() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit wraith1 = new Wraith(new IntVector2D(6, 6));
        Unit wraith2 = new Wraith(new IntVector2D(4, 5));
        Unit wraith3 = new Wraith(new IntVector2D(5, 6));
        Unit wraith4 = new Wraith(new IntVector2D(8, 7));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(wraith1);
        units.add(wraith2);
        units.add(wraith3);
        units.add(wraith4);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    private static void TankAndTurret() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit tank1 = new Tank(new IntVector2D(4, 4));
        Unit turret1 = new Turret(new IntVector2D(6, 4));
        Unit turret2 = new Turret(new IntVector2D(6, 5));
        Unit turret3 = new Turret(new IntVector2D(6, 3));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(tank1);
        units.add(turret1);
        units.add(turret2);
        units.add(turret3);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    public static void WraithAndTankTest() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit tank1 = new Tank(new IntVector2D(0, 0));
        Unit tank2 = new Tank(new IntVector2D(6, 5));
        Unit tank3 = new Tank(new IntVector2D(5, 6));
        Unit wraith1 = new Wraith(new IntVector2D(8, 6));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(tank1);
        units.add(tank2);
        units.add(tank3);
        units.add(wraith1);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    public static void MarinWeakestAtEast() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit tank1 = new Tank(new IntVector2D(6, 4));
        Unit turret1 = new Turret(new IntVector2D(2, 4));
        Unit turret2 = new Turret(new IntVector2D(4, 2));
        Unit turret3 = new Turret(new IntVector2D(4, 6));
        Unit marine1 = new Marine(new IntVector2D(4, 4));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(tank1);
        units.add(turret1);
        units.add(turret2);
        units.add(turret3);
        units.add(marine1);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    public static void MarinWeakestAtSouth() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit tank1 = new Tank(new IntVector2D(4, 6));
        Unit turret1 = new Turret(new IntVector2D(2, 4));
        Unit turret2 = new Turret(new IntVector2D(4, 2));
        Unit turret3 = new Turret(new IntVector2D(6, 4));
        Unit marine1 = new Marine(new IntVector2D(4, 4));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(tank1);
        units.add(turret1);
        units.add(turret2);
        units.add(turret3);
        units.add(marine1);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    public static void MarinHpAttackTest() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit tank1 = new Tank(new IntVector2D(3, 4));
        Unit turret1 = new Turret(new IntVector2D(4, 5));
        Unit turret2 = new Turret(new IntVector2D(4, 3));
        Unit turret3 = new Turret(new IntVector2D(5, 4));
        Unit marine1 = new Marine(new IntVector2D(4, 4));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(tank1);
        units.add(turret1);
        units.add(turret2);
        units.add(turret3);
        units.add(marine1);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    public static void MarinDirectionMoveTest() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        // 마린 기준 1234분면
        // Unit turret1 = new Turret(new IntVector2D(2, 6));
        // Unit turret2 = new Turret(new IntVector2D(6, 6));
        // Unit turret3 = new Turret(new IntVector2D(6, 2));
        // Unit turret4 = new Turret(new IntVector2D(2, 2));
        // Unit marine1 = new Marine(new IntVector2D(4, 4));

        // 마린 기준 동서남북
        // Unit turret1 = new Turret(new IntVector2D(4, 2));
        // Unit turret2 = new Turret(new IntVector2D(4, 6));
        // Unit turret3 = new Turret(new IntVector2D(6, 4));
        // Unit turret4 = new Turret(new IntVector2D(2, 4));
        // Unit marine1 = new Marine(new IntVector2D(4, 4));

        // ArrayList<Unit> units = new ArrayList<>();
        // units.add(turret1);
        // units.add(turret2);
        // units.add(turret3);
        // units.add(turret4);
        // units.add(marine1);

        // 마린기준 남북쪽
        Unit turret1 = new Turret(new IntVector2D(4, 6));
        Unit turret2 = new Turret(new IntVector2D(2, 4));
        Unit turret3 = new Turret(new IntVector2D(6, 4));
        Unit marine1 = new Marine(new IntVector2D(4, 4));
        ArrayList<Unit> units = new ArrayList<>();
        units.add(turret1);
        units.add(turret2);
        units.add(turret3);
        units.add(marine1);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    public static void TurretWraithTest() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit turret1 = new Turret(new IntVector2D(4, 5));
        Unit turret2 = new Turret(new IntVector2D(4, 3));
        Unit turret3 = new Turret(new IntVector2D(5, 4));
        Unit turret4 = new Turret(new IntVector2D(3, 4));
        Unit wraith1 = new Wraith(new IntVector2D(4, 4));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(turret1);
        units.add(turret2);
        units.add(turret3);
        units.add(turret4);
        units.add(wraith1);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    public static void WraithShieldTest() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit wraith1 = new Wraith(new IntVector2D(4, 4));
        Unit turret1 = new Turret(new IntVector2D(4, 5));
        Unit turret2 = new Turret(new IntVector2D(4, 3));
        Unit turret3 = new Turret(new IntVector2D(5, 4));
        Unit turret4 = new Turret(new IntVector2D(3, 4));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(wraith1);
        units.add(turret1);
        units.add(turret2);
        units.add(turret3);
        units.add(turret4);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    private static void WraithBackToInitialPosition() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit wraith1 = new Wraith(new IntVector2D(6, 6));
        Unit turret1 = new Marine(new IntVector2D(2, 2));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(wraith1);
        units.add(turret1);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    private static void TankNotAttackWraith() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit tank1 = new Tank(new IntVector2D(6, 6));
        Unit wraith1 = new Wraith(new IntVector2D(4, 6));
        Unit wraith2 = new Wraith(new IntVector2D(4, 5));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(tank1);
        units.add(wraith1);
        units.add(wraith2);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    private static void MineGrountUnit() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit mine1 = new Mine(new IntVector2D(6, 6), 1);
        Unit mine2 = new Mine(new IntVector2D(5, 6), 1);
        Unit tank1 = new Tank(new IntVector2D(4, 6));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(mine1);
        units.add(mine2);
        units.add(tank1);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    private static void MineAirUnit() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit mine1 = new Mine(new IntVector2D(6, 6), 1);
        Unit mine2 = new Mine(new IntVector2D(5, 6), 1);
        Unit wraith1 = new Wraith(new IntVector2D(4, 6));
        Unit turret1 = new Turret(new IntVector2D(7, 6));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(mine1);
        units.add(mine2);
        units.add(wraith1);
        units.add(turret1);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    private static void TurretNoAirNoAction() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit tank1 = new Tank(new IntVector2D(7, 6));
        Unit turret1 = new Turret(new IntVector2D(4, 6));
        Unit marine1 = new Marine(new IntVector2D(3, 5));
        Unit wraith1 = new Wraith(new IntVector2D(4, 5));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(wraith1);
        units.add(tank1);
        units.add(turret1);
        units.add(marine1);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    private static void SmartMineBombSensor() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        Unit tank1 = new Tank(new IntVector2D(6, 6));
        Unit turret1 = new Turret(new IntVector2D(4, 6));
        Unit marine1 = new Marine(new IntVector2D(2, 6));
        Unit wraith1 = new Wraith(new IntVector2D(4, 5));
        Unit smartMine1 = new SmartMine(new IntVector2D(3, 6), 2, 2);
        Unit marine2 = new Marine(new IntVector2D(3, 4));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(wraith1);
        units.add(tank1);
        units.add(turret1);
        units.add(marine1);
        units.add(smartMine1);
        units.add(marine2);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);
        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    private static void SimulationTest0_0() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        // 0
        Unit unit1 = new Wraith(new IntVector2D(15, 0));
        // 1
        Unit unit2 = new Marine(new IntVector2D(4, 3));
        // 2
        Unit unit3 = new Marine(new IntVector2D(10, 5));
        // 3
        Unit unit4 = new Turret(new IntVector2D(3, 4));
        // 4
        Unit unit5 = new Wraith(new IntVector2D(14, 7));
        // 5
        Unit unit6 = new Mine(new IntVector2D(14, 6), 3);
        // 6
        Unit unit7 = new Mine(new IntVector2D(5, 0), 3);
        // 7
        Unit unit8 = new SmartMine(new IntVector2D(8, 0), 2, 2);
        // 8
        Unit unit9 = new Mine(new IntVector2D(4, 2), 3);
        // 9
        Unit unit10 = new Tank(new IntVector2D(9, 6));
        // A
        Unit unit11 = new Turret(new IntVector2D(3, 7));
        // B
        Unit unit12 = new SmartMine(new IntVector2D(14, 3), 3, 1);
        // C
        Unit unit13 = new Tank(new IntVector2D(8, 0));
        // D
        Unit unit14 = new SmartMine(new IntVector2D(2, 4), 4, 1);
        // E
        Unit unit15 = new Wraith(new IntVector2D(10, 0));
        // F
        Unit unit16 = new Wraith(new IntVector2D(4, 5));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(unit1);
        units.add(unit2);
        units.add(unit3);
        units.add(unit4);
        units.add(unit5);
        units.add(unit6);
        units.add(unit7);
        units.add(unit8);
        units.add(unit9);
        units.add(unit10);
        units.add(unit11);
        units.add(unit12);
        units.add(unit13);
        units.add(unit14);
        units.add(unit15);
        units.add(unit16);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);

        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    private static void SimulationTest0_1() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        // 0
        Unit unit0 = new Wraith(new IntVector2D(6, 5));
        // 1
        Unit unit1 = new SmartMine(new IntVector2D(11, 4), 4, 3);
        // 2
        Unit unit2 = new Wraith(new IntVector2D(2, 2));
        // 3
        Unit unit3 = new Marine(new IntVector2D(10, 3));
        // 4
        Unit unit4 = new Tank(new IntVector2D(8, 4));
        // 5
        Unit unit5 = new Tank(new IntVector2D(10, 0));
        // 6
        Unit unit6 = new Wraith(new IntVector2D(11, 6));
        // 7
        Unit unit7 = new Turret(new IntVector2D(0, 7));
        // 8
        Unit unit8 = new Mine(new IntVector2D(11, 1), 4);
        // 9
        Unit unit9 = new Mine(new IntVector2D(5, 5), 1);
        // A
        Unit unit10 = new Marine(new IntVector2D(11, 4));
        // B
        Unit unit11 = new Mine(new IntVector2D(14, 6), 4);
        // C
        Unit unit12 = new Wraith(new IntVector2D(11, 2));
        // D
        Unit unit13 = new Turret(new IntVector2D(15, 3));
        // E
        Unit unit14 = new Marine(new IntVector2D(11, 7));
        // F
        Unit unit15 = new SmartMine(new IntVector2D(4, 7), 3, 3);

        ArrayList<Unit> units = new ArrayList<>();

        units.add(unit0);
        units.add(unit1);
        units.add(unit2);
        units.add(unit3);
        units.add(unit4);
        units.add(unit5);
        units.add(unit6);
        units.add(unit7);
        units.add(unit8);
        units.add(unit9);
        units.add(unit10);
        units.add(unit11);
        units.add(unit12);
        units.add(unit13);
        units.add(unit14);
        units.add(unit15);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);

        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    private static void SimulationTest0_2() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        // 0
        Unit unit0 = new Marine(new IntVector2D(12, 6));
        // 1
        Unit unit1 = new Turret(new IntVector2D(7, 4));
        // 2
        Unit unit2 = new SmartMine(new IntVector2D(2, 5), 2, 2);
        // 3
        Unit unit3 = new Mine(new IntVector2D(7, 3), 2);
        // 4
        Unit unit4 = new Mine(new IntVector2D(7, 7), 4);
        // 5
        Unit unit5 = new Turret(new IntVector2D(1, 6));
        // 6
        Unit unit6 = new Mine(new IntVector2D(11, 0), 4);
        // 7
        Unit unit7 = new SmartMine(new IntVector2D(3, 0), 2, 1);
        // 8
        Unit unit8 = new Turret(new IntVector2D(10, 0));
        // 9
        Unit unit9 = new Turret(new IntVector2D(13, 3));
        // A
        Unit unit10 = new Turret(new IntVector2D(14, 2));
        // B
        Unit unit11 = new Tank(new IntVector2D(14, 6));
        // C
        Unit unit12 = new SmartMine(new IntVector2D(10, 0), 1, 3);
        // D
        Unit unit13 = new Marine(new IntVector2D(12, 6));
        // E
        Unit unit14 = new Wraith(new IntVector2D(8, 7));
        // F
        Unit unit15 = new Wraith(new IntVector2D(15, 7));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(unit0);
        units.add(unit1);
        units.add(unit2);
        units.add(unit3);
        units.add(unit4);
        units.add(unit5);
        units.add(unit6);
        units.add(unit7);
        units.add(unit8);
        units.add(unit9);
        units.add(unit10);
        units.add(unit11);
        units.add(unit12);
        units.add(unit13);
        units.add(unit14);
        units.add(unit15);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);

        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    private static void SimulationTest0_3() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        // 0
        Unit unit0 = new Wraith(new IntVector2D(4, 4));
        // 1
        Unit unit1 = new Tank(new IntVector2D(1, 1));
        // 2
        Unit unit2 = new Tank(new IntVector2D(5, 5));
        // 3
        Unit unit3 = new Tank(new IntVector2D(5, 7));
        // 4
        Unit unit4 = new Turret(new IntVector2D(2, 0));
        // 5
        Unit unit5 = new Tank(new IntVector2D(4, 4));
        // 6
        Unit unit6 = new SmartMine(new IntVector2D(13, 0), 2, 1);
        // 7
        Unit unit7 = new Marine(new IntVector2D(14, 4));
        // 8
        Unit unit8 = new Turret(new IntVector2D(0, 5));
        // 9
        Unit unit9 = new Marine(new IntVector2D(5, 4));
        // A
        Unit unit10 = new Marine(new IntVector2D(11, 3));
        // B
        Unit unit11 = new Mine(new IntVector2D(1, 5), 3);
        // C
        Unit unit12 = new Turret(new IntVector2D(7, 5));
        // D
        Unit unit13 = new Tank(new IntVector2D(1, 7));
        // E
        Unit unit14 = new Tank(new IntVector2D(2, 5));
        // F
        Unit unit15 = new Tank(new IntVector2D(12, 0));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(unit0);
        units.add(unit1);
        units.add(unit2);
        units.add(unit3);
        units.add(unit4);
        units.add(unit5);
        units.add(unit6);
        units.add(unit7);
        units.add(unit8);
        units.add(unit9);
        units.add(unit10);
        units.add(unit11);
        units.add(unit12);
        units.add(unit13);
        units.add(unit14);
        units.add(unit15);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);

        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }

    private static void SimulationTest0_4() {
        SimulationManager simulationManager = SimulationManager.getInstance();

        // 0
        Unit unit0 = new Mine(new IntVector2D(10, 0), 4);
        // 1
        Unit unit1 = new Tank(new IntVector2D(15, 5));
        // 2
        Unit unit2 = new Tank(new IntVector2D(11, 4));
        // 3
        Unit unit3 = new Wraith(new IntVector2D(11, 3));
        // 4
        Unit unit4 = new SmartMine(new IntVector2D(4, 6), 3, 2);
        // 5
        Unit unit5 = new SmartMine(new IntVector2D(8, 2), 4, 2);
        // 6
        Unit unit6 = new SmartMine(new IntVector2D(12, 4), 2, 1);
        // 7
        Unit unit7 = new SmartMine(new IntVector2D(5, 5), 1, 2);
        // 8
        Unit unit8 = new SmartMine(new IntVector2D(1, 4), 1, 3);
        // 9
        Unit unit9 = new Wraith(new IntVector2D(2, 2));
        // A
        Unit unit10 = new Marine(new IntVector2D(9, 2));
        // B
        Unit unit11 = new Tank(new IntVector2D(7, 4));
        // C
        Unit unit12 = new Turret(new IntVector2D(3, 0));
        // D
        Unit unit13 = new SmartMine(new IntVector2D(3, 0), 1, 1);
        // E
        Unit unit14 = new Turret(new IntVector2D(9, 6));
        // F
        Unit unit15 = new Destroyer(new IntVector2D(8, 5));

        ArrayList<Unit> units = new ArrayList<>();

        units.add(unit0);
        units.add(unit1);
        units.add(unit2);
        units.add(unit3);
        units.add(unit4);
        units.add(unit5);
        units.add(unit6);
        units.add(unit7);
        units.add(unit8);
        units.add(unit9);
        units.add(unit10);
        units.add(unit11);
        units.add(unit12);
        units.add(unit13);
        units.add(unit14);
        units.add(unit15);

        for (Unit unit : units) {
            simulationManager.spawn(unit);
        }

        SimulationVisualizer visualizer = new SimulationVisualizer(units);

        visualizer.visualize(0, simulationManager.getUnits());
        for (int i = 1; i < 50; ++i) {
            clearConsole();
            simulationManager.update();
            visualizer.visualize(i, simulationManager.getUnits());
            continueOnEnter();
        }
    }
}
package academy.pocu.comp2500.assignment3;

import academy.pocu.comp2500.assignment3.registry.Registry;

public class App {
    public App(Registry registry) {
        // Register like this
        // registry.registerMarineCreator("Foo");
        // OR
        // registry.registerMarineCreator("Foo", "bar");

        registry.registerMarineCreator("Marine");
        registry.registerTankCreator("Tank");
        registry.registerWraithCreator("Wraith");
        registry.registerTurretCreator("Turret");
        registry.registerMineCreator("Mine");
        registry.registerSmartMineCreator("SmartMine");
        registry.registerDestroyerCreator("Destroyer");
    }
}

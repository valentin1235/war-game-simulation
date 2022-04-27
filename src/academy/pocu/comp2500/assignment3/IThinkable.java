package academy.pocu.comp2500.assignment3;

import java.util.ArrayList;

public interface IThinkable {
    public boolean isVisible(Unit unit);

    public boolean isAttackable(Unit unit);

    public IntVector2D findAttackingPointOrNull(ArrayList<Unit> units);
}

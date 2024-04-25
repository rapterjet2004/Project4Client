
import java.io.Serializable;
import java.util.ArrayList;

import javafx.util.Pair;


public class Message implements Serializable {
    static final long serialVersionUID = 42L;

    boolean isPlacingShips = false;
    boolean isAttacking = false;
    boolean didHit = false;
    ArrayList<Boat> updateBoats;
    Pair<Integer,Integer> attackCoord;


    public void setUpdateBoats(ArrayList<Boat> t){
        updateBoats = t;

    }

    public ArrayList<Boat> getUpdateBoats(){
        return updateBoats;
    }

    public void setAttackCoord(Pair<Integer,Integer> coords){
        attackCoord = coords;
    }

    public Pair<Integer,Integer> getAttackCoord(){
        return attackCoord;
    }



}

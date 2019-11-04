package scheduler;

import java.util.*;

public class Problem {

    private ArrayList<Slot> slots;


    public Problem(){
        this.slots = new ArrayList<Slot>(); 
    }


    public void addSlot(Slot slot){
        this.slots.add(slot);
    }
    

}
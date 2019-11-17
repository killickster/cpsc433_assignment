package scheduler;

public class Preference {

    
    private Slot preferedSlot;
    private int weight;


    public Preference(Slot slot, int weight){
        this.preferedSlot = slot;
        this.weight = weight;
    }

    public Slot getPreferedSlot(){
        return this.preferedSlot;
    }

    public int getWeight(){
        return weight;
    }

}
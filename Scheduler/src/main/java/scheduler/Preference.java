package scheduler;

public class Preference {

    
    private Slot preferedSlot;
    private SlotBooking booking;
    private int weight;


    public Preference(Slot slot, SlotBooking booking, int weight){
        this.preferedSlot = slot;
        this.booking = booking;
        this.weight = weight;
    }

    public Slot getPreferedSlot(){
        return this.preferedSlot;
    }

    public SlotBooking getBooking(){
        return this.booking;
    }
}
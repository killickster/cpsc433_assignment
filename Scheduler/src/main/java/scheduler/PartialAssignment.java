package scheduler;

public class PartialAssignment {

    
    private SlotBooking booking;
    private Slot slot;


    public PartialAssignment(SlotBooking booking, Slot slot){
        this.booking = booking;
        this.slot = slot;
    }

    public SlotBooking getBooking(){
        return this.booking;
    }

    public Slot getSlot(){
        return this.slot;
    }
}
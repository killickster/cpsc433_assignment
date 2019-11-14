package scheduler;

public class Unwanted {

    private Slot slot;
    private SlotBooking booking;

    public Unwanted(SlotBooking booking, Slot slot){

        this.booking = booking;
        this.slot = slot;
    }

    public Slot getSlot(){
        return this.slot;
    }

    public SlotBooking getSlotBooking(){
        return this.booking;
    }
    
}
package scheduler;

public class Unwanted{


    private SlotBooking booking;
    private Slot slot;

    public Unwanted(SlotBooking booking, Slot slot){
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
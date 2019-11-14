package scheduler;

public class Unwanted {

    private Slot slot;
    private RoomBooking booking;

    public Unwanted(RoomBooking booking, Slot slot){

        this.booking = booking;
        this.slot = slot;
    }

    public Slot getSlot(){
        return this.slot;
    }

    public RoomBooking getRoomBooking(){
        return this.booking;
    }
    
}
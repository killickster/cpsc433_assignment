package scheduler;


public class NotCompatible {


    private SlotBooking booking1;
    private SlotBooking booking2;

    public NotCompatible(SlotBooking booking1, SlotBooking booking2){
        this.booking1 = booking1;
        this.booking2 = booking2;

    }

    public SlotBooking getSlotBooking1(){
        return this.booking1;
    }
    

    public SlotBooking getSlotBooking2(){
        return this.booking2;
    }
}
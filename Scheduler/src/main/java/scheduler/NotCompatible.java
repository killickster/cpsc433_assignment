package scheduler;

public class NotCompatible {

    SlotBooking booking1;
    SlotBooking booking2;

    public NotCompatible(SlotBooking booking1, SlotBooking booking2){
        this.booking1 = booking1;
        this.booking2 = booking2;
    }

    public SlotBooking getBooking1(){
        return this.booking1;
    }

    public SlotBooking getBooking2(){
        return this.booking2;
    }
}
package scheduler;

public class Pair{

    private SlotBooking booking1;
    private SlotBooking booking2;

    public Pair(SlotBooking booking1, SlotBooking booking2){
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
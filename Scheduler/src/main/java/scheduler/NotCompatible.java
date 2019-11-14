package scheduler;


public class NotCompatible {


    private RoomBooking booking1;
    private RoomBooking booking2;

    public NotCompatible(RoomBooking booking1, RoomBooking booking2){
        this.booking1 = booking1;
        this.booking2 = booking2;

    }

    public RoomBooking getRoomBooking1(){
        return this.booking1;
    }
    

    public RoomBooking getRoomBooking2(){
        return this.booking2;
    }
}
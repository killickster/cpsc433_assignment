package scheduler;

import java.util.ArrayList;

/**
 * Abstract class extended by Course and Lab classes
 */

public abstract class SlotBooking {

    private String courseIdentifier;
    private ArrayList<Preference> preferences;
    private ArrayList<SlotBooking> paired;           //holds other slot bookings this slot booking is paired with
    private Slot assignedSlot;
    private int slotBookingNumber;
    private int id;

    public SlotBooking(String courseIdentifier, int id){

        this.courseIdentifier = courseIdentifier;
        this.preferences = new ArrayList<Preference>();
        this.paired = new ArrayList<SlotBooking>();
        this.assignedSlot = null;

        this.id = id;
    }

    public int getId(){
        return this.id;
    }


    public void setSlotBookingNumber(int slotBookingNumber){
        this.slotBookingNumber = slotBookingNumber;
    }

    public ArrayList<Preference> getPreferences(){
        return this.preferences;
    }

    public ArrayList<SlotBooking> getPaired(){
        return this.paired;
    }

    public void addPreference(Preference preference){
        this.preferences.add(preference);
    }

    public void addPaired(SlotBooking booking){
        this.paired.add(booking);
    }

    public String getCourseIdentifier(){
        return courseIdentifier;
    }

    public Slot getAssignedSlot(){
        return assignedSlot;
    }

    public void assignSlot(Slot slot){
        this.assignedSlot = slot;
        slot.incrementNumberOfCoursesAssigned();
    }



}
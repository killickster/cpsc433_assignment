package scheduler;

import java.util.ArrayList;

/**
 * Abstract class extended by Course and Lab classes
 */

public abstract class SlotBooking {

    String courseIdentifier;
    ArrayList<Preference> preferences;
    ArrayList<SlotBooking> paired;           //holds other slot bookings this slot booking is paired with
    Slot assignedSlot;

    public SlotBooking(String courseIdentifier){

        this.courseIdentifier = courseIdentifier;
        this.preferences = new ArrayList<Preference>();
        this.paired = new ArrayList<SlotBooking>();
        assignedSlot = null;

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
    }



}
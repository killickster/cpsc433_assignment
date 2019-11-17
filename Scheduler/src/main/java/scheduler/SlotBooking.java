package scheduler;

import java.util.ArrayList;

/**
 * Abstract class extended by Course and Lab classes
 */

public abstract class SlotBooking {

    String courseIdentifier;
    ArrayList<Slot> unwantedSlots;
    ArrayList<Preference> preferences;
    ArrayList<SlotBooking> notCompatible;
    ArrayList<SlotBooking> paired;           //holds other slot bookings this slot booking is paired with

    public SlotBooking(String courseIdentifier){

        this.courseIdentifier = courseIdentifier;
        this.unwantedSlots = new ArrayList<Slot>();
        this.preferences = new ArrayList<Preference>();
        this.paired = new ArrayList<SlotBooking>();
        this.notCompatible = new ArrayList<SlotBooking>();

    }

    public ArrayList<Preference> getPreferences(){
        return this.preferences;
    }

    public ArrayList<Slot> getUnwantedSlots(){
        return this.unwantedSlots;
    }

    public ArrayList<SlotBooking> getPaired(){
        return this.paired;
    }

    public ArrayList<SlotBooking> getNotCompatible(){
        return this.notCompatible;
    }

    public void addPreference(Preference preference){
        this.preferences.add(preference);
    }

    public void addPaired(SlotBooking booking){
        this.paired.add(booking);
    }

    public void addUnwantedSlot(Slot slot){
        this.unwantedSlots.add(slot);
    }

    public void addNotCompatible(SlotBooking booking){
        this.notCompatible.add(booking);
    }

    public String getCourseIdentifier(){
        return courseIdentifier;
    }



}
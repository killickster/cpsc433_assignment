package scheduler;

import java.util.ArrayList;

/**
 * Abstract class extended by Course and Lab classes
 */

public abstract class SlotBooking implements Comparable<SlotBooking> {

    private String courseIdentifier;
    private ArrayList<Preference> preferences;
    private ArrayList<SlotBooking> paired;           //holds other slot bookings this slot booking is paired with
    private Slot assignedSlot;
    private int slotBookingNumber;
    private int id;
    private boolean eveingingClass;
    private int numberOfUncompatible;
    private int numberOfUnwanted;
    private boolean is313 = false;
    private boolean is413 = false;
    private boolean hasPartialAssignemnt = false;
    private int priority = 0;


    public SlotBooking(String courseIdentifier, int id){

        this.courseIdentifier = courseIdentifier;
        this.preferences = new ArrayList<Preference>();
        this.paired = new ArrayList<SlotBooking>();
        this.assignedSlot = null;
        this.numberOfUncompatible = 0;
        this.id = id;

        this.is313 = courseIdentifier.split(" ")[1].equals("313");
        this.is413 = courseIdentifier.split(" ")[1].equals("413");
    }

    public void notCompatibleIncrease(){
        this.numberOfUncompatible++;
    }

    public int getNumberOfUncompatible(){
        return this.numberOfUncompatible;
    }

    public boolean hasPartialAssignemnt(){
        return this.hasPartialAssignemnt;
    }

    public void setHasPartialAssignment(){
        this.hasPartialAssignemnt = true;
    }


    public boolean is313(){
        return this.is313;
    }

    public boolean is413(){
        return this.is413;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
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

    public void unwantedIncrease(){
        this.numberOfUnwanted++;
    }
    public int getNumberOfUnwanted(){
        return this.numberOfUnwanted;
    }

    public int getPriority(){
        return this.priority;
    }

    public void calculatePriority(){
        int priority = 0;
        if(this instanceof Course && (((Course) this).is313Quiz() || ((Course) this).is413Quiz())){
            priority+=100;
        }
        if(this.hasPartialAssignemnt){
            priority+=50;
        } 

        priority += this.numberOfUncompatible*2;

        priority += this.numberOfUnwanted*2;

        this.priority = priority;
    }

    public int compareTo(SlotBooking slotBooking){

        return slotBooking.getPriority() - this.getPriority();

    }



}
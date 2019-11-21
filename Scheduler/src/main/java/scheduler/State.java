package scheduler;

import java.util.ArrayList;


public class State{


    private ArrayList<SlotBooking> slotBookings; 
    private ArrayList<Slot> slots;
    private Problem problem;

    public State(Problem problem){
        this.slotBookings = new ArrayList<SlotBooking>();
        this.slots = new ArrayList<Slot>();

        for(Course course: problem.getCourses()){
            slotBookings.add(course);
        }

        for(Lab lab: problem.getLabs()){
            slotBookings.add(lab);
        }

        for(Slot slot: problem.getLabSlots()){
            this.slots.add(slot);
        }

        for(Slot slot: problem.getCourseSlots()){
            this.slots.add(slot);
        }
    }


    public boolean testCourseMaxConstraint(){

        for(Slot slot: this.slots){
            if(slot.getNumberOfCoursesAssigned() > slot.getCourseMax()){
                return false;
            }
        }
        return true;
    }
    
}
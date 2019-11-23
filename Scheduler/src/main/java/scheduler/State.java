package scheduler;

import java.util.ArrayList;


public class State{


    private SlotBooking[] slotBookings; 
    private int slotBookingsSize;
    private ArrayList<Slot> slots;
    private Problem problem;

    public State(Problem problem){

        this.slotBookingsSize = problem.getCourses().size() + problem.getCourses().size();

        this.slotBookings = new SlotBooking[this.slotBookingsSize];
        this.slots = new ArrayList<Slot>();

        int i = 0;      //Index of current slot booking element

        for(Course course: problem.getCourses()){
            slotBookings[i] = course;
            i++;
        }

        for(Lab lab: problem.getLabs()){
            slotBookings[i] = lab;
            i++;
        }

        for(Slot slot: problem.getLabSlots()){
            this.slots.add(slot);
        }

        for(Slot slot: problem.getCourseSlots()){
            this.slots.add(slot);
        }
    }


    public boolean constr(SlotBooking[] slotBookings){

       boolean courseMaxConstraint = this.testCourseMaxContraint();

       return true;
    }

    public boolean testCourseMaxContraint(){

        for(int i = 0; i < slotBookingsSize; i++){
            Slot slot = slotBookings[i].getAssignedSlot();
            if(slot != null){
                if(slot.getNumberOfCoursesAssigned() > slot.getCourseMax()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean testCourseLabTimeConflict(){
        for(int i = 0; i < this.slotBookingsSize; i++){
            if(this.slotBookings[i] instanceof Course){
                for(Lab lab: ((Course) this.slotBookings[i]).getLabs()){
                    if(lab.getAssignedSlot() != null && this.slotBookings[i].getAssignedSlot() != null){
                        if(lab.getAssignedSlot().getStartTime().equals(this.slotBookings[i].getAssignedSlot().getStartTime())){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
}
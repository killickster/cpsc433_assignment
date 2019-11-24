package scheduler;

import java.util.ArrayList;


public class State{


    private SlotBooking[] slotBookings; 
    private int slotBookingsSize;
    private ArrayList<Slot> slots;
    private Problem problem;
    private ArrayList<NotCompatible> notCompatible;
    private ArrayList<PartialAssignment> partialAssignments;
    private ArrayList<Unwanted> unwanted;

    public State(Problem problem){

        this.slotBookingsSize = problem.getCourses().size() + problem.getCourses().size();

        this.slotBookings = new SlotBooking[this.slotBookingsSize];
        this.notCompatible = problem.getNotCompatible();
        this.slots = new ArrayList<Slot>();
        this.partialAssignments = problem.getPartialAssignemnts();
        this.unwanted = problem.getUwanted();


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


    public boolean constr(){

       boolean courseMaxConstraint = this.testCourseMaxContraint();
       boolean courseLabTimeConflict = this.testCourseLabTimeConflict();
       boolean nonCompatible = this.testNonCompatible();
       boolean partialAssignments = this.testPartialAssignment();
       boolean unwanted = this.testUnwanted();

       if(courseLabTimeConflict && courseLabTimeConflict && nonCompatible && partialAssignments && unwanted){
           return true;
       }else{
           return false;
       }

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

    public boolean testNonCompatible(){
        for(NotCompatible notCompatible: this.notCompatible){
            if(notCompatible.booking1.getAssignedSlot().getStartTime().equals(notCompatible.booking2.getAssignedSlot().getStartTime())){
                return false;
            }
        }
        return true;
    }

    public boolean testPartialAssignment(){

        for(PartialAssignment partialAssignemnt: this.partialAssignments){
            if(partialAssignemnt.getBooking().getAssignedSlot() != null){
                if(!partialAssignemnt.getBooking().getAssignedSlot().equals(partialAssignemnt.getSlot())){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean testUnwanted(){
        for(Unwanted unwanted: this.unwanted){

            if(unwanted.getBooking().getAssignedSlot() != null){
                if(unwanted.getBooking().getAssignedSlot().equals(unwanted.getSlot())){
                    return false;
                }
            }
        }
        return true;
    }


    
}
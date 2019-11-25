package scheduler;

import java.util.ArrayList;


public class OTree{

    private ArrayList<Lab> labs;
    private ArrayList<Course> courses;
    private ArrayList<Slot> labSlots;
    private ArrayList<Slot> courseSlots;
    private Problem problem;
    private ArrayList<NotCompatible> notCompatible;
    private ArrayList<PartialAssignment> partialAssignments;
    private ArrayList<Unwanted> unwanted;
    private State rootNode;

    public OTree(Problem problem){

        int numberOfCourses = problem.getCourses().size();
        int numberOfLabs = problem.getLabs().size();
        int numberOfCourseSlots = problem.getCourseSlots().size();
        int numberOfLabSlots = problem.getLabSlots().size();

        this.labs = problem.getLabs();
        this.courses = problem.getCourses();
        this.labSlots = problem.getLabSlots();
        this.courseSlots = problem.getCourseSlots();
        this.notCompatible = problem.getNotCompatible();
        this.partialAssignments = problem.getPartialAssignemnts();
        this.unwanted = problem.getUwanted();

        this.rootNode = new State(numberOfLabs, numberOfCourses, numberOfLabSlots, numberOfCourseSlots);


    }

    public boolean constr(State state){

        boolean courseMaxConstraint = this.testCourseMaxConstraint(state);


        return courseMaxConstraint;
    }


    public boolean testCourseMaxConstraint(State state){

        for(int i = 0; i < state.getNumberOfFilledCourses(); i++){

            int slotNumber = state.getCourses()[i];

            if(state.getCourseSlots()[slotNumber] > this.courseSlots.get(slotNumber).getCourseMax()){
                return false;
            }
        }

        for(int i = 0; i < state.getNumberOfFilledLabs(); i++){

            int slotNumber = state.getLabs()[i];

            if(state.getLabSlots()[slotNumber] > this.labSlots.get(slotNumber).getCourseMax()){
                return false;
            }
        }

        return true;

    }

    public boolean testCourseLabTimeConflict(State state){

        String courseDay = this.courseSlots.get(state.getCourses()[state.getNumberOfFilledCourses()-1]).getDay();
        String courseTime = this.courseSlots.get(state.getCourses()[state.getNumberOfFilledCourses()-1]).getStartTime();

        ArrayList<Lab> labs = this.courses.get(state.getNumberOfCourseSlots()-1).getLabs();

        for(Lab lab: labs){
            int id = lab.getId();

            String labDay = this.labSlots.get(state.getLabs()[id]).getDay();
            String labTime = this.labSlots.get(state.getLabs()[id]).getStartTime();

            if(labDay.equals(courseDay) && labTime.equals(courseTime)){
                return false;
            }

        }

        return true;
            
    }


    public State getRootNode(){
        return this.rootNode;
    }
    



    /*


    public boolean constr(){

       boolean courseMaxConstraint = this.testCourseMaxContraint();
       boolean courseLabTimeConflict = this.testCourseLabTimeConflict();
       boolean nonCompatible = this.testNonCompatible();
       boolean partialAssignments = this.testPartialAssignment();
       boolean unwanted = this.testUnwanted();

       if(courseMaxConstraint && courseLabTimeConflict && courseLabTimeConflict && nonCompatible && partialAssignments && unwanted){
           return true;
       }else{
           return false;
       }

    }

    public boolean testCourseMaxConstraint(){

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


    public void displayState(){

        for(int i = 0 ; i < this.slotBookingsSize; i++){
            if(slotBookings[i] instanceof Course){
                Course course = ((Course) slotBookings[i]);
                if(course.getAssignedSlot() != null){
                    System.out.println(course.getCourseIdentifier() + " " + course.getCourseSection() + "\t :" + course.getAssignedSlot().getDay() + ", " + course.getAssignedSlot().getStartTime());
                }
            }else if(slotBookings[i] instanceof Lab){
                Lab lab = ((Lab) slotBookings[i]); 

                if(lab.getAssignedSlot() != null){
                    System.out.println(lab.getCourseIdentifier() + "\t :" + lab.getAssignedSlot().getDay() + ", " + lab.getAssignedSlot().getStartTime());
                }
            }else{
                ExclusiveLab lab = ((ExclusiveLab) slotBookings[i]);

                if(lab.getAssignedSlot() != null){

                    System.out.println(lab.getCourseIdentifier() + " " + lab.getCourseSection() + "\t :" + lab.getAssignedSlot().getDay() + ", " + lab.getAssignedSlot().getStartTime());
                }


            }

        }

    }
    */
    
}
package scheduler;

import java.util.ArrayList;
import java.util.Collections;


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
    private OTree parent;
    private ArrayList<OTree> children;
    private Slot tuesday11Slot;

    public OTree(Problem problem){

        problem.calculateNumberOfUncompatible();
        problem.regenerateIds();
        problem.addConstraintsFor913and813();

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

        this.problem = problem;
        this.rootNode = new State(numberOfLabs, numberOfCourses, numberOfLabSlots, numberOfCourseSlots, problem);
        this.parent = null; 
        this.children = new ArrayList<OTree>();

    }

    public boolean constr(State state){
        boolean courseMaxConstraint = this.testCourseMaxConstraint(state);
        boolean courseLabTimeConflict = this.testCourseLabTimeConflict(state);
        boolean nonCompatible = this.testNonCompatible(state);
        boolean partialAssignment = this.testPartialAssignemnt(state);
        boolean unwanted = this.testUnwanted(state);
        boolean courseEveningRequirements = this.testEveningSlotRequirements(state);
        boolean level500TimeConflict = this.test500LevelConflict(state);
        boolean tuesBookingCorrect = this.testNoBookingOnTues11(state);
        boolean computationClassesCorrect = this.test413and313valid(state);

        if(courseMaxConstraint && courseLabTimeConflict && nonCompatible && partialAssignment && unwanted && level500TimeConflict && courseEveningRequirements && tuesBookingCorrect && computationClassesCorrect){
            return true;
        }

        return false;
   
    }

    public void traverseTree(){

        State state = this.rootNode;

        state.generateChildNodes();

        int numberOfOperations = 0;



        while(state.getNumberOfFilledCourses() != this.courses.size() || state.getNumberOfFilledLabs() != this.labs.size()){


            numberOfOperations++;
            ArrayList<State> states = state.getChildNodes();

            //System.out.println("Number of childeren: " + state.getChildNodes().size());
            
            int deepest = 0;
            boolean tie = false;

            for(int i = 0; i<states.size(); i++){
                if(constr(states.get(i)) == false){
                    states.remove(i);
                    i--;
                }
            }

            for(State childState: states){

                if(childState.getDepth() == deepest){
                    tie = true;
                }

                if(childState.getDepth() > deepest){
                    deepest = childState.getDepth();
                }

            }

            ArrayList<State> statesOfEqualDepth = new ArrayList<State>();

            for(State cState: states){
                if(cState.getDepth() == deepest){
                    statesOfEqualDepth.add(cState);
                }
            }

            if(states.size() == 0){
            	
            	 if(state.equals(this.getRootNode())) {
                 	System.out.println("No solution");
                 	return;
                 	
                 }
            	
                for(int i = 0; i < state.getParent().getChildNodes().size(); i++){
                    if(state.getParent().getChildNodes().get(i).equals(state)){
                        //System.out.println("removing itself");
                        state.getParent().getChildNodes().remove(i);
                        
                       
                    }
                }


                state = state.getParent();
            }else if(states.size() > 0){
//                System.out.println("size" + states.size());
 
                Collections.sort(states);
                state = states.get(0);


                state.generateChildNodes();
            }

             //System.out.println("Depth: " + state.getDepth());

        }

        this.displayState(state);




    }


    public boolean testCourseMaxConstraint(State state){

        for(int i = 0; i < state.getNumberOfFilledCourses(); i++){

            int slotNumber = state.getCourses()[i];

            if(state.getCourseSlots()[slotNumber-1] > this.courseSlots.get(slotNumber-1).getCourseMax()){
                return false;
            }
        }

        for(int i = 0; i < state.getNumberOfFilledLabs(); i++){

            int slotNumber = state.getLabs()[i];

            if(state.getLabSlots()[slotNumber-1] > this.labSlots.get(slotNumber-1).getCourseMax()){
                return false;
            }
        }

        return true;
    }

    public boolean testCourseLabTimeConflict(State state){

        if(state.getNumberOfFilledCourses()-1 != -1){

        //System.out.println(state.getNumberOfFilledCourses());
        int slotIdForCurrentlyAssignedCourse = state.getCourses()[state.getNumberOfFilledCourses()-1];


        String courseDay = this.courseSlots.get(slotIdForCurrentlyAssignedCourse-1).getDay();
        Integer courseStart = this.courseSlots.get(slotIdForCurrentlyAssignedCourse-1).getBeginTime();
        Integer courseEnd = this.courseSlots.get(slotIdForCurrentlyAssignedCourse-1).getEndTime();

        
        ArrayList<Lab> labs = this.courses.get(state.getNumberOfFilledCourses()-1).getLabs();


        for(Lab lab: labs){
            int id = lab.getId();

            //System.out.println(id);

            int currentlyAssignedLabId = state.getLabs()[id-1];

            if(currentlyAssignedLabId != 0){
                String labDay = this.labSlots.get(currentlyAssignedLabId-1).getDay();
                Integer labStart = this.labSlots.get(currentlyAssignedLabId-1).getBeginTime();
                Integer labEnd = this.labSlots.get(currentlyAssignedLabId-1).getEndTime();

                if(labDay.equals(courseDay) && ((labStart >= courseStart && labStart < courseEnd) || (labEnd > courseStart && labEnd <= courseStart))){

                    return false;
                }
            }

        }


    }
        return true;
            
    }


    public boolean testNonCompatible(State state){

        int labId = state.getNumberOfFilledLabs()-1;
        int courseId = state.getNumberOfFilledCourses()-1;
        
        for(NotCompatible nc: this.notCompatible){

            int id1 = nc.getBooking1().getId();

            int id2= nc.getBooking2().getId();


            Slot slot1 = new Slot();
            Slot slot2 = new Slot();


            if(nc.getBooking1() instanceof Course){

                if(state.getCourses()[id1-1] != 0){

                slot1 = this.courseSlots.get(state.getCourses()[id1-1]-1);

                }

            }else{

                if(state.getLabs()[id1-1] != 0){
              
                slot2 = this.labSlots.get(state.getLabs()[id1-1]-1);

                }
            }

            if(nc.getBooking2() instanceof Course){

                if(state.getCourses()[id2-1] != 0){

                slot2 = this.courseSlots.get(state.getCourses()[id2-1]-1);

                }

            }else{

                if(state.getLabs()[id2-1] != 0){
              

                slot2 = this.labSlots.get(state.getLabs()[id2-1]-1);

                }
            }

            if((slot1 instanceof CourseSlot || slot1 instanceof LabSlot) && (slot2 instanceof CourseSlot || slot2 instanceof LabSlot) && slot1.timeConflict(slot2)){
                return false;
            }

        }

        return true;
    }

    public boolean testPartialAssignemnt(State state){

        for(PartialAssignment pa: this.partialAssignments){


            int slotId;

            if(pa.getBooking() instanceof Course){

                int id = pa.getBooking().getId();

                slotId = state.getCourses()[id-1];

            }else{
                int id = pa.getBooking().getId();

                slotId = state.getLabs()[id-1];
            

            }


            if(slotId != 0 && slotId != pa.getSlot().getId()){
                return false;
            }

        }

        return true;

    }

    public boolean testUnwanted(State state){

        for(Unwanted unwanted: this.unwanted){



            int slotId;

            if(unwanted.getBooking() instanceof Course){

                slotId = state.getCourses()[unwanted.getBooking().getId()-1];

            }else{
                slotId = state.getLabs()[unwanted.getBooking().getId()-1];

                
            }


            if(slotId == unwanted.getSlot().getId()){
                return false;
            }
        }

        return true;
    }

    //All course sections with a section number starting LEC 9 are evening classes and have to be scheduled into evening slots.

    public boolean testEveningSlotRequirements(State state){


        if(state.getNumberOfFilledCourses() != 0){
        int slotIdForCurrentlyAssignedCourse = state.getCourses()[state.getNumberOfFilledCourses()-1];

        if(this.courses.get(state.getNumberOfFilledCourses()-1).isEvening()){

            Slot slot = this.courseSlots.get(slotIdForCurrentlyAssignedCourse-1);

            if(!slot.isEvening()){
                return false;
            }

        }

    }
        return true;   
    }

    public boolean test500LevelConflict(State state){

        if(state.getNumberOfFilledCourses() != 0){
        int mostRecentCourseId = state.getNumberOfFilledCourses();

        int slotIdMostRecentCourse = state.getCourses()[mostRecentCourseId-1];


        Slot slotMostRecentCourse = this.courseSlots.get(slotIdMostRecentCourse-1);

        if(this.courses.get(mostRecentCourseId-1).isSenior()){

            for(Course course: this.courses){

                

                if(course.isSenior()){

                    int slotId = state.getCourses()[course.getId()-1];


                    if(slotId != 0 && course.getId() != mostRecentCourseId){

                        if(this.courseSlots.get(slotId -1).timeConflict(slotMostRecentCourse)){


                            //System.out.println("conflict");

                            return false;
                        }
                    
                    }

                }
            }
        }
    }

    	return true;
    }

    public boolean testNoBookingOnTues11(State state){


        int courseId = state.getNumberOfFilledCourses();

        if(courseId != 0){

            int courseSlotId = state.getCourses()[courseId -1];

            if(courseSlotId != 0){
                Slot slotMostRecentCourse = this.courseSlots.get(courseSlotId-1);

                if(slotMostRecentCourse.getDay().equals("TU")){
                    if((slotMostRecentCourse.getBeginTime() >= 1100 && slotMostRecentCourse.getBeginTime() < 1230) || (slotMostRecentCourse.getEndTime() > 1100 && slotMostRecentCourse.getEndTime() <= 1230)){
                        return false;
                    }
                }
            }
        }


        return true;
    }


    public boolean test413and313valid(State state){
        return true;
    }



    public State getRootNode() { return this.rootNode; }  



    public void displayState(State state){

        for(int i = 0; i < state.getNumberOfFilledCourses(); i++){

            Course course = this.courses.get(i);

            Slot slot = this.courseSlots.get(state.getCourses()[i]-1);


            System.out.println("Course: " + course.getCourseIdentifier() + "\t" + course.getCourseSection() + " \t: " + slot.getDay()  + ", " + slot.getStartTime());
        }

        for(int i = 0; i < state.getNumberOfFilledLabs(); i++){

            Lab lab = this.labs.get(i);

            Slot slot = this.labSlots.get(state.getLabs()[i]-1);

            System.out.println("Lab: " + lab.getCourseIdentifier() + "\t" + lab.getLabType() + " " + lab.getLabSection() + " \t: " + slot.getDay()  + ", " + slot.getStartTime());
        }

    }    
}
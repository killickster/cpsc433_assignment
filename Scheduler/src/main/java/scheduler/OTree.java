package scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


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

        this.problem = problem;
        this.rootNode = new State(numberOfLabs, numberOfCourses, numberOfLabSlots, numberOfCourseSlots);
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

        if(courseMaxConstraint && courseLabTimeConflict && nonCompatible && partialAssignment && unwanted && courseEveningRequirements && level500TimeConflict && tuesBookingCorrect && computationClassesCorrect){
            System.out.println("true");
            return true;
        }
        System.out.println("false");

        return false;
   
    }

    public void traverseTree(Random rand, boolean random){

        State state = this.rootNode;

        state.generateChildNodes();

        int numberOfOperations = 0;



        while(state.getNumberOfFilledCourses() != this.courses.size() || state.getNumberOfFilledLabs() != this.labs.size()){


            numberOfOperations++;
            ArrayList<State> states = state.getChildNodes();


            
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
                for(int i = 0; i < state.getParent().getChildNodes().size(); i++){
                    if(state.getParent().getChildNodes().get(i).equals(state)){
                        state.getParent().getChildNodes().remove(i);
                    }
                }

                System.out.println("going up");

                state = state.getParent();
            }


            Collections.sort(states);

            if(states.size() > 0 && !random){

                int x = rand.nextInt((states.size()-1 - 0) + 1) + 0;

                //System.out.println("size" + states.size());
                //System.out.println("selected" + x);

                state = states.get(x);

                state.generateChildNodes();
            }else if(states.size() > 0 && random){
//                System.out.println("size" + states.size());
 
                state = states.get(0);

                state.generateChildNodes();
            }


        }

        this.displayState(state);

        System.out.println(numberOfOperations);

        System.out.println("Depth: " + state.getDepth());

        System.out.println(constr(state));




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


            String slot1Day = null;
            String slot1Time = null;
            String slot2Day= null;
            String slot2Time = null;



            if(nc.getBooking1() instanceof Course){

                if(state.getCourses()[id1-1] != 0){

                Slot slot = this.courseSlots.get(state.getCourses()[id1-1]-1);

                slot1Day = slot.getDay();

                slot1Time = slot.getStartTime();

                }

            }else{

                if(state.getLabs()[id1-1] != 0){
              
                Slot slot = this.labSlots.get(state.getLabs()[id1-1]-1);

                slot1Day = slot.getDay();

                slot1Time = slot.getStartTime();
                }
            }

            if(nc.getBooking2() instanceof Course){

                if(state.getCourses()[id2-1] != 0){

                Slot slot = this.courseSlots.get(state.getCourses()[id2-1]-1);

                slot2Day = slot.getDay();

                slot2Time = slot.getStartTime();

                }

            }else{

                if(state.getLabs()[id2-1] != 0){
              

                Slot slot = this.labSlots.get(state.getLabs()[id2-1]-1);

                slot2Day = slot.getDay();

                slot2Time = slot.getStartTime();
                }
            }


            if(slot1Day != null && slot1Time != null && slot2Day != null && slot2Time != null && slot1Day.equals(slot2Day) && slot1Time.equals(slot2Time)){
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

        //System.out.println(this.unwanted.size());
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

        int mostRecentCourseId = state.getNumberOfFilledCourses();

        int slotIdMostRecentCourse = state.getCourses()[mostRecentCourseId];

        Slot slotMostRecentCourse = this.courseSlots.get(slotIdMostRecentCourse-1);

        if(this.courses.get(mostRecentCourseId).isSenior()){

            for(Course course: this.courses){

                if(course.isSenior()){

                    int slotId = state.getCourses()[course.getId()-1];

                    if(slotId != 0){

                        if(this.courseSlots.get(slotId -1).timeConflict(slotMostRecentCourse)){

                            return false;
                        }
                    
                    }

                }
            }
        }

    	return true;
    }

    public boolean testNoBookingOnTues11(State state){
        return true;
    }


    public boolean test413and313valid(State state){
    	
        return true;
    }



    public State getRootNode() { return this.rootNode; }  
/*
    public Problem getProblem() { return this.problem; }
    public OTree getParent() { return this.parent; }
    public void setParent(OTree parent) { this.parent = parent; }
    public int numberOfChildren() { return this.children.size(); }
    
    //pops the leftmost child to continue leftmost-dfs. If there are no children, backtracking is required, thus return parent.
    public OTree getLeftmostChild() {
    	if (this.children.size() > 0) { return this.children.remove(0); }
    	else {
    		System.out.println("This node has no remaining children. Returning this node's parent instead");
    		return this.parent;
    	} 
    }
    
    public void generateChildren() {
    	/*Select the ~first~ unfilled slot and make a copy of this tree/node for each unassigned course or lab w.r.t. the slot type and
    	 * assign these copies to this.children.
    	 * As or after children are generated assign each one one of the unassigned courses/labs that determined the number of copies.
    	 * > Do this by checking SlotBooking(?) for each course/lab (or slot?) to determine the correct number of children. Not sure yet.
        */
        
    	/*
    	//this many children
    	int courseChildren = this.courses.size()-this.rootNode.getNumberOfFilledCourses(); 
    	int labChildren = 	 this.labs.size()-this.rootNode.getNumberOfFilledLabs();
    	
    	//for each course child, add one partial assignment of an unassigned course.
    	for (int i = 0; i < courseChildren; i++) {
    		OTree child = new OTree(this.problem);   		
    		this.children.add(child);
    		child.setParent(this);

    		for (Course c : courses) {
    			if (c.getAssignedSlot() == null) {
    				for (Slot s : courseSlots) {
    					if (s.getNumberOfCoursesAssigned() < s.getCourseMax()) {
    						child.getProblem().addPartialAssignment(new PartialAssignment((SlotBooking) c,s));
    						break;
    					}
    				}
    			}
    		}
    	}
    	//mirrors the above but for labs
    	for (int i = 0; i < labChildren; i++) {
    		OTree child = new OTree(this.problem);
    		child.setParent(this);
    		this.children.add(child);
    		for (Lab l : labs) {
    			if (l.getAssignedSlot() == null) {
    				for (Slot s : labSlots) {
    					if (s.getNumberOfCoursesAssigned() < s.getCourseMax()) {
    						child.getProblem().addPartialAssignment(new PartialAssignment((SlotBooking) l,s));
    						break;
    					}
    				}
    			}
    		}
    	}
    }

*/

    
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

    */

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
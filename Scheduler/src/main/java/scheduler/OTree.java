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
            return true;
        }

        return false;
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

        //System.out.println(state.getNumberOfFilledCourses());
        int slotIdForCurrentlyAssignedCourse = state.getCourses()[state.getNumberOfFilledCourses()-1];


        String courseDay = this.courseSlots.get(slotIdForCurrentlyAssignedCourse-1).getDay();
        String courseTime = this.courseSlots.get(slotIdForCurrentlyAssignedCourse-1).getStartTime();

        
        ArrayList<Lab> labs = this.courses.get(state.getNumberOfFilledCourses()-1).getLabs();


        for(Lab lab: labs){
            int id = lab.getId();

            //System.out.println(id);

            int currentlyAssignedLabId = state.getLabs()[id-1];

            if(currentlyAssignedLabId != 0){
                String labDay = this.labSlots.get(currentlyAssignedLabId-1).getDay();
                String labTime = this.labSlots.get(currentlyAssignedLabId-1).getStartTime();

                if(labDay.equals(courseDay) && labTime.equals(courseTime)){

                    return false;
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

                Slot slot = this.labSlots.get(id1-1);

                slot1Day = slot.getDay();

                slot1Time = slot.getStartTime();

                }

            }else{

                if(state.getLabs()[id1-1] != 0){
               
                Slot slot = this.labSlots.get(id1-1);

                slot1Day = slot.getDay();

                slot1Time = slot.getStartTime();
                }
            }

            if(nc.getBooking2() instanceof Course){

                if(state.getCourses()[id2-1] != 0){

                Slot slot = this.courseSlots.get(id1-1);

                slot2Day = slot.getDay();

                slot2Time = slot.getStartTime();

                }

            }else{

                if(state.getLabs()[id1-1] != 0){
               
                Slot slot = this.labSlots.get(id1-1);

                slot2Day = slot.getDay();

                slot2Time = slot.getStartTime();
                }
            }

            if(slot1Day.equals(slot2Day) && slot1Time.equals(slot2Time)){
                return true;
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

            System.out.println(slotId);

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

        int slotIdForCurrentlyAssignedCourse = state.getCourses()[state.getNumberOfFilledCourses()-1];

        System.out.println(this.courses.get(state.getNumberOfFilledCourses()-1).isEvening());

        if(this.courses.get(state.getNumberOfFilledCourses()-1).isEvening()){

            Slot slot = this.courseSlots.get(slotIdForCurrentlyAssignedCourse-1);

            System.out.println(slot.isEvening());

            if(!slot.isEvening()){
                return false;
            }

        }
        
        return true;   
    }

    public boolean test500LevelConflict(State state){
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
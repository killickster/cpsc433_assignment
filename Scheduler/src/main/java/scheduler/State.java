package scheduler;

import java.util.ArrayList;


public class State implements Comparable<State>{

    private int[] labs; 
    private int labsSize;
    private int[] courses;
    private int coursesSize;
    private int numberOfFilledLabs;
    private int numberOfFilledCourses;
    private int[] labSlots;
    private int labSlotsSize;
    private int[] courseSlots;
    private int courseSlotsSize;
    private ArrayList<State> childNodes;
    private State parent;
    private int depth;
    private int order;
   
    
    //Constructor for creating first state

    public State(int numberOfLabs, int numberOfCourses, int numberOfLabSlots, int numberOfCourseSlots){

        this.labs = new int[numberOfLabs];
        this.courses = new int[numberOfCourses];
        this.labSlots = new int[numberOfLabSlots];
        this.courseSlots = new int[numberOfCourseSlots];
        this.coursesSize = numberOfCourses;
        this.labsSize = numberOfLabs;
        this.courseSlotsSize = numberOfCourseSlots;
        this.labSlotsSize = numberOfLabSlots;
        this.numberOfFilledCourses = 0;
        this.numberOfFilledLabs = 0;
        this.depth = 1;
        this.order = 1;
    }

    public State(State state, int order){

        this.labs = new int[state.getLabsSize()];
        System.arraycopy(state.getLabs(), 0, this.labs, 0, state.getLabsSize());
        this.courses = new int[state.getCoursesSize()];
        System.arraycopy(state.getCourses(), 0, this.courses,0, state.getCoursesSize());
        this.labSlots = new int[state.getLabsSize()];
        System.arraycopy(state.getLabSlots(), 0, this.labSlots,0, state.getNumberOfLabSlots());
        this.courseSlots = new int[state.getLabsSize()];
        System.arraycopy(state.getCourseSlots(), 0, this.courseSlots, 0, state.getNumberOfCourseSlots());
        this.coursesSize = state.getCoursesSize();
        this.labsSize = state.getLabsSize();
        this.numberOfFilledCourses = state.getNumberOfFilledCourses();
        this.numberOfFilledLabs = state.getNumberOfFilledLabs();
        this.courseSlotsSize = state.getNumberOfCourseSlots();
        this.labSlotsSize = state.getNumberOfLabSlots();
        this.depth = state.depth + 1;
        this.order = order;
        this.parent = state;

    }

    public ArrayList<State> getChildNodes(){
        return this.childNodes;
    }

    public void generateChildNodes(){
        this.childNodes = new ArrayList<State>();

        int j = 0;

        if(this.courses[this.courses.length-1] == 0 ){

            for(int i = 0; i < this.courseSlotsSize; i++){
                State state = new State(this, j++);

                state.assignSlotToCourse(i+1);

                this.childNodes.add(state);
            }
        }


        if(this.labs[this.labs.length-1] == 0){

            for(int i = 0; i < this.labSlotsSize; i++){
                State state = new State(this, j++);

                state.assignSlotToLab(i+1);

                this.childNodes.add(state);
            }
        }
    }

    public int getDepth(){
        return this.depth;
    }

    public int getPosition(){
        return this.order;
    }


    public State getParent(){
        return this.parent;
    }


    public int[] getLabs(){
        return this.labs;
    }

    public int[] getCourses(){
        return this.courses;
    }

    public int[] getCourseSlots(){
        return this.courseSlots;
    }

    public int[] getLabSlots(){
        return this.labSlots;
    }

    public int getCoursesSize(){
        return this.coursesSize;
    }

    public int getLabsSize(){
        return this.labsSize;
    }

    public int getNumberOfFilledLabs(){
        return this.numberOfFilledLabs;
    }

    public int getNumberOfFilledCourses(){
        return this.numberOfFilledCourses;
    }

    public int getNumberOfLabSlots(){
        return this.labSlotsSize;
    }

    public int getNumberOfCourseSlots(){
        return this.courseSlotsSize;
    }

    public int compareTo(State state){
        return state.getPosition()-this.getPosition();
    }


    // The following two functions are just for testing purposes to make sure the constr function is working properly

    public void assignSlotToCourse(int courseSlotNum){
        this.courses[this.numberOfFilledCourses] = courseSlotNum;
        this.numberOfFilledCourses++;
        this.courseSlots[courseSlotNum-1]++;
    }

    public void assignSlotToLab(int labSlotNum){

        this.labs[this.numberOfFilledLabs] = labSlotNum;
        this.numberOfFilledLabs++;
        this.labSlots[labSlotNum-1]++;
    }


}
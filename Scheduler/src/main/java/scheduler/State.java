package scheduler;


public class State {

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

    public void assignSlotToCourse(int courseSlotNum){
        this.courses[this.numberOfFilledCourses] = courseSlotNum;
        this.numberOfFilledCourses++;
        this.courseSlots[courseSlotNum]++;
    }

    public void assignSlotToLab(int labSlotNum){
        this.labs[this.numberOfFilledLabs] = labSlotNum;
        this.numberOfFilledLabs++;
        this.labSlots[labSlotNum]++;
    }


}
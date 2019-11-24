package scheduler;

import java.util.ArrayList;

public class Course extends SlotBooking{

    private String courseSection;
    private ArrayList<Lab> labs;
    
    public Course(String courseIdentifier, String courseSection){

        super(courseIdentifier);

        this.courseSection = courseSection;
        this.labs = new ArrayList<Lab>();
    }


    public String getCourseSection(){
        return this.courseSection;
    }

    public ArrayList<Lab> getLabs(){
        return this.labs;
    }

    public void addLab(Lab lab){
        this.labs.add(lab);
    }

}
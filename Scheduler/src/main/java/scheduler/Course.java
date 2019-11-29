package scheduler;

import java.util.ArrayList;

public class Course extends SlotBooking{

    private static int id = 1;

    private String courseSection;
    private ArrayList<Lab> labs;
    private boolean evening;
    
    public Course(String courseIdentifier, String courseSection){

        super(courseIdentifier, id++); 

        this.courseSection = courseSection;
        this.labs = new ArrayList<Lab>();



        if(Integer.parseInt(courseSection.split(" ")[1]) >= 9){
            this.evening = true;    
        }else{
            this.evening = false;
        }

    }

    public boolean isEvening(){
        return this.evening;
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
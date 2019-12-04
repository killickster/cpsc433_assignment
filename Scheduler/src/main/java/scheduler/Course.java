package scheduler;

import java.util.ArrayList;

public class Course extends SlotBooking{

    private static int id = 1;

    private String courseSection;
    private ArrayList<Lab> labs;
    private boolean evening;
    private boolean seniorCourse;
    private boolean isSenior;
    private boolean is313Quiz = false;
    private boolean is413Quiz = false;
    
    public Course(String courseIdentifier, String courseSection){

        super(courseIdentifier, id++); 

        this.courseSection = courseSection;
        this.labs = new ArrayList<Lab>();


        this.isSenior = courseIdentifier.split(" ")[1].charAt(0) == '5';

        if(Integer.parseInt(courseSection.split(" ")[1]) >= 9){
            this.evening = true;    
        }else{
            this.evening = false;
        }

        System.out.println(courseIdentifier.split(" ")[1]);

        this.is313Quiz = courseIdentifier.split(" ")[1].equals("813");
        this.is413Quiz = courseIdentifier.split(" ")[1].equals("913");

        System.out.println("quiz: " + this.is313Quiz);

    }

    public boolean isSenior(){
        return this.isSenior;
    }

    public boolean isEvening(){
        return this.evening;
    }

    public boolean is313Quiz(){
        return this.is313Quiz;
    }

    public boolean is413Quiz(){
        return this.is413Quiz;
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
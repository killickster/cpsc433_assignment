package scheduler;

import java.util.*;

public class Problem {

    private ArrayList<Slot> courseSlots;
    private ArrayList<Slot> labSlots;
    private ArrayList<Course> courses;
    private ArrayList<Lab> labs;
    private ArrayList<NotCompatible> notCompatible;


    public Problem(){
        this.courseSlots = new ArrayList<Slot>(); 
        this.labSlots = new ArrayList<Slot>();
        this.courses = new ArrayList<Course>();
        this.labs = new ArrayList<Lab>();
        this.notCompatible = new ArrayList<NotCompatible>();
    }


    public void addLabSlot(Slot slot){
        this.labSlots.add(slot);
    }

    public void addCourseSlot(Slot slot){
        this.courseSlots.add(slot);
    }

    public void addCourse(Course course){
        this.courses.add(course);
    }

    public void addLab(Lab lab){
        this.labs.add(lab);
    }

    public void addNotCompatible(NotCompatible notCompatible){
        this.notCompatible.add(notCompatible);
    }

    public ArrayList<Slot> getLabSlots(){
        return this.labSlots;
    }

    public ArrayList<Slot> getCourseSlots(){
        return this.courseSlots;
    }

    public ArrayList<Course> getCourses(){
        return this.courses;
    }

    public ArrayList<Lab> getLabs(){
        return this.labs;
    }

    public ArrayList<NotCompatible> getNotCompatible(){
        return this.notCompatible;
    }



    

}
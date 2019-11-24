package scheduler;

import java.util.*;

public class Problem {

    private ArrayList<Slot> courseSlots;
    private ArrayList<Slot> labSlots;
    private ArrayList<Course> courses;
    private ArrayList<Lab> labs;
    private ArrayList<PartialAssignment> partialAssignments;
    private ArrayList<NotCompatible> notCompatible;
    private ArrayList<Unwanted> unwanted;


    public Problem(){
        this.courseSlots = new ArrayList<Slot>(); 
        this.labSlots = new ArrayList<Slot>();
        this.courses = new ArrayList<Course>();
        this.labs = new ArrayList<Lab>();
        this.partialAssignments = new ArrayList<PartialAssignment>();
        this.notCompatible = new ArrayList<NotCompatible>();
        this.unwanted = new ArrayList<Unwanted>();

    }


    public Slot getLabSlot(String day, String startTime){
        Slot slot = null;

        for(Slot labSlot: this.labSlots){
            if(labSlot.getDay().equals(day) && labSlot.getStartTime().equals(startTime)){
                slot = labSlot;
            }
        }

        return slot;
    }

    public Slot getCourseSlot(String day, String startTime){
        Slot slot = null;

        for(Slot courseSlot: this.courseSlots){
            if(courseSlot.getDay().equals(day) && courseSlot.getStartTime().equals(startTime)){
                slot = courseSlot;
            }
        }

        return slot;
    }

    public Lab getLab(String courseIdentifier, String courseSection, String labType, String labSection){

        Lab selectedLab = null;
        for(Lab lab: this.labs){
            if(lab instanceof ExclusiveLab){
                ExclusiveLab exclusiveLab = (ExclusiveLab) lab;
                if(exclusiveLab.getCourseIdentifier().equals(courseIdentifier) && exclusiveLab.getCourseSection().equals(courseSection)
                && exclusiveLab.getLabType().equals(labType) && exclusiveLab.getLabSection().equals(labSection)){
                   selectedLab = lab;

                System.out.println("finding lab");
                }
            }   
        }

        return selectedLab;
    }

    public Lab getLab(String courseIdentifier, String labType, String labSection){

        Lab selectedLab = null;
        for(Lab lab: this.labs){
            if(!(lab instanceof ExclusiveLab)){
                if(lab.getCourseIdentifier().equals(courseIdentifier) && lab.getLabType().equals(labType) && lab.getLabSection().equals(labSection)){
                    selectedLab = lab;
                }
            }
        }

        return selectedLab;
    }

    public Course getCourse(String courseIdentifier, String courseSection){

        Course selectedCourse = null;
        for(Course course: this.courses){
            if(course.getCourseIdentifier().equals(courseIdentifier) && course.getCourseSection().equals(courseSection)){
                selectedCourse = course;
            }
        }
        return selectedCourse;
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

    public void addUnwanted(Unwanted unwanted){
        this.unwanted.add(unwanted);
    }


    public void addPartialAssignment(PartialAssignment partialAssignemnt){
        this.partialAssignments.add(partialAssignemnt);
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

    public ArrayList<Unwanted> getUwanted(){
        return this.unwanted;
    }


    public ArrayList<PartialAssignment> getPartialAssignemnts(){
        return this.partialAssignments;
    }

    public ArrayList<NotCompatible> getNotCompatible(){
        return this.notCompatible;
    }




    

}
package scheduler;

import java.util.*;

public class Problem {

    private ArrayList<Slot> courseSlots;
    private ArrayList<Slot> labSlots;
    private ArrayList<Course> courses;
    private ArrayList<Lab> labs;
    private ArrayList<PartialAssignment> partialAssignments;
    private ArrayList<NotCompatible> notCompatibles;
    private ArrayList<Unwanted> unwanted;


    public Problem(){
        this.courseSlots = new ArrayList<Slot>(); 
        this.labSlots = new ArrayList<Slot>();
        this.courses = new ArrayList<Course>();
        this.labs = new ArrayList<Lab>();
        this.partialAssignments = new ArrayList<PartialAssignment>();
        this.notCompatibles = new ArrayList<NotCompatible>();
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

    public void addConstraintsFor913and813(){

        ArrayList<NotCompatible> notCompatiblesToAdd = new ArrayList<NotCompatible>();
        for(Course course: this.courses){
            if(course.is313Quiz()){
                for(Slot slot: this.courseSlots){
                    if(slot.getDay().equals("TU") && slot.getBeginTime() == 1800){
                        this.partialAssignments.add(new PartialAssignment(course, slot));
                    }
                }
            

            for(Course c: this.courses){
                if(course.getId() != c.getId() && c.is313()){
                    notCompatiblesToAdd.add(new NotCompatible(course, c));
                    for(NotCompatible notCompatible: this.notCompatibles){
                        if(notCompatible.getBooking1().equals((SlotBooking) c)){
                            notCompatiblesToAdd.add(new NotCompatible(course, notCompatible.getBooking2()));
                        }
                        if(notCompatible.getBooking2().equals((SlotBooking) c)){
                            notCompatiblesToAdd.add(new NotCompatible(course, notCompatible.getBooking1()));
                        }
                    }
                }
            }
            for(Lab lab: this.labs){
                if(lab.is313()){
                    notCompatiblesToAdd.add(new NotCompatible(course, lab));

                    for(NotCompatible notCompatible: this.notCompatibles){
                        if(notCompatible.getBooking1().equals((SlotBooking)lab)){
                            notCompatiblesToAdd.add(new NotCompatible(course, notCompatible.getBooking2()));
                        }
                        if(notCompatible.getBooking2().equals((SlotBooking)lab)){
                            notCompatiblesToAdd.add(new NotCompatible(course, notCompatible.getBooking1()));
                        }
                    }
                }
            }
        }

        if(course.is413Quiz()){

            for(Slot slot: this.courseSlots){
                if(slot.getDay().equals("TU") && slot.getBeginTime() == 1800){
                    this.partialAssignments.add(new PartialAssignment(course, slot));
                }
            }

            for(Course c: this.courses){
                if(course.getId() != c.getId() && c.is413()){
                    notCompatiblesToAdd.add(new NotCompatible(course, c));
                    for(NotCompatible notCompatible: this.notCompatibles){
                        if(notCompatible.getBooking1().equals((SlotBooking) c)){
                            notCompatiblesToAdd.add(new NotCompatible(course, notCompatible.getBooking2()));
                        }
                        if(notCompatible.getBooking2().equals((SlotBooking) c)){
                            notCompatiblesToAdd.add(new NotCompatible(course, notCompatible.getBooking1()));
                        }
                    }
                }
            }
            for(Lab lab: this.labs){
                if(lab.is413()){
                    notCompatiblesToAdd.add(new NotCompatible(course, lab));

                    for(NotCompatible notCompatible: this.notCompatibles){
                        if(notCompatible.getBooking1().equals((SlotBooking)lab)){
                            notCompatiblesToAdd.add(new NotCompatible(course, notCompatible.getBooking2()));
                        }
                        if(notCompatible.getBooking2().equals((SlotBooking)lab)){
                            notCompatiblesToAdd.add(new NotCompatible(course, notCompatible.getBooking1()));
                        }
                    }
                }
            }

        }

    }

    this.notCompatibles.addAll(notCompatiblesToAdd);

    }

    /*
    public boolean containsPartialAssignemntForCourse(int courseId){

        for(PartialAssignment ps: this.partialAssignments){
            if(ps.getBooking() instanceof Course){
                if(ps.getBooking().getId() == courseId){
                    return true;
                }
            }
        }
    }
    */

    public void testForPartialAssignemnt(){

        for(PartialAssignment pa: this.partialAssignments){
            for(Course course: this.courses){
                if(pa.getBooking().equals(course)){
                    course.setHasPartialAssignment();
                }
            }
            for(Lab lab: this.labs){
                if(pa.getBooking().equals(lab)){
                    lab.setHasPartialAssignment();
                }
            }
        }

    }

    public void calculateNumberOfUncompatible(){
        for(Course course: this.courses){
            for(NotCompatible notCompatible: this.notCompatibles){
                if(notCompatible.getBooking1().equals((SlotBooking) course)){
                    course.notCompatibleIncrease();
                }
                if(notCompatible.getBooking2().equals((SlotBooking) course)){
                    course.notCompatibleIncrease();
                }
            }
        }

        for(Lab lab: this.labs){
            for(NotCompatible notCompatible: this.notCompatibles){
                if(notCompatible.getBooking1().equals((SlotBooking) lab)){
                    lab.notCompatibleIncrease();
                }
                if(notCompatible.getBooking2().equals((SlotBooking) lab)){
                    lab.notCompatibleIncrease();
                }
            }
        }
    }

    public void calculateNumberOfUnwanted(){
        for(Course course: this.courses){
            for(Unwanted unwanted: this.unwanted){
                if(unwanted.getBooking().equals(course)){
                    course.unwantedIncrease();
                }
            }
        }

        for(Lab lab: this.labs){
            for(Unwanted unwanted: this.unwanted){
                if(unwanted.getBooking().equals(lab)){
                    lab.unwantedIncrease();
                }
            }
        }
    }

    public void regenerateIds(){

        for(Course course: this.courses){
            course.calculatePriority();
            if(course.getCourseIdentifier().equals("CPSC 587")){
                System.out.println("587 priority: " + course.getPriority());
                System.out.println(course.getNumberOfUncompatible());
            }
        }

        for(Lab lab: this.labs){
            lab.calculatePriority();
        }


        Collections.sort(this.courses);
        int i = 1;
        for(Course course: this.courses){
            course.setId(i);
            i++;
        }

        Collections.sort(this.labs);
        i = 1;
        for(Lab lab: this.labs){
            lab.setId(i);
            i++;
        }


    }

    /*
    public boolean containsUnwantedCourse(int courseId){

        for(Unwanted unwanted: this.unwanted){
            if(unwanted.getBooking() instanceof Course){
                if(unwanted.getBooking().getId() == courseId){

                }
            }
        }
    }
*/



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
        this.notCompatibles.add(notCompatible);
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
        return this.notCompatibles;
    }




    

}
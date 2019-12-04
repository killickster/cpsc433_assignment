package scheduler;

import org.junit.Test;


import static org.junit.Assert.*;

public class Test413and313 {
    private Problem problem;
    private OTree otree;


    @Test public void setup(){


        this.problem = new Problem();

        Course course1 = new Course("CPSC 313", "LEC 01");
        Course course2 = new Course("CPSC 313", "LEC 02");
        Course course3 = new Course("CPSC 313", "LEC 03");
        Course course4 = new Course("CPSC 413", "LEC 01");
        Course course5 = new Course("CPSC 501", "LEC 01");
        Course course6 = new Course("CPSC 913", "LEC 01");

        this.problem.addCourse(course1);
        this.problem.addCourse(course2);
        this.problem.addCourse(course3);
        this.problem.addCourse(course4);
        this.problem.addCourse(course5);
        this.problem.addCourse(course6);

        Lab lab1 = new Lab("CPSC 313", "LAB", "01");
        Lab lab2 = new Lab("CPSC 313", "LAB", "02");
        Lab lab3 = new Lab("CPSC 313", "LAB", "03");
        Lab lab4 = new Lab("CPSC 413", "LAB", "01");

        course1.addLab(lab1);
        course2.addLab(lab2);
        course3.addLab(lab3);
        course4.addLab(lab4);

        problem.addLab(lab1);
        problem.addLab(lab2);
        problem.addLab(lab3);
        problem.addLab(lab4);

        LabSlot labSlot1 = new LabSlot("MO", "8:00", 3,0);
        LabSlot labSlot2 = new LabSlot("TU", "9:00", 3,0);

        problem.addLabSlot(labSlot1);
        problem.addLabSlot(labSlot2);

        CourseSlot courseSlot1 = new CourseSlot("MO", "8:00", 3, 0);
        CourseSlot courseSlot2 = new CourseSlot("MO", "10:00", 3,0);
        CourseSlot courseSlot3 = new CourseSlot("TU", "10:00", 3,0);
        CourseSlot courseSlot4 = new CourseSlot("TU", "18:00", 3, 0);
        

        problem.addCourseSlot(courseSlot1);
        problem.addCourseSlot(courseSlot2);
        problem.addCourseSlot(courseSlot3);
        problem.addCourseSlot(courseSlot4);

        NotCompatible notCompatible1 = new NotCompatible(course3, course5);
        NotCompatible notCompatible2 = new NotCompatible(course4, course5);

        problem.addNotCompatible(notCompatible1);
        problem.addNotCompatible(notCompatible2);

        problem.addConstraintsFor913and813();

        System.out.println("size of uncompatible: " + this.problem.getNotCompatible().size());

        for(NotCompatible nc: this.problem.getNotCompatible()){
            System.out.println("booking 1: " + nc.getBooking1().getCourseIdentifier() + " booking 2: " + nc.getBooking2().getCourseIdentifier());
        }


        assertEquals("should have 5 not compatible", 5, this.problem.getNotCompatible().size());
        assertEquals("should have 1 partial assignement",1, this.problem.getPartialAssignemnts().size());

        OTree otree = new OTree(problem);

        this.otree = otree;


    } 

}
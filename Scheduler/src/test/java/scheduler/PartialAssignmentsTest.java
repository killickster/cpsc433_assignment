package scheduler;

import org.junit.Test;


import static org.junit.Assert.*;
public class PartialAssignmentsTest{
    

    private Problem problem;
    private OTree otree;


    public void setup(){


        this.problem = new Problem();

        Course course1 = new Course("CPSC 433", "LEC 01");
        Course course2 = new Course("CPSC 411", "LEC 01");
        Course course3 = new Course("CPSC 313", "LEC 01");
        Course course4 = new Course("CPSC 413", "LEC 01");
        Course course5 = new Course("CPSC 501", "LEC 09");

        this.problem.addCourse(course1);
        this.problem.addCourse(course2);
        this.problem.addCourse(course3);
        this.problem.addCourse(course4);
        this.problem.addCourse(course5);

        Lab lab1 = new Lab("CPSC 433", "LAB", "01");
        Lab lab2 = new Lab("CPSC 411", "LAB", "01");
        Lab lab3 = new Lab("CPSC 313", "LAB", "01");
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

        PartialAssignment partialAssignment = new PartialAssignment(course1,courseSlot1);

        problem.addPartialAssignment(partialAssignment);

        OTree otree = new OTree(problem);

        this.otree = otree;

        assertEquals("Should have 4 course slots: ", 4, otree.getRootNode().getNumberOfCourseSlots());

        assertEquals("Should have 2 lab slots: ", 2, otree.getRootNode().getNumberOfLabSlots());

        assertEquals("Should have 5 courses: ", 5, otree.getRootNode().getCoursesSize());

        assertEquals("Should have 4 labs: ", 4, otree.getRootNode().getLabsSize());


    }

    @Test public void testPartialAssignemnts(){

        this.setup();

        this.otree.getRootNode().assignSlotToCourse(2);

        assertFalse("Should not be a valid state", this.otree.testPartialAssignemnt(this.otree.getRootNode()));
    




    }



}
package scheduler;

import org.junit.Test;

import static org.junit.Assert.*;
public class ConstraintFunctionTest {
    

    private Problem problem;


    public void setup(){


        this.problem = new Problem();

        Course course1 = new Course("CPSC 433", "LEC 01");
        Course course2 = new Course("CPSC 411", "LEC 01");
        Course course3 = new Course("CPSC 313", "LEC 01");
        Course course4 = new Course("CPSC 413", "LEC 01");

        this.problem.addCourse(course1);
        this.problem.addCourse(course2);
        this.problem.addCourse(course3);
        this.problem.addCourse(course4);

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

        problem.addCourseSlot(courseSlot1);
        problem.addCourseSlot(courseSlot2);
        problem.addCourseSlot(courseSlot3);

    }

    @Test public void testConstraintFunction1(){

        this.setup();
        testUnwantedFunction(this.problem);
    }

    @Test public void testConstraintFunction2(){
        this.setup();
        testNotCompatible(this.problem);
    }

    @Test public void testConstraintFunction3(){
        this.setup(); 
        testCourseLabTimeConflict(this.problem);
    }
    @Test public void testConstraintFunction4(){
        this.setup(); 
        testCourseMaxViolation(this.problem);
    }

    @Test public void testConstraintFunction5(){
        this.setup();
        testPartialAssignmentViolation(this.problem);
        
    }
    @Test public void testConstraintFunction6(){

        this.setup();
        testScenarioWhichShouldSatisfyConstr(this.problem);
    }


    public void testUnwantedFunction(Problem problem){

        Unwanted unwanted = new Unwanted(problem.getCourse("CPSC 433", "LEC 01"), problem.getCourseSlot("MO", "8:00"));

        problem.addUnwanted(unwanted);

        problem.getCourse("CPSC 433", "LEC 01").assignSlot(problem.getCourseSlot("MO", "8:00"));

        State state = new State(problem);

        assertFalse("unwanted should be false: ",state.constr());

    }

    public void testNotCompatible(Problem problem){

        SlotBooking booking1 = problem.getCourse("CPSC 433", "LEC 01");
        SlotBooking booking2 = problem.getCourse("CPSC 313", "LEC 01");

        NotCompatible notCompatible = new NotCompatible(booking1, booking2);

        problem.addNotCompatible(notCompatible);

        booking1.assignSlot(problem.getCourseSlot("MO", "8:00"));
        booking2.assignSlot(problem.getCourseSlot("MO", "8:00"));

        State state = new State(problem);

        assertFalse("This scenario should be not compatible", state.constr());
    }

    public void testCourseLabTimeConflict(Problem problem){


        problem.getCourse("CPSC 433", "LEC 01").assignSlot(problem.getCourseSlot("MO", "8:00"));

        problem.getCourse("CPSC 433", "LEC 01").getLabs().get(0).assignSlot(problem.getLabSlot("MO", "8:00"));

        State state = new State(problem);

        assertFalse("There is a time conflict between this course and one of its labs", state.constr());
    }

    public void testCourseMaxViolation(Problem problem){

        problem.getCourse("CPSC 433", "LEC 01").assignSlot(problem.getCourseSlot("MO", "8:00"));
        problem.getCourse("CPSC 313", "LEC 01").assignSlot(problem.getCourseSlot("MO", "8:00"));
        problem.getCourse("CPSC 411", "LEC 01").assignSlot(problem.getCourseSlot("MO", "8:00"));
        problem.getCourse("CPSC 413", "LEC 01").assignSlot(problem.getCourseSlot("MO", "8:00"));


        State state = new State(problem);

        assertFalse("Too many courses assigned to a slot: ", state.constr());

    }

    public void testPartialAssignmentViolation(Problem problem){

        PartialAssignment partialAssignment = new PartialAssignment(problem.getCourse("CPSC 433", "LEC 01"), problem.getCourseSlot("MO", "8:00"));

        problem.getCourse("CPSC 433", "LEC 01").assignSlot(problem.getCourseSlot("TU", "10:00"));

        problem.addPartialAssignment(partialAssignment);

        State state = new State(problem);

        assertFalse("This assignment goes against a required partial assignemnt", state.constr());
    }

    public void testScenarioWhichShouldSatisfyConstr(Problem problem){

        Unwanted unwanted = new Unwanted(problem.getCourse("CPSC 433", "LEC 01"), problem.getCourseSlot("MO", "8:00"));

        problem.addUnwanted(unwanted);

        problem.getCourse("CPSC 433", "LEC 01").assignSlot(problem.getCourseSlot("TU", "10:00"));

        State state = new State(problem);

        assertTrue("Should not violate constraints", state.constr());


    }

}
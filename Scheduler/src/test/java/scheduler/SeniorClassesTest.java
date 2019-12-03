package scheduler;

import org.junit.Test;
import static org.junit.Assert.*;

public class SeniorClassesTest {

    private Problem problem;
    private OTree otree;


    public void setup(){


        this.problem = new Problem();
        
        Course course1 = new Course("CPSC 585", "LEC 01");
        Course course2 = new Course("CPSC 587", "LEC 01");
        Course course3 = new Course("CPSC 589", "LEC 01");
        
        this.problem.addCourse(course1);
        this.problem.addCourse(course2);
        this.problem.addCourse(course3);

        CourseSlot courseSlot1 = new CourseSlot("MO", "8:00", 3, 0);
        CourseSlot courseSlot2 = new CourseSlot("TU", "8:00", 3, 0);
        CourseSlot courseSlot3 = new CourseSlot("MO", "8:00", 3, 0);
        
        problem.addCourseSlot(courseSlot1);
        problem.addCourseSlot(courseSlot2);
        problem.addCourseSlot(courseSlot3);
        
        PartialAssignment partialAssignment1 = new PartialAssignment(course1,courseSlot1);
        problem.addPartialAssignment(partialAssignment1);
        PartialAssignment partialAssignment2 = new PartialAssignment(course1,courseSlot1);
        problem.addPartialAssignment(partialAssignment2);
        PartialAssignment partialAssignment3 = new PartialAssignment(course1,courseSlot1);
        problem.addPartialAssignment(partialAssignment3);
        
        OTree otree = new OTree(problem);

        this.otree = otree;
    }

    @Test public void testSeniorClasses(){

        this.setup();

        otree.getRootNode().assignSlotToCourse(1);
        otree.getRootNode().assignSlotToCourse(2);
        otree.getRootNode().assignSlotToCourse(3);
        
        assertFalse("Should not be a valid sate", this.otree.test500LevelConflict(this.otree.getRootNode()));     
        
    }

}

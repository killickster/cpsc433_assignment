package scheduler;

import org.junit.Test;
import static org.junit.Assert.*;

public class Tuesdays11Test {
    private Problem problem;
    private OTree otree;


    public void setup(){


        this.problem = new Problem();
        
        Course course1 = new Course("CPSC 585", "LEC 01");
        
        this.problem.addCourse(course1);

        CourseSlot courseSlot1 = new CourseSlot("TU", "11:00", 3, 0);
        
        problem.addCourseSlot(courseSlot1);
        
        course1.assignSlot(courseSlot1);
        
        PartialAssignment partialAssignment = new PartialAssignment(course1,courseSlot1);

        problem.addPartialAssignment(partialAssignment);
        
        OTree otree = new OTree(problem);

        this.otree = otree;
    }

    @Test public void testTuesday11(){

        this.setup();
        
        otree.getRootNode().assignSlotToCourse(1);
        
        assertFalse("Should not be a valid sate", this.otree.testNoBookingOnTues11(this.otree.getRootNode()));  
        
    }

}

package scheduler;

public class CourseSlot extends Slot{
    
    public CourseSlot(String name, String startTime, Integer coursemax, Integer coursemin){
        super(name, startTime, coursemax, coursemin);
    }
}
package scheduler;

public class CourseSlot extends Slot{
    
    public CourseSlot(String day, String startTime, Integer coursemax, Integer coursemin){
        super(day, startTime, coursemax, coursemin);
    }
}
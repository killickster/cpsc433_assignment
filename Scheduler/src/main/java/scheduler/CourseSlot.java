package scheduler;

public class CourseSlot extends Slot{

    public static int ids = 0;
    
    public CourseSlot(String day, String startTime, Integer coursemax, Integer coursemin){
        super(day, startTime, coursemax, coursemin, ids++);

    }
}
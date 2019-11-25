package scheduler;

public class LabSlot extends Slot {

    public static int ids = 1;

    public LabSlot(String day, String startTime, Integer coursemax, Integer coursemin){
        super(day, startTime, coursemax, coursemin, ids++);
    }
}
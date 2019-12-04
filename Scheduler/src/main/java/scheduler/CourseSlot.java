package scheduler;

public class CourseSlot extends Slot{

    public static int ids = 1;
    
    public CourseSlot(String day, String startTime, Integer coursemax, Integer coursemin){
        super(day, startTime, coursemax, coursemin, ids++);

        Integer endTime;

        if(day.equals("TU") || day.equals("TH")){
            if(super.getBeginTime()%100 == 0){
                endTime = super.getBeginTime() + 130;
            }else{
                endTime = super.getBeginTime() + 170;
            }
        }else{

            endTime = super.getBeginTime() + 100;
        }

        super.setEndTime(endTime);



    }

}
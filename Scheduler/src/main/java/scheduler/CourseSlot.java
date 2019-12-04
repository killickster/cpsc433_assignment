package scheduler;

public class CourseSlot extends Slot{

    public static int ids = 1;
    private Integer endTime;
    
    public CourseSlot(String day, String startTime, Integer coursemax, Integer coursemin){
        super(day, startTime, coursemax, coursemin, ids++);


        if(day.equals("TU") || day.equals("TH")){
            if(super.getBeginTime()%100 == 0){
                this.endTime = super.getBeginTime() + 130;
            }else{
                this.endTime = super.getBeginTime() + 170;
            }
        }else{

            this.endTime = super.getBeginTime() + 100;
        }

        System.out.println(this.getBeginTime());

        System.out.println(this.endTime);

    }

    public Integer getEndTime(){
        return this.endTime;
    }
}
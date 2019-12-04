package scheduler;

public class LabSlot extends Slot {

    public static int ids = 1;
    private Integer endTime;

    public LabSlot(String day, String startTime, Integer coursemax, Integer coursemin){
        super(day, startTime, coursemax, coursemin, ids++);


        if(day.equals("FR")){
            this.endTime = super.getBeginTime() + 200;
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
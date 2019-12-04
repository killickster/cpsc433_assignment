package scheduler;

public class Slot {

    private String day;
    private String startTime;
    private Integer beginTime;
    private Integer endTime;
    private Integer coursemax;
    private Integer coursemin;
    private Integer numberOfCoursesAssigned;
    private Integer slotNumber;
    private Integer id;
    private boolean evening;


    public Slot(){

    }

    public Slot(String day, String startTime, Integer coursemax, Integer coursemin, int id){
        this.day = day;
        this.startTime = startTime;
        this.coursemax = coursemax;
        this.coursemin = coursemin;
        this.numberOfCoursesAssigned = 0;
        this.id = id;

        String[] startTimePieces = startTime.split(":");

        this.beginTime = Integer.parseInt(startTimePieces[0])*100+Integer.parseInt(startTimePieces[1]);

        if(this.beginTime >= 1800){
            this.evening = true;
        }else{
            this.evening = false;
        }

        System.out.println(this.evening);

    }

    public Integer getBeginTime(){
        return this.beginTime;
    }

    public Integer getEndTime(){
        return this.endTime;
    }

    public boolean timeConflict(Slot slot){
        if(slot.getDay().equals(this.day) && ((this.beginTime >= slot.getBeginTime() && this.beginTime < slot.getEndTime()) || (this.endTime <= slot.getEndTime() && this.endTime > slot.getBeginTime()))){
            return true;
        }

        return false;
    }

    public void setEndTime(Integer endTime){
        this.endTime = endTime;
    }

    public int getId(){
        return this.id;
    }

    public boolean isEvening(){
        return evening;
    }

    public void setSlotNumber(Integer i){
        this.slotNumber = i;
    }

    public Integer getSlotNumber(){
        return this.slotNumber;
    }

    public String getDay(){
        return this.day;
    }

    public String getStartTime(){
        return this.startTime;
    }

    public Integer getCourseMax(){
        return this.coursemax;
    }

    public Integer getCourseMin(){
        return this.coursemin;
    }

    public void setDay(String day){
        this.day = day;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setCourseMax(Integer courseMax){
        this.coursemax = courseMax;
    }

    public void setCourseMin(Integer courseMin){
        this.coursemin = coursemin;
    }

    public Integer getNumberOfCoursesAssigned(){
        return numberOfCoursesAssigned;
    }

    public void incrementNumberOfCoursesAssigned(){
        this.numberOfCoursesAssigned+=1;
    }





    
}
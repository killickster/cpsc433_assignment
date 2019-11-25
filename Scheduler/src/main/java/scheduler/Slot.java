package scheduler;

public abstract class Slot {

    private String day;
    private String startTime;
    private Integer coursemax;
    private Integer coursemin;
    private Integer numberOfCoursesAssigned;
    private Integer slotNumber;
    private Integer id;


    public Slot(String day, String startTime, Integer coursemax, Integer coursemin, int id){
        this.day = day;
        this.startTime = startTime;
        this.coursemax = coursemax;
        this.coursemin = coursemin;
        this.numberOfCoursesAssigned = 0;
        this.id = id;
    }

    public int getId(){
        return this.id;
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
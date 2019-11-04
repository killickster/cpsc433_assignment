package scheduler;

public abstract class Slot {

    private String day;
    private String startTime;
    private int coursemax;
    private int coursemin;


    public Slot(String day, String startTime, int coursemax, int coursemin){
        this.day = day;
        this.startTime = startTime;
        this.coursemax = coursemax;
        this.coursemin = coursemin;
    }

    public String getDay(){
        return this.day;
    }

    public String getStartTime(){
        return this.startTime;
    }

    public int getCourseMax(){
        return this.coursemax;
    }

    public int getCourseMin(){
        return this.coursemin;
    }

    public void setDay(String day){
        this.day = day;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setCourseMax(int courseMax){
        this.coursemax = courseMax;
    }

    public void setCourseMin(int courseMin){
        this.coursemin = coursemin;
    }



    
}
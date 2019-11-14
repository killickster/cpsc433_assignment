package scheduler;

/**
 * Abstract class extended by Course and Lab classes
 */

public abstract class SlotBooking {

    private String courseName;
    private String courseNumber;

    public SlotBooking(String courseName, String courseNumber){

        this.courseName = courseName;
        this.courseNumber = courseNumber;

    }

    public String getCourseName(){
        return this.courseName;
    }

    public String getCourseNumber(){
        return this.courseNumber;
    }


}
package scheduler;

public abstract class RoomBooking {

    private String courseName;
    private String courseNumber;

    public RoomBooking(String courseName, String courseNumber){

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
package scheduler;

public class Course extends SlotBooking{

    private String courseSection;
    
    public Course(String courseIdentifier, String courseSection){

        super(courseIdentifier);

        this.courseSection = courseSection;
    }


    public String getCourseSection(){
        return this.courseSection;
    }

}
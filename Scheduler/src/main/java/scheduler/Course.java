package scheduler;

public class Course extends SlotBooking{

    private String format;
    private String section;
    
    public Course(String abbreviation, String courseNumber, String format, String section){

        super(abbreviation, courseNumber);
        this.format = format;
        this.section = section;

    }

    public String getFormat(){
        return this.format;
    }

    public String getSection(){
        return this.section;
    }
}
package scheduler;

public class Lab extends SlotBooking{


    private String courseFormat;
    private String courseSection;
    private String labFormat;
    private String labSection;


    public Lab(String courseAbbreviation, String courseNumber, String labFormat, String labSection){

        super(courseAbbreviation, courseNumber);

        this.labFormat = labFormat;
        this.labSection = labSection;

    }

    public void setCourseFormat(String courseFormat){
        this.courseFormat = courseFormat;
    }

    public void setCourseSection(String courseSection){
        this.courseSection = courseSection;
    }

    public String getCourseFormat(){
        return this.courseFormat;
    }

    public String getCourseSection(){
        return this.courseSection;
    }

    public String getLabFormat(){
        return this.labFormat;
    }

    public String getLabSection(){
        return this.labSection;
    }
}
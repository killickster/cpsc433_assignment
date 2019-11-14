package scheduler;

public class Lab{


    private String courseAbbreviation;
    private String courseNumber;
    private String courseFormat;
    private String courseSection;
    private String labFormat;
    private String labSection;


    public Lab(String courseAbbreviation, String courseNumber, String labFormat, String labSection){

        this.courseAbbreviation = courseAbbreviation;
        this.courseNumber = courseNumber;
        this.labFormat = labFormat;
        this.labSection = labSection;

    }

    public void setCourseFormat(String courseFormat){
        this.courseFormat = courseFormat;
    }

    public void setCourseSection(String courseSection){
        this.courseSection = courseSection;
    }
}
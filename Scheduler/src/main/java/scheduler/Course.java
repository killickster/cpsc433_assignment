package scheduler;

public class Course {

    private String abbreviation;
    private String courseNumber;
    private String format;
    private String section;
    
    public Course(String abbreviation, String courseNumber, String format, String section){

        this.abbreviation = abbreviation;
        this.courseNumber = courseNumber;
        this.format = format;
        this.section = section;

    }

    public String getAbbreviation(){
        return this.abbreviation;
    }


    public String getCourseNumber(){
        return this.courseNumber;
    }

    public String getFormat(){
        return this.format;
    }

    public String getSection(){
        return this.section;
    }
}
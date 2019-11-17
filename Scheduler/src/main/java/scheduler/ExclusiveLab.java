package scheduler;

/**
 * Create an instance of this class when labs are only open to certiain sections
 */

public class ExclusiveLab extends Lab {

    private String courseSection;

    public ExclusiveLab(String courseIdentifier, String courseSection, String labType, String labSection){

        super(courseIdentifier, labType, labSection);
        this.courseSection = courseSection;

    }

    public String getCourseSection(){
        return courseSection;
    }


}
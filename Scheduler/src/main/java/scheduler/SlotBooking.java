package scheduler;

/**
 * Abstract class extended by Course and Lab classes
 */

public abstract class SlotBooking {

    String courseIdentifier;

    public SlotBooking(String courseIdentifier){

        this.courseIdentifier = courseIdentifier;

    }

    public String getCourseIdentifier(){
        return courseIdentifier;
    }

}
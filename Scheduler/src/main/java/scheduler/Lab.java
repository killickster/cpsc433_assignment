package scheduler;

public class Lab extends SlotBooking{


    private String courseId;
    private String labType;
    private String labSection;


    public Lab(String courseIdentifier, String labType, String labSection){

        super(courseIdentifier);
        this.labType = labType;
        this.labSection = labSection;

    }


    public String getLabType(){
        return this.labType;
    }

    public String getLabSection(){
        return this.labSection;
    }
}
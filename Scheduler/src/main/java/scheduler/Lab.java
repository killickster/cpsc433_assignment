package scheduler;

public class Lab extends SlotBooking{

    public static int id = 0;

    private String courseId;
    private String labType;
    private String labSection;


    public Lab(String courseIdentifier, String labType, String labSection){

        super(courseIdentifier, id++);
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
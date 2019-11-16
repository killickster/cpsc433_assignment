package scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private Problem problem;

    //Regular expressions to parse text file
    private static final Pattern slotPattern = Pattern.compile("([A-Z][A-Z]),\\s*(\\d{1,2}:\\d\\d),\\s*(\\d),\\s*(\\d)");
    private static final Pattern coursePattern = Pattern.compile("([A-Z]{4})\\s*(\\d{3})\\s*([A-Z]{3})\\s*(\\d{2})");
    private static final Pattern labPattern1 = Pattern.compile("([A-Z]{4})\\s*(\\d{3})\\s*([A-Z]{3})\\s*(\\d{2})\\s*([A-Z]{3})\\s*(\\d{2})");
    private static final Pattern labPattern2 = Pattern.compile("([A-Z]{4})\\s*(\\d{3})\\s*([A-Z]{3})\\s*(\\d{2})");
    private static final Pattern timePattern = Pattern.compile("([A-Z]{2}),\\s*(\\d{1,2}:\\d{2})");
    private static final Pattern preferencePattern1 = Pattern.compile("([A-Z]{2}),\\s*(\\d{1,2}:\\d{2}),\\s*([A-Z]{4})\\s*(\\d{3})\\s*([A-Z]{3})\\s*(\\d{2}),\\s*(\\d{1,2})");
    private static final Pattern preferencePattern2 = Pattern.compile("([A-Z]{2}),\\s*(\\d{1,2}:\\d{2}),\\s*([A-Z]{4})\\s*(\\d{3})\\s*([A-Z]{3})\\s*(\\d{2})\\s*([A-Z]{3})\\s*(\\d{2}),\\s*(\\d{1,2})");
    private static final Pattern partialAssignmentCourses = Pattern.compile("([A-Z]{4})\\s*(\\d{3})\\s*([A-Z]{3})\\s*(\\d{2}),\\s*([A-Z]{2}),\\s*(\\d{1,2}:\\d{2})");
    private static final Pattern partialAssignmentLabs = Pattern.compile("([A-Z]{4})\\s*(\\d{3})\\s*([A-Z]{3})\\s*(\\d{2})\\s*([A-Z]{3})\\s*(\\d{2}),\\s*([A-Z]{2}),\\s*(\\d{1,2}:\\d{2})");




    public Parser(){

        problem = new Problem();


    }

    public Problem getProblem(){
        return this.problem;
    }


    public void parseFile(String fileName) throws IOException{

        File file = new File(fileName);

        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;

        while((line = reader.readLine()) != null){
            String trimmedLine = line.trim();

            switch(trimmedLine){
                case "Course slots:":
                    parseCourseSlots(reader);
                    break;
                case "Lab slots:":
                    parseLabSlots(reader);
                    break;
                case "Courses:":
                    parseCourses(reader);
                    break;
                case "Labs:":
                    parseLabs(reader);
                    break;
                case "Not compatible:":
                    parseNotCompatible(reader);
                    break;
                case "Unwanted:":
                    parseUnwanted(reader);
                    break;
                case "Preferences:":
                    parsePreferences(reader);
                    break;
                case "Pair:":
                    parsePairs(reader);
                    break;
                case "Partial assignments:":
                    parsePartialAssignments(reader);
                    break;
                default:
                    System.out.println(trimmedLine);
            }
        }

    }

    public void parseCourseSlots(BufferedReader reader) throws IOException{

        String line;

        while((line = reader.readLine()) != null){

            String trimmedLine = line.trim();

            //If the line is empty 
            if(trimmedLine.length() == 0){ 
                return;
            }

            System.out.println("Course Slot:" + trimmedLine);

            Matcher regexMatcher = this.slotPattern.matcher(trimmedLine);

            regexMatcher.find();

            if(regexMatcher.group().length() != 0){

                String day = regexMatcher.group(1);
                String time = regexMatcher.group(2);
                String coursemax = regexMatcher.group(3);
                String coursemin = regexMatcher.group(4);

                Slot courseSlot = new CourseSlot(day, time, Integer.parseInt(coursemax), Integer.parseInt(coursemin));

                this.problem.addCourseSlot(courseSlot);
            }

            


        }
    }

    public void parseLabSlots(BufferedReader reader) throws IOException{
    
        String line;

        while((line = reader.readLine()) != null){

            String trimmedLine = line.trim();

            //If the line is empty 
            if(trimmedLine.length() == 0){ 
                return;
            }

            System.out.println("Lab Slot: " + trimmedLine);

            Matcher regexMatcher = this.slotPattern.matcher(trimmedLine);

            regexMatcher.find();

            if(regexMatcher.group().length() != 0){

                String day = regexMatcher.group(1);
                String time = regexMatcher.group(2);
                String coursemax = regexMatcher.group(3);
                String coursemin = regexMatcher.group(4);

                Slot labSlot = new LabSlot(day, time, Integer.parseInt(coursemax), Integer.parseInt(coursemin));

                this.problem.addLabSlot(labSlot);
            }

        }
    }


    public void parseCourses(BufferedReader reader) throws IOException{
    
        String line;

        while((line = reader.readLine()) != null){

            String trimmedLine = line.trim();

            //If the line is empty 
            if(trimmedLine.length() == 0){ 
                return;
            }

            System.out.println("Course: " + trimmedLine);

            Matcher regexMatcher = Parser.coursePattern.matcher(trimmedLine);

            regexMatcher.find();

            if(regexMatcher.group().length() != 0){

                String courseAbbreviation = regexMatcher.group(1);
                String courseNumber = regexMatcher.group(2);
                String courseFormat = regexMatcher.group(3);
                String courseSection = regexMatcher.group(4);

                Course course = new Course(courseAbbreviation, courseNumber, courseFormat, courseSection);

                this.problem.addCourse(course);
            }

        }
    }



    public void parseLabs(BufferedReader reader) throws IOException{
    
        String line;

        while((line = reader.readLine()) != null){

            String trimmedLine = line.trim();

            //If the line is empty 
            if(trimmedLine.length() == 0){ 
                return;
            }

            System.out.println("Lab: " + trimmedLine);

            Matcher regexMatcher1 = Parser.labPattern1.matcher(trimmedLine);
            Matcher regexMatcher2 = Parser.labPattern2.matcher(trimmedLine);

            regexMatcher1.find();
            regexMatcher2.find();


            if(regexMatcher1.matches()){ 

                String courseAbbreviation = regexMatcher1.group(1);
                String courseNumber = regexMatcher1.group(2);
                String courseFormat = regexMatcher1.group(3);
                String courseSection = regexMatcher1.group(4);
                String labType = regexMatcher1.group(5);
                String labSection = regexMatcher1.group(6);

                Lab lab = new Lab(courseAbbreviation, courseNumber, labType, labSection);

                lab.setCourseFormat(courseFormat);
                lab.setCourseSection(courseSection);

                this.problem.addLab(lab);


            }else if(regexMatcher2.matches()){ 

                String courseAbbreviation = regexMatcher2.group(1);
                String courseNumber = regexMatcher2.group(2);
                String labType = regexMatcher2.group(3);
                String labSection = regexMatcher2.group(4);

                Lab lab = new Lab(courseAbbreviation, courseNumber, labType, labSection);

                lab.setCourseFormat(null);
                lab.setCourseSection(null);

                this.problem.addLab(lab);
            }
        }
    }

    public void parseNotCompatible(BufferedReader reader) throws IOException{
    
        String line;

        while((line = reader.readLine()) != null){

            String trimmedLine = line.trim();

            //If the line is empty 
            if(trimmedLine.length() == 0){ 
                return;
            }


            Matcher regexMatcherLabs = Parser.labPattern1.matcher(trimmedLine);
            Matcher regexMatcherCourses = Parser.coursePattern.matcher(trimmedLine);


            ArrayList<SlotBooking> bookings = new ArrayList<SlotBooking>();


            while(regexMatcherLabs.find()){

                String courseAbbreviation = regexMatcherLabs.group(1);
                String courseNumber = regexMatcherLabs.group(2);
                String courseFormat = regexMatcherLabs.group(3);
                String courseSection = regexMatcherLabs.group(4);
                String labType = regexMatcherLabs.group(5);
                String labSection = regexMatcherLabs.group(6);




                for(Lab lab: problem.getLabs()){

                    if(lab.getCourseName().equals(courseAbbreviation) && lab.getCourseNumber().equals(courseNumber)
                        && lab.getCourseFormat().equals(courseFormat) && lab.getCourseSection().equals(courseSection)
                        && lab.getLabFormat().equals(labType) && lab.getLabSection().equals(labSection)){

                            bookings.add(lab);
                }
                
            }

        }


            

            while(regexMatcherCourses.find()){

                String courseAbbreviation = regexMatcherCourses.group(1);
                String courseNumber = regexMatcherCourses.group(2);
                String format = regexMatcherCourses.group(3);


                if(format.equals("TUT")){

                    String labFormat = format;
                    String labSection = regexMatcherCourses.group(4);

                    for(Lab lab: problem.getLabs()){
                        if(lab.getCourseName().equals(courseAbbreviation) && lab.getCourseNumber().equals(courseNumber)
                        && lab.getLabFormat().equals(labFormat) && lab.getLabSection().equals(labSection)){
    
                            if(bookings.size() < 2){
                                bookings.add(lab);
                            }
                   
                        }
                    }




                }else{
                    String courseSection = regexMatcherCourses.group(4);
                    String courseFormat = format;

                    for(Course course: problem.getCourses()){
                        if(course.getCourseName().equals(courseAbbreviation) && course.getCourseNumber().equals(courseNumber)
                        && course.getFormat().equals(courseFormat) && course.getSection().equals(courseSection)){
    
                            if(bookings.size() < 2){
    
                                bookings.add(course);

                            }
    
    
                        }
                    }

                }

            }



            if(bookings.size() == 2){

                NotCompatible notCompatible = new NotCompatible(bookings.get(0), bookings.get(1));


                this.problem.addNotCompatible(notCompatible);
            }

    }
}

    public void parseUnwanted(BufferedReader reader) throws IOException{
    
        String line;

        while((line = reader.readLine()) != null){

            String trimmedLine = line.trim();

            //If the line is empty 
            if(trimmedLine.length() == 0){ 
                return;
            }

            Matcher regexMatcherCourses = Parser.coursePattern.matcher(trimmedLine);
            Matcher regexMatcherLabs1 = Parser.labPattern1.matcher(trimmedLine);
            Matcher regexMatcherTime = Parser.timePattern.matcher(trimmedLine);

            regexMatcherTime.find();

            String day = regexMatcherTime.group(1);
            String time = regexMatcherTime.group(2);

            Unwanted unwanted = null;
            
            if(regexMatcherLabs1.find()){

                String courseAbbreviation = regexMatcherLabs1.group(1);
                String courseNumber = regexMatcherLabs1.group(2);
                String courseFormat = regexMatcherLabs1.group(3);
                String courseSection = regexMatcherLabs1.group(4);
                String labType = regexMatcherLabs1.group(5);
                String labSection = regexMatcherLabs1.group(6);

                SlotBooking selectedBooking;

                System.out.println("lab1");

                for(Lab lab: problem.getLabs()){

                    if(lab.getCourseName().equals(courseAbbreviation) && lab.getCourseNumber().equals(courseNumber)
                        && lab.getCourseFormat().equals(courseFormat) && lab.getCourseSection().equals(courseSection)
                        && lab.getLabFormat().equals(labType) && lab.getLabSection().equals(labSection)){
                            selectedBooking = lab;

                        for(Slot labSlot: problem.getLabSlots()){
                            if(labSlot.getDay().equals(day) && labSlot.getStartTime().equals(time)){
                                unwanted = new Unwanted(selectedBooking, labSlot);
                            }
                        }
                            
                }



            }
        }


        else if(regexMatcherCourses.find()){

            String courseAbbreviation = regexMatcherCourses.group(1);
            String courseNumber = regexMatcherCourses.group(2);
            String format = regexMatcherCourses.group(3);

            System.out.println("Course");

            if(format.equals("TUT")){

                String labFormat = format;
                String labSection = regexMatcherCourses.group(4);

                for(Lab lab: problem.getLabs()){
                    if(lab.getCourseName().equals(courseAbbreviation) && lab.getCourseNumber().equals(courseNumber)
                    && lab.getLabFormat().equals(labFormat) && lab.getLabSection().equals(labSection)){

                    
                        SlotBooking selectedBooking = lab;
    
    
                        for(Slot courseSlot: problem.getCourseSlots()){
                            if(courseSlot.getDay().equals(day) && courseSlot.getStartTime().equals(time)){
    
                                unwanted = new Unwanted(selectedBooking, courseSlot);
    
                            }
                        }
               
                    }
                }

            }else{
                String courseFormat = format;
                String courseSection = regexMatcherCourses.group(4);

                for(Course course: problem.getCourses()){
                    if(course.getCourseName().equals(courseAbbreviation) && course.getCourseNumber().equals(courseNumber)
                    && course.getFormat().equals(courseFormat) && course.getSection().equals(courseSection)){
                       
                        SlotBooking selectedBooking = course;
    
    
                        for(Slot courseSlot: problem.getCourseSlots()){
                            if(courseSlot.getDay().equals(day) && courseSlot.getStartTime().equals(time)){
    
                                unwanted = new Unwanted(selectedBooking, courseSlot);
    
                            }
                        } 
    
                    }
                }
            }

        }


            this.problem.addUnwanted(unwanted);
            System.out.println("Unwanted: " + trimmedLine);

        
        }  
      }

    public void parsePreferences(BufferedReader reader) throws IOException{
    
        String line;

        while((line = reader.readLine()) != null){

            String trimmedLine = line.trim();

            //If the line is empty 
            if(trimmedLine.length() == 0){ 
                return;
            }

            Preference preference = null;


            Matcher matcher1 = Parser.preferencePattern1.matcher(trimmedLine);
            Matcher matcher2 = Parser.preferencePattern2.matcher(trimmedLine);

            if(matcher1.find()){
                String day = matcher1.group(1);
                String startTime = matcher1.group(2);
                String courseName = matcher1.group(3);
                String courseNumber = matcher1.group(4);
                String format = matcher1.group(5);

                if(format.equals("TUT")){
                    String labFormat = format;
                    String labSection = matcher1.group(6);
                    String weight = matcher1.group(7);

                    for(Lab lab: problem.getLabs()){
                        if(lab.getCourseName().equals(courseName) && lab.getCourseNumber().equals(courseNumber)
                            && lab.getLabFormat().equals(labFormat) && lab.getLabSection().equals(labSection)){

                                for(Slot slot: problem.getLabSlots()){
                                    if(slot.getDay().equals(day) && slot.getStartTime().equals(startTime)){
                                       preference = new Preference(slot, lab, Integer.parseInt(weight)); 
                                       this.problem.addPreference(preference);
                                    }
                                }

                    }
                }
            }else{
                    String courseFormat = format;
                    String courseSection = matcher1.group(6);
                    String weight = matcher1.group(7);

                    for(Course course: problem.getCourses()){
                        if(course.getCourseName().equals(courseName) && course.getCourseNumber().equals(courseNumber)
                            && course.getFormat().equals(courseFormat) && course.getSection().equals(courseSection)){ 


                                for(Slot slot: problem.getCourseSlots()){
                                    if(slot.getDay().equals(day) && slot.getStartTime().equals(startTime)){
                                       preference = new Preference(slot, course, Integer.parseInt(weight)); 
                                       this.problem.addPreference(preference);
                                    }
                                }
                                
                    }

                }

        }
    }else if(matcher2.find()){


            String day = matcher2.group(1);
            String startTime = matcher2.group(2);
            String courseName = matcher2.group(3);
            String courseNumber = matcher2.group(4);
            String courseFormat = matcher2.group(5);
            String courseSection = matcher2.group(6);
            String labFomrat = matcher2.group(7);
            String labSection = matcher2.group(8);
            String weight = matcher2.group(9);

            for(Lab lab: problem.getLabs()){

                if(lab.getCourseName().equals(courseName) && lab.getCourseNumber().equals(courseNumber)
                    && lab.getCourseFormat().equals(courseFormat) && lab.getCourseSection().equals(courseSection)
                    && lab.getLabFormat().equals(labFomrat) && lab.getLabSection().equals(labSection)){

                    for(Slot labSlot: problem.getLabSlots()){
                        if(labSlot.getDay().equals(day) && labSlot.getStartTime().equals(startTime)){
                                preference = new Preference(labSlot, lab, Integer.parseInt(weight)); 
                                this.problem.addPreference(preference);

                        }
                    }
                        
            }

        }




            System.out.println("Preference: " + trimmedLine);

            if(preference == null){
                System.out.println("Either the lab or the slot does not exist");
            }





    }

}
}



    public void parsePairs(BufferedReader reader) throws IOException{
    
        String line;

        while((line = reader.readLine()) != null){

            String trimmedLine = line.trim();

            //If the line is empty 
            if(trimmedLine.length() == 0){ 
                return;
            }

            Matcher matcherLab = Parser.labPattern1.matcher(trimmedLine);
            Matcher matcherCourse = Parser.coursePattern.matcher(trimmedLine);

            ArrayList<SlotBooking> booking = new ArrayList<SlotBooking>();

            while(matcherLab.find()){
            
                
                String courseName =  matcherLab.group(1);
                String courseNumber = matcherLab.group(2);
                String courseFormat = matcherLab.group(3);
                String courseSection = matcherLab.group(4);
                String labFormat = matcherLab.group(5);
                String labSection = matcherLab.group(6);

                for(Lab lab: this.problem.getLabs()){

                    if(lab.getCourseName().equals(courseName) && lab.getCourseNumber().equals(courseNumber)
                        && lab.getCourseFormat().equals(courseFormat) && lab.getCourseSection().equals(courseSection)
                        && lab.getLabFormat().equals(labFormat) && lab.getLabSection().equals(labSection)){

                            booking.add(lab);

                }

            }

        }

        while(matcherCourse.find()){

            String courseName = matcherCourse.group(1);
            String courseNumber = matcherCourse.group(2);
            String format = matcherCourse.group(3);

            if(format.equals("TUT")){
                String labFormat = format;
                String labSection = matcherCourse.group(4);

                for(Lab lab: problem.getLabs()){
                    if(lab.getCourseName().equals(courseName) && lab.getCourseNumber().equals(courseNumber)
                        && lab.getLabFormat().equals(labFormat) && lab.getLabSection().equals(labSection)){

                            if(booking.size()< 2){
                                booking.add(lab);
                            }


                }
            }
        }else{
                String courseFormat = format;
                String courseSection = matcherCourse.group(4);

                for(Course course: problem.getCourses()){
                    if(course.getCourseName().equals(courseName) && course.getCourseNumber().equals(courseNumber)
                        && course.getFormat().equals(courseFormat) && course.getSection().equals(courseSection)){ 

                            if(booking.size()< 2){
                                booking.add(course);
                            }
                            
                }

            }

    }

      }

      this.problem.addPair(new Pair(booking.get(0), booking.get(1)));

        System.out.println("Pair: " + trimmedLine);
    }
}


    public void parsePartialAssignments(BufferedReader reader) throws IOException{
    
        String line;

        while((line = reader.readLine()) != null){

            String trimmedLine = line.trim();

            //If the line is empty 
            if(trimmedLine.length() == 0){ 
                return;
            }

            Matcher matcherCourse = Parser.partialAssignmentCourses.matcher(trimmedLine);
            Matcher matcherLab = Parser.partialAssignmentLabs.matcher(trimmedLine);


            if(matcherLab.find()){

                System.out.println("yes1");

                String courseName =  matcherLab.group(1);
                String courseNumber = matcherLab.group(2);
                String courseFormat = matcherLab.group(3);
                String courseSection = matcherLab.group(4);
                String labFormat = matcherLab.group(5);
                String labSection = matcherLab.group(6);
                String day = matcherLab.group(7);
                String startTime = matcherLab.group(8);

                for(Lab lab: this.problem.getLabs()){

                    if(lab.getCourseName().equals(courseName) && lab.getCourseNumber().equals(courseNumber)
                        && lab.getCourseFormat().equals(courseFormat) && lab.getCourseSection().equals(courseSection)
                        && lab.getLabFormat().equals(labFormat) && lab.getLabSection().equals(labSection)){

                            for(Slot labSlot: problem.getLabSlots()){
                                if(labSlot.getDay().equals(day) && labSlot.getStartTime().equals(startTime)){

                                    this.problem.addPartialAssignment(new PartialAssignment(lab, labSlot));
        
                                }
                            } 

                }

            }



            }else if(matcherCourse.find()){


                System.out.println("yes");

                String courseName = matcherCourse.group(1);
                String courseNumber = matcherCourse.group(2);
                String format = matcherCourse.group(3);
    
                if(format.equals("TUT")){
                    String labFormat = format;
                    String labSection = matcherCourse.group(4);
                    String day = matcherCourse.group(5);
                    String startTime = matcherCourse.group(6);
    
                    for(Lab lab: problem.getLabs()){
                        if(lab.getCourseName().equals(courseName) && lab.getCourseNumber().equals(courseNumber)
                            && lab.getLabFormat().equals(labFormat) && lab.getLabSection().equals(labSection)){

                                for(Slot labSlot: problem.getLabSlots()){
                                    if(labSlot.getDay().equals(day) && labSlot.getStartTime().equals(startTime)){
    
                                        this.problem.addPartialAssignment(new PartialAssignment(lab, labSlot));
                                    }
                                } 

    
                    }
                }
            }else{
                    String courseFormat = format;
                    String courseSection = matcherCourse.group(4);
                    String day = matcherCourse.group(5);
                    String startTime = matcherCourse.group(6);
    
                    for(Course course: problem.getCourses()){
                        if(course.getCourseName().equals(courseName) && course.getCourseNumber().equals(courseNumber)
                            && course.getFormat().equals(courseFormat) && course.getSection().equals(courseSection)){ 

                                System.out.println("yes");

                                for(Slot courseSlot: problem.getCourseSlots()){
                                    if(courseSlot.getDay().equals(day) && courseSlot.getStartTime().equals(startTime)){

                                        this.problem.addPartialAssignment(new PartialAssignment(course, courseSlot));

                                    }
                                }
    
                                
                    }
    
                }

                
            }
        }


            System.out.println("Partial Assignment: " + trimmedLine);

        }

        System.out.println("Number of course slots: "+this.problem.getCourseSlots().size());
        System.out.println("Number of lab slots: " +this.problem.getLabSlots().size());
        System.out.println("Number of courses: " + this.problem.getCourses().size());
        System.out.println("Number of labs: " + this.problem.getLabs().size());
        System.out.println("Number of not compatible: " + this.problem.getNotCompatible().size());
        System.out.println("Number of unwanted: " + this.problem.getUnwanted().size());
        System.out.println("Number of Preferences: " + this.problem.getPrefrences().size());
        System.out.println("Number of pairs: " + this.problem.getPairs().size());
        System.out.println("Number of partial assignements" + this.problem.getPartialAssignemnts().size());

    }
    
}
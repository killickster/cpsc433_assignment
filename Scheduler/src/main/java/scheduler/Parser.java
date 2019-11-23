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
                String courseIdentifier = courseAbbreviation + " " + courseNumber;
                String courseFormat = regexMatcher.group(3);
                String section = regexMatcher.group(4);
                String courseSection = courseFormat + " " + section;

                Course course = new Course(courseIdentifier, courseSection);

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
            Matcher regexMatcher2 = Parser.coursePattern.matcher(trimmedLine);

            regexMatcher1.find();
            regexMatcher2.find();

            if(regexMatcher1.matches()){ 

                String courseAbbreviation = regexMatcher1.group(1);
                String courseNumber = regexMatcher1.group(2);
                String courseIdentifier = courseAbbreviation + " " + courseNumber;
                String courseFormat = regexMatcher1.group(3);
                String section = regexMatcher1.group(4);
                String courseSection = courseFormat + " " + section;
                String labType = regexMatcher1.group(5);
                String labSection = regexMatcher1.group(6);

                Lab lab = new ExclusiveLab(courseIdentifier,courseSection, labType, labSection);

                for(Course course: this.problem.getCourses()){
                    if(course.getCourseIdentifier().equals(courseIdentifier)){
                        course.getLabs().add(lab);
                    }
                }

                this.problem.addLab(lab);


            }else if(regexMatcher2.matches()){ 

                String courseAbbreviation = regexMatcher2.group(1);
                String courseNumber = regexMatcher2.group(2);
                String courseIdentifier = courseAbbreviation + " " + courseNumber;
                String labType = regexMatcher2.group(3);
                String labSection = regexMatcher2.group(4);

                Lab lab = new Lab(courseIdentifier, labType, labSection);
                for(Course course: this.problem.getCourses()){
                    if(course.getCourseIdentifier().equals(courseIdentifier)){
                        course.getLabs().add(lab);
                    }
                }
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

                String courseName = regexMatcherLabs.group(1);
                String courseNumber = regexMatcherLabs.group(2);
                String courseIdentifier = courseName + " " + courseNumber;
                String courseFormat = regexMatcherLabs.group(3);
                String section = regexMatcherLabs.group(4);
                String courseSection = courseFormat + " " + section;
                String labType = regexMatcherLabs.group(5);
                String labSection = regexMatcherLabs.group(6);



                Lab lab;
                if((lab = this.problem.getLab(courseIdentifier, courseSection, labType, labSection)) != null){
                    
                    bookings.add(lab);
                }

        }


            

            while(regexMatcherCourses.find()){

                String courseAbbreviation = regexMatcherCourses.group(1);
                String courseNumber = regexMatcherCourses.group(2);
                String courseIdentifier = courseAbbreviation + " " + courseNumber;
                String format = regexMatcherCourses.group(3);

                if(format.equals("TUT")){

                    String labType = format;
                    String labSection = regexMatcherCourses.group(4);

                    Lab lab;
                    if((lab = this.problem.getLab(courseIdentifier, labType, labSection)) != null){
                        bookings.add(lab);
                    }

                }else{
                    String section = regexMatcherCourses.group(4);
                    String courseFormat = format;
                    String courseSection = courseFormat + " " + section;


                    Course course;
                    if((course = this.problem.getCourse(courseIdentifier, courseSection)) != null){
                        bookings.add(course);
                    }
                }

            }



            if(bookings.size() == 2){

                bookings.get(0).addNotCompatible(bookings.get(1));
                bookings.get(1).addNotCompatible(bookings.get(0));

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

            if(regexMatcherLabs1.find()){

                String courseAbbreviation = regexMatcherLabs1.group(1);
                String courseNumber = regexMatcherLabs1.group(2);
                String courseIdentifier = courseAbbreviation + " " + courseNumber;
                String courseFormat = regexMatcherLabs1.group(3);
                String section = regexMatcherLabs1.group(4);
                String courseSection = courseFormat + " " + section;
                String labType = regexMatcherLabs1.group(5);
                String labSection = regexMatcherLabs1.group(6);

                SlotBooking selectedBooking;


                if((selectedBooking = problem.getLab(courseIdentifier, courseSection, labType, labSection)) != null){

                    Slot slot;

                    if((slot = problem.getLabSlot(day, time)) != null){

                        selectedBooking.addUnwantedSlot(slot);
                    }
                }

        }


        else if(regexMatcherCourses.find()){

            String courseAbbreviation = regexMatcherCourses.group(1);
            String courseNumber = regexMatcherCourses.group(2);
            String courseIdentifier = courseAbbreviation + " " + courseNumber;
            String format = regexMatcherCourses.group(3);

            if(format.equals("TUT")){

                String labSection = regexMatcherCourses.group(4);
                String labType = format;

                SlotBooking selectedBooking;

                if((selectedBooking = this.problem.getLab(courseIdentifier, labType, labSection)) != null){

                    Slot slot;

                    if((slot = this.problem.getLabSlot(day, time)) != null){

                        selectedBooking.addUnwantedSlot(slot);

                    }
                }

            }else{
                String courseFormat = format;
                String section = regexMatcherCourses.group(4);
                String courseSection = courseFormat + " " + section;


                SlotBooking selectedBooking;

                if((selectedBooking = problem.getCourse(courseIdentifier, courseSection)) != null){
                    Slot slot;
                    if((slot = this.problem.getCourseSlot(day, time)) != null){
                        selectedBooking.addUnwantedSlot(slot);
                    }
                }

            }

        }
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
                String courseIdentifier = courseName + " " + courseNumber;
                String format = matcher1.group(5);

                if(format.equals("TUT")){
                    String labType = format;
                    String labSection = matcher1.group(6);
                    String weight = matcher1.group(7);

                SlotBooking selectedBooking;
                if((selectedBooking = this.problem.getLab(courseIdentifier, labType, labSection)) != null){

                    Slot slot;
                    if((slot = this.problem.getLabSlot(day, startTime)) != null){
                        preference = new Preference(slot, Integer.parseInt(weight)); 
                        selectedBooking.addPreference(preference);

                    }
                }
            }else{
                String courseFormat = format;
                String section = matcher1.group(6);
                String courseSection = courseFormat + " " + section;
                String weight = matcher1.group(7);
                SlotBooking selectedBooking;

                    if((selectedBooking = problem.getCourse(courseIdentifier, courseSection)) != null){
                        Slot slot;
                        if((slot = this.problem.getCourseSlot(day, startTime)) != null){
                                preference = new Preference(slot, Integer.parseInt(weight)); 
                                selectedBooking.addPreference(preference);
                        }
                    }

        }

        }else if(matcher2.find()){


                String day = matcher2.group(1);
                String startTime = matcher2.group(2);
                String courseName = matcher2.group(3);
                String courseNumber = matcher2.group(4);
                String courseFormat = matcher2.group(5);
                String courseIdentifier = courseName + " " + courseNumber;
                String section = matcher2.group(6);
                String courseSection = courseFormat + " " + section;
                String labType = matcher2.group(7);
                String labSection = matcher2.group(8);
                String weight = matcher2.group(9);

                SlotBooking selectedBooking;
                if((selectedBooking = this.problem.getLab(courseIdentifier, courseSection, labType, labSection)) != null){

                    Slot slot;
                    if((slot = this.problem.getLabSlot(day, startTime)) != null){
                        preference = new Preference(slot, Integer.parseInt(weight));
                        selectedBooking.addPreference(preference);
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
                String courseIdentifier = courseName + " " + courseNumber;
                String courseFormat = matcherLab.group(3);
                String section = matcherLab.group(4);
                String courseSection = courseFormat + " " + section;
                String labType = matcherLab.group(5);
                String labSection = matcherLab.group(6);

                SlotBooking selectedBooking;
                if((selectedBooking = this.problem.getLab(courseIdentifier, courseSection, labType, labSection)) != null){
                    booking.add(selectedBooking);
                }

        }

        while(matcherCourse.find()){

            String courseName = matcherCourse.group(1);
            String courseNumber = matcherCourse.group(2);
            String courseIdentifier = courseName + " " + courseNumber;
            String format = matcherCourse.group(3);

            if(format.equals("TUT")){
                String labType = format;
                String labSection = matcherCourse.group(4);

                SlotBooking selectedBooking;
                if((selectedBooking = this.problem.getLab(courseIdentifier, labType, labSection)) != null){

                    if(booking.size()< 2){
                        booking.add(selectedBooking);
                    }

                    }
        }else{
                String courseFormat = format;
                String section = matcherCourse.group(4);
                String courseSection = courseFormat + " " + section;


                SlotBooking selectedBooking;
                if((selectedBooking = this.problem.getCourse(courseIdentifier, courseSection)) != null){
                    if(booking.size()< 2){
                        booking.add(selectedBooking);
                    }
                }

    }

      }

      booking.get(0).addPaired(booking.get(1));
      booking.get(1).addPaired(booking.get(0));

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

                String courseName =  matcherLab.group(1);
                String courseNumber = matcherLab.group(2);
                String courseIdentifier = courseName + " " + courseNumber;
                String courseFormat = matcherLab.group(3);
                String section = matcherLab.group(4);
                String courseSection = courseFormat + " " +section;
                String labFormat = matcherLab.group(5);
                String labSection = matcherLab.group(6);
                String day = matcherLab.group(7);
                String startTime = matcherLab.group(8);

                Lab lab;
                if((lab = this.problem.getLab(courseIdentifier, courseSection, labFormat, labSection))!= null){

                    Slot slot;
                    if((slot = this.problem.getLabSlot(day, startTime)) != null){
                        
                            this.problem.addPartialAssignment(new PartialAssignment(lab, slot));
                    }
            }



            }else if(matcherCourse.find()){

                String courseName = matcherCourse.group(1);
                String courseNumber = matcherCourse.group(2);
                String courseIdentifier = courseName + " " + courseNumber;
                String format = matcherCourse.group(3);
    
                if(format.equals("TUT")){
                    String labType = format;
                    String labSection = matcherCourse.group(4);
                    String day = matcherCourse.group(5);
                    String startTime = matcherCourse.group(6);
    
                    SlotBooking selectedBooking;
                    if((selectedBooking = this.problem.getLab(courseIdentifier, labType, labSection)) != null){

                        Slot slot;
                        if((slot = this.problem.getLabSlot(day, startTime)) != null){
                            this.problem.addPartialAssignment(new PartialAssignment(selectedBooking, slot));
                        }
                    }
            }else{
                    String courseFormat = format;
                    String section = matcherCourse.group(4);
                    String courseSection = courseFormat + " " +section;
                    String day = matcherCourse.group(5);
                    String startTime = matcherCourse.group(6);
   
                    SlotBooking selectedBooking;
                    if((selectedBooking = problem.getCourse(courseIdentifier, courseSection)) != null){
                        Slot slot;
                        if((slot = this.problem.getCourseSlot(day, startTime)) != null){
                            this.problem.addPartialAssignment(new PartialAssignment(selectedBooking, slot));
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
        System.out.println("Number of partial assignements" + this.problem.getPartialAssignemnts().size());

    }






    
    
}
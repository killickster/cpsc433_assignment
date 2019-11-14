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



    public Parser(){

        problem = new Problem();


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

            Matcher regexMatcherCourses = Parser.coursePattern.matcher(trimmedLine);
            Matcher regexMatcherLabs1 = Parser.labPattern1.matcher(trimmedLine);
            Matcher regexMatcherLabs2 = Parser.labPattern2.matcher(trimmedLine);


            ArrayList<RoomBooking> bookings = new ArrayList<RoomBooking>();


            while(regexMatcherLabs1.find()){

                String courseAbbreviation = regexMatcherLabs1.group(1);
                String courseNumber = regexMatcherLabs1.group(2);
                String courseFormat = regexMatcherLabs1.group(3);
                String courseSection = regexMatcherLabs1.group(4);
                String labType = regexMatcherLabs1.group(5);
                String labSection = regexMatcherLabs1.group(6);



                for(Lab lab: problem.getLabs()){

                    if(lab.getCourseName().equals(courseAbbreviation) && lab.getCourseNumber().equals(courseNumber)
                        && lab.getCourseFormat().equals(courseFormat) && lab.getCourseSection().equals(courseSection)
                        && lab.getLabFormat().equals(labType) && lab.getLabSection().equals(labSection)){

                            bookings.add(lab);
                }
                
            }
        }

            while(regexMatcherLabs2.find()){

                String courseAbbreviation = regexMatcherLabs2.group(1);
                String courseNumber = regexMatcherLabs2.group(2);
                String labType = regexMatcherLabs2.group(3);
                String labSection = regexMatcherLabs2.group(4);




                for(Lab lab: problem.getLabs()){
                    if(lab.getCourseName().equals(courseAbbreviation) && lab.getCourseNumber().equals(courseNumber)
                    && lab.getLabFormat().equals(labType) && lab.getLabSection().equals(labSection)){

                        if(bookings.size() < 2){
                            bookings.add(lab);
                        }
               
                    }
                }
            }

            while(regexMatcherCourses.find()){

                String courseAbbreviation = regexMatcherCourses.group(1);
                String courseNumber = regexMatcherCourses.group(2);
                String courseFormat = regexMatcherCourses.group(3);
                String courseSection = regexMatcherCourses.group(4);

                for(Course course: problem.getCourses()){
                    if(course.getCourseName().equals(courseAbbreviation) && course.getCourseNumber().equals(courseNumber)
                    && course.getFormat().equals(courseFormat) && course.getSection().equals(courseSection)){

                        if(bookings.size() < 2){

                            bookings.add(course);
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

            System.out.println("Preference: " + trimmedLine);

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

            System.out.println("Partial Assignment: " + trimmedLine);

        }

        System.out.println("Number of course slots: "+this.problem.getCourseSlots().size());
        System.out.println("Number of lab slots: " +this.problem.getLabSlots().size());
        System.out.println("Number of courses: " + this.problem.getCourses().size());
        System.out.println("Number of labs: " + this.problem.getLabs().size());
        System.out.println("Number of not compatible: " + this.problem.getNotCompatible().size());

    }


    
}
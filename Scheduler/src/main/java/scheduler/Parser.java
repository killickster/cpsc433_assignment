package scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private Problem problem;
    private Pattern slotPattern;
    private Pattern coursePattern;

    public Parser(){

        problem = new Problem();

    }


    public void parseFile(String fileName) throws IOException{

        //Generate regular expressions

        //Regular expression for slots
        String slotRegex = "([A-Z][A-Z]),\\s{0,10}(\\d{1,2}:\\d\\d),\\s{0,10}(\\d),\\s{0,10}(\\d)"; 
        this.slotPattern = Pattern.compile(slotRegex);

        //Regular expression for Courses
        String courseRegex = "([A-Z]{4})\\s{0,10}(\\d{3})\\s{0,10}([A-Z]{3})\\s{0,10}(\\d{2})";

        this.coursePattern = Pattern.compile(courseRegex);


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

            Matcher regexMatcher = this.coursePattern.matcher(trimmedLine);

            regexMatcher.find();

            if(regexMatcher.group().length() != 0){

                String courseAbbreviation = regexMatcher.group(1);
                String courseNumber = regexMatcher.group(2);
                String courseFormat = regexMatcher.group(3);
                String courseSection = regexMatcher.group(4);

                Course course = new Course(courseAbbreviation, courseNumber, courseFormat, courseSection);

                System.out.println(courseNumber);
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

            System.out.println("Not Compatible: " + trimmedLine);

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

        System.out.println(this.problem.getCourseSlots().size());
        System.out.println(this.problem.getLabSlots().size());
        System.out.println(this.problem.getCourses().size());

    }


    
}
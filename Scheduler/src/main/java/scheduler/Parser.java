package scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

    private Problem problem;

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

            System.out.println("Course Slot: " + trimmedLine);



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
    }


    
}
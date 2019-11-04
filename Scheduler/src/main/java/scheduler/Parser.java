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
                    parseCourseSlot(reader);
                    break;
                default:
                    System.out.println(trimmedLine);
            }
        }

    }

    public void parseCourseSlot(BufferedReader reader) throws IOException{

        String line;

        while((line = reader.readLine()) != null){

            String trimmedLine = line.trim();

            if(trimmedLine.length() == 0){ 
                return;
            }

            System.out.println("Course Slots: " + trimmedLine);



        }
    }


    
}
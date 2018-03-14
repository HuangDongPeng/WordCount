import java.io.*;
import java.util.Scanner;

public class Main {


    public static void main(String[] args)throws Exception{
        int countChar=0;
        int countWord=0;
        int countLine=0;

        String testPath="d:/test.c";


        ReadChar(testPath);

        System.out.println();
        System.out.println("char count"+countChar);
        System.out.println("word count"+countWord);
        System.out.println("line count"+countLine);

    }

    public  static  void ReadChar(String path){

        File file=new File(path);
        Reader reader=null;

        try{
            reader  =new InputStreamReader(new FileInputStream(file));
            int tempChar;
            char character;
            while((tempChar=reader.read())!=-1){
                character=(char)tempChar;
                //if(character=='\n')
                //System.out.println("换行");

                System.out.print(character);
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


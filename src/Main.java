import java.io.*;
import java.util.Scanner;

public class Main {


    public static void main(String[] args)throws Exception{

        String testPath="d:/test.c";

        ReadChar(testPath);
        ReadLine(testPath);
        ReadWord(testPath);

    }

    public  static  void ReadChar(String path){

        File file=new File(path);
        Reader reader=null;

        try{
            int charCount=0;
            reader  =new InputStreamReader(new FileInputStream(file));
            int tempChar;
            char character;
            while((tempChar=reader.read())!=-1){
                //判断是不是回车
                if(!(tempChar==13||tempChar==10))
                    charCount++;

                character=(char)tempChar;
                System.out.print(" "+tempChar);

            }
            reader.close();
            System.out.println("word count: "+charCount);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  static  void ReadLine(String path){
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
                line++;
            }
            reader.close();
            System.out.println("line count: "+line  );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public  static  void ReadWord(String path) {
        File file = new File(path);
        Reader reader = null;

        try {
            int wordCount = 0;
            reader = new InputStreamReader(new FileInputStream(file));
            int tempChar;
            char character;
            boolean isChar=false;
            while ((tempChar = reader.read()) != -1) {
               if((tempChar>=65&&tempChar<=90)||(tempChar>=97&&tempChar<=122)){
                   isChar=true;
                   System.out.print((char)tempChar);
               }
               else {
                   if(isChar)
                   {
                       isChar=false;
                       wordCount++;
                   }
                   continue;
               }

            }
            reader.close();
            System.out.println("word count: " + wordCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


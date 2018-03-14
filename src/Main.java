import java.io.*;
import java.util.Scanner;

public class Main {


    public static void main(String[] args)throws Exception{
        int countChar=0;
        int countWord=0;
        int countLine=0;

        String testPath="d:/test.c";

        ReadChar(testPath);
        ReadLine(testPath);

        System.out.println();
        System.out.println("char count"+countChar);
        System.out.println("word count"+countWord);
        System.out.println("line count"+countLine);

    }

    public  static  void ReadChar(String path){

        File file=new File(path);
        Reader reader=null;

        try{
            int wordCount=0;
            reader  =new InputStreamReader(new FileInputStream(file));
            int tempChar;
            char character;
            while((tempChar=reader.read())!=-1){
            //判断是不是回车
                if(!(tempChar==13||tempChar==10))
                    wordCount++;

                character=(char)tempChar;
                System.out.print(" "+tempChar);

            }
            reader.close();
            System.out.println("word count: "+wordCount);
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

    public  static  void ReadWord(String path){

    }
}


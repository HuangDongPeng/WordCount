import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args)throws Exception{
        String testPath="d:/test/test.c";
        //GetDifferentLine(testPath);
        StopWordTable("d:/test/test.txt",testPath);
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
            }
            reader.close();
            System.out.println("char count: "+charCount);
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

    public static  void FindFile(String dir){
        File tmpFile=new File(dir);
        if(tmpFile.isDirectory()){
            try {
                String [] fileNames=tmpFile.list();
                if(fileNames.length!=0){
                    for (String s:fileNames) {
                        String newPath=dir+"/"+s;
                        FindFile(newPath);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            if(dir.contains(".c"))
            {
                System.out.println("file dir is: "+dir);
                ReadLine(dir);
            }
        }
    }

    public  static void GetDifferentLine(String path){
        File file = new File(path);
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int emptyLine=0;
            int codeLine=0;
            int noteLine=0;
            while ((tempString = reader.readLine()) != null) {
                if(tempString.contains("//"))
                    noteLine++;
                else if(tempString.isEmpty()){
                    emptyLine++;
                }
                else
                    codeLine++;
            }
            reader.close();

            System.out.println("code line is: "+codeLine);
            System.out.println("empty line is: "+emptyLine);
            System.out.println("note line is: "+noteLine);

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

    public  static void StopWordTable(String tablePath,String filePath) {
        File wordTableFile = new File(tablePath);
        Reader reader = null;
        ArrayList<String> wordTable=new ArrayList<String>();

        try {
            reader = new InputStreamReader(new FileInputStream(wordTableFile));
            int tempChar;
            boolean isChar=false;
            StringBuilder sb=new StringBuilder();

            while ((tempChar = reader.read()) != -1) {
                if((tempChar>=65&&tempChar<=90)||(tempChar>=97&&tempChar<=122)){
                    isChar=true;
                    sb.append((char)tempChar);
                }
                else {
                    if(isChar)
                    {
                        wordTable.add(sb.toString());
                        sb=new StringBuilder();
                        isChar=false;
                    }
                    continue;
                }

            }
            reader.close();
            if(isChar&&sb.length()!=0){
                wordTable.add(sb.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //读取文件内容
        File readFile=new File(filePath);
        try {
            reader = new InputStreamReader(new FileInputStream(readFile));
            int tempChar;
            int wordCount=0;
            boolean isChar=false;
            StringBuilder sb=new StringBuilder();

            while ((tempChar = reader.read()) != -1) {
                if((tempChar>=65&&tempChar<=90)||(tempChar>=97&&tempChar<=122)){
                    isChar=true;
                    sb.append((char)tempChar);
                }
                else {
                    if(isChar)
                    {
                        if(!IsInTable(wordTable,sb.toString())){
                            wordCount++;
                        }
                        sb=new StringBuilder();
                        isChar=false;
                    }
                    continue;
                }

            }
            reader.close();
            System.out.print("word count with stopWordTable: "+wordCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  static boolean IsInTable(ArrayList<String> tabel,String word){
        for (String s :
                tabel) {
            if(s.equals(word))
                return  true;
        }
        return  false;
    }

}


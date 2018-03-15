import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static StringBuilder sb = new StringBuilder();
    static String filePath=null;
    static String outputFileName=null;
    static String stopListFileName=null;
    static String fileDir="./";
    static String fomatName=null;
    static boolean isUseStopList = false;
    static boolean isOutPutFile=false;
    static boolean isGetDirFiles=false;
    static ArrayList<String> canBeFoundFile=new ArrayList<String>();


    public static void main(String[] args) throws Exception {
        String[] inputArgs =args; //{"-c", "-a","-s", "test.c","-e","test.txt","-o","output.txt"};
        //String[] inputArgs={"-s","-w","*.class","-o","output.txt"};
        for (int i = 0; i < inputArgs.length; i++) {
            //is use stop list
            if (inputArgs[i].contains("-e")) {
                isUseStopList = true;
                i++;
                stopListFileName = inputArgs[i];
            }
            //is out put new file
            if(inputArgs[i].contains("-o")){
                isOutPutFile=true;
                i++;
                outputFileName=inputArgs[i];
            }
            if(inputArgs[i].contains("-s")){
                isGetDirFiles=true;
            }
        }

        int fileNameIndex = 0;
        //get fileName index
        for (int i = 0; i < inputArgs.length; i++) {
            if (inputArgs[i].contains(".")) {
                fileNameIndex = i;
                filePath=inputArgs[i];
                if(filePath.contains("*."))
                {
                    int pointIndex=filePath.indexOf(".");
                    fomatName=filePath.substring(pointIndex);
                }
                break;
            }
        }


        if(!isGetDirFiles) {
            for (int i = 0; i < fileNameIndex; i++) {
                OrderJudge(inputArgs[i]);
            }
        }
        else {
            FindFile(fileDir);
            for (String s :
                    canBeFoundFile) {
                filePath=s;
                System.out.println(s);
                for (int i = 0; i < fileNameIndex; i++) {
                    OrderJudge(inputArgs[i]);
                }
            }
        }

        if(isOutPutFile)
            OutPutFile(outputFileName,sb);
    }

    public static void OrderJudge(String order) {
        switch (order) {
            case "-c":
                if (filePath.isEmpty())
                    return;
                ReadChar(filePath);
                break;
            case "-w":
                if (filePath.isEmpty())
                    return;
                if (isUseStopList){
                    StopWordTable(stopListFileName, filePath);
                }
                else
                    ReadWord(filePath);
                break;
            case "-l":
                if (filePath.isEmpty())
                    return;
                ReadLine(filePath);
                break;
            case "-o":
                if (outputFileName.isEmpty())
                    return;
                OutPutFile(outputFileName, sb);
                break;
            case "-a":
                if (filePath.isEmpty())
                    return;
                GetDifferentLine(filePath);
                break;
            case "-e":
                if (stopListFileName.isEmpty())
                    return;
                StopWordTable(stopListFileName, filePath);
                break;
            default:break;
        }
    }

    public static void ReadChar(String path) {

        File file = new File(path);
        Reader reader = null;

        try {
            int charCount = 0;
            reader = new InputStreamReader(new FileInputStream(file));
            int tempChar;
            char character;
            while ((tempChar = reader.read()) != -1) {
                //判断是不是回车
                if (!(tempChar == 13 || tempChar == 10))
                    charCount++;
                character = (char) tempChar;
            }
            reader.close();
            sb.append(path + "字符数： " + charCount);
            AppendNewLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ReadLine(String path) {
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
            sb.append(path + "行数：" + line);
            AppendNewLine();
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

    public static void ReadWord(String path) {
        File file = new File(path);
        Reader reader = null;

        try {
            int wordCount = 0;
            reader = new InputStreamReader(new FileInputStream(file));
            int tempChar;
            char character;
            boolean isChar = false;
            while ((tempChar = reader.read()) != -1) {
                if ((tempChar >= 65 && tempChar <= 90) || (tempChar >= 97 && tempChar <= 122)) {
                    isChar = true;
                } else {
                    if (isChar) {
                        isChar = false;
                        wordCount++;
                    }
                    continue;
                }

            }
            reader.close();
            sb.append(path + "单词数：" + wordCount);
            AppendNewLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void FindFile(String dir) {
        File tmpFile = new File(dir);
        if (tmpFile.isDirectory()) {
            try {
                String[] fileNames = tmpFile.list();
                if (fileNames.length != 0) {
                    for (String s : fileNames) {
                        String newPath = dir + "/" + s;
                        FindFile(newPath);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (dir.contains(fomatName)) {
                canBeFoundFile.add(dir);
            }
        }
    }

    public static void GetDifferentLine(String path) {
        File file = new File(path);
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int emptyLine = 0;
            int codeLine = 0;
            int noteLine = 0;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("//"))
                    noteLine++;
                else if (tempString.isEmpty()) {
                    emptyLine++;
                } else
                    codeLine++;
            }
            reader.close();
            sb.append(path + "代码行/空行/注释行：" + codeLine + "/" + emptyLine + "/" + noteLine);
            AppendNewLine();
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

    public static void StopWordTable(String tablePath, String filePath) {
        isUseStopList=false;
        File wordTableFile = new File(tablePath);
        Reader reader = null;
        ArrayList<String> wordTable = new ArrayList<String>();

        try {
            reader = new InputStreamReader(new FileInputStream(wordTableFile));
            int tempChar;
            boolean isChar = false;
            StringBuilder sb = new StringBuilder();

            while ((tempChar = reader.read()) != -1) {
                if ((tempChar >= 65 && tempChar <= 90) || (tempChar >= 97 && tempChar <= 122)) {
                    isChar = true;
                    sb.append((char) tempChar);
                } else {
                    if (isChar) {
                        wordTable.add(sb.toString());
                        sb = new StringBuilder();
                        isChar = false;
                    }
                    continue;
                }

            }
            reader.close();
            if (isChar && sb.length() != 0) {
                wordTable.add(sb.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //读取文件内容
        File readFile = new File(filePath);
        try {
            reader = new InputStreamReader(new FileInputStream(readFile));
            int tempChar;
            int wordCount = 0;
            boolean isChar = false;
            StringBuilder localSb = new StringBuilder();

            while ((tempChar = reader.read()) != -1) {
                if ((tempChar >= 65 && tempChar <= 90) || (tempChar >= 97 && tempChar <= 122)) {
                    isChar = true;
                    localSb.append((char) tempChar);
                } else {
                    if (isChar) {
                        if (!IsInTable(wordTable, localSb.toString())) {
                            wordCount++;
                        }
                        localSb = new StringBuilder();
                        isChar = false;
                    }
                    continue;
                }

            }
            reader.close();
            sb.append(filePath+"单词数:" + wordCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean IsInTable(ArrayList<String> tabel, String word) {
        for (String s :
                tabel) {
            if (s.equals(word))
                return true;
        }
        return false;
    }

    public static void OutPutFile(String outputPath, StringBuilder sb) {
        try {
            File file = new File(outputPath);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = null;
            PrintWriter pw = null;

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(sb.toString());
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void AppendNewLine() {
        sb.append('\r');
        sb.append('\n');
    }

}


import com.sun.xml.internal.fastinfoset.tools.PrintTable;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static StringBuilder sb = new StringBuilder();
    static String filePath = null;
    static String outputFileName = "result.txt";
    static String stopListFileName = null;
    static String fileDir = "./";
    public static String fomatName = null;

    static boolean isUseStopList = false;
    static boolean isOutPutFile = false;
    static boolean isGetDirFiles = false;
    public static ArrayList<String> canBeFoundFile = new ArrayList<String>();


    public static void main(String[] args) throws Exception {
        if (args[0].equals("testCase"))
            TestCase();
        else {
            // String[] inputArgs = {"-w", "test.c", "-e", "stoplist.txt"};
            String[] inputArgs = args;
            Execute(inputArgs);
        }
    }

    static void TestCase() {
        Test exm = new Test();
        String path = "testCase/test.c";
        String stopListPath = "testCase/wordTable.txt";
        PrintTestResult(exm.Test_CharCount(20,path));
        PrintTestResult(exm.Test_LineCount(9,path));
        PrintTestResult(exm.Test_WordCount(6,path));
        PrintTestResult(exm.Test_ReadDiffLine(4,4,1,path));
        PrintTestResult(exm.Test_StopList(6,stopListPath,path));
        PrintTestResult(exm.Test_Recursion(2,"./testCase",".c"));
        PrintTestResult(exm.Test_OutputFile(sb,"testCase/output.txt"));
        int[] expResult={6,6};
        PrintTestResult(exm.Test_Recursion_StopList(".c",stopListPath,expResult));
        int[] expResult2={5,2};
        PrintTestResult(exm.Test_Recusion_WordRead_OutputFile(".c","ouputtext.txt",expResult2));
    }

    static void PrintTestResult(Object Result) {
        System.out.println("result: " + Result);
    }

    public static void Execute(String[] inputArgs) {
        for (int i = 0; i < inputArgs.length; i++) {
            //is use stop list
            if (inputArgs[i].contains("-e")) {
                isUseStopList = true;
                i++;
                stopListFileName = inputArgs[i];
            }
            //is out put new file
            if (inputArgs[i].contains("-o")) {
                isOutPutFile = true;
                i++;
                outputFileName = inputArgs[i];
            }
            if (inputArgs[i].contains("-s")) {
                isGetDirFiles = true;
            }
        }

        int fileNameIndex = 0;
        //get fileName index
        for (int i = 0; i < inputArgs.length; i++) {
            if (inputArgs[i].contains(".")) {
                fileNameIndex = i;
                filePath = inputArgs[i];
                if (filePath.contains(".")) {
                    int pointIndex = filePath.lastIndexOf(".");
                    fomatName = filePath.substring(pointIndex);
                }
                break;
            }
        }


        if (!isGetDirFiles) {
            for (int i = 0; i < fileNameIndex; i++) {
                OrderJudge(inputArgs[i]);
            }
        } else {
            SetFileDir(inputArgs);
            FindFile(fileDir);
            for (String s :
                    canBeFoundFile) {
                filePath = s;
                for (int i = 0; i < fileNameIndex; i++) {
                    OrderJudge(inputArgs[i]);
                }
            }
        }

        OutPutFile(outputFileName, sb);
    }

    public static void SetFileDir(String[] inputArgs) {
        for (String s :
                inputArgs) {
            if (s.contains(".")) {
                fileDir = s;
                break;
            }
        }

        int indexOfPoint = fileDir.indexOf("*");
        if (indexOfPoint == -1)
            fileDir = "./";
        else
            fileDir = fileDir.substring(0, indexOfPoint);

        if(fileDir.isEmpty())
            fileDir="./";
        System.out.println("file dir: " + fileDir);
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
                if (isUseStopList) {
                    StopWordTable(stopListFileName, filePath);
                } else
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
            default:
                break;
        }
    }

    public static int ReadChar(String path) {

        File file = new File(path);
        Reader reader = null;

        try {
            int charCount = 0;
            reader = new InputStreamReader(new FileInputStream(file));
            int tempChar;
            char character;
            while ((tempChar = reader.read()) != -1) {
                //判断是不是回车
                if (!(tempChar == 13 || tempChar == 10 || tempChar == 9))
                    charCount++;
                character = (char) tempChar;
            }
            reader.close();
            sb.append(path + "字符数： " + charCount);
            AppendNewLine();
            return charCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int ReadLine(String path) {
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
            return line;
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
        return 0;
    }

    public static int ReadWord(String path) {
        File file = new File(path);
        Reader reader = null;
        try {
            int wordCount = 0;
            reader = new InputStreamReader(new FileInputStream(file));
            int tempChar;
            boolean isChar = false;
            while ((tempChar = reader.read()) != -1) {
                if (tempChar != '\t' && tempChar != '\n' && tempChar != ' '&&tempChar!='\r') {
                    isChar = true;
                } else {
                    if (isChar) {
                        isChar = false;
                        wordCount++;
                    }
                    continue;
                }
            }
            if (isChar) {
                wordCount++;
                isChar = false;
            }
            reader.close();
            sb.append(path + "单词数：" + wordCount);
            AppendNewLine();
            return wordCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int FindFile(String dir) {

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
                int filePointIndex = dir.lastIndexOf(".");
                String rightFormatName = dir.substring(filePointIndex);
                //System.out.println(dir.substring(filePointIndex));
                if (rightFormatName.equals(fomatName)){
                    canBeFoundFile.add(dir);
            }
            }
        }

        return canBeFoundFile.size();
    }

    public static int[] GetDifferentLine(String path) {
        int[] result = new int[3];
        File file = new File(path);
        BufferedReader reader = null;
        try {
            boolean isNote = false;
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int emptyLine = 0;
            int codeLine = 0;
            int noteLine = 0;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("/*")) {
                    isNote = true;
                } else if (tempString.contains("*/")) {
                    isNote = false;
                }

                if (tempString.contains("//") || tempString.contains("*/") || isNote)
                    noteLine++;
                else if (tempString.isEmpty() || IsEmpty(tempString)) {
                    emptyLine++;
                } else
                    codeLine++;
            }
            reader.close();
            sb.append(path + "代码行/空行/注释行：" + codeLine + "/" + emptyLine + "/" + noteLine);
            AppendNewLine();
            result[0] = codeLine;
            result[1] = emptyLine;
            result[2] = noteLine;
            return result;
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
        return result;
    }

    public static int StopWordTable(String tablePath, String filePath) {
        isUseStopList = false;
        File wordTableFile = new File(tablePath);
        Reader reader = null;
        ArrayList<String> wordTable = new ArrayList<String>();
        int wordCount = 0;

        try {
            System.out.println("stop list word:");
            reader = new InputStreamReader(new FileInputStream(wordTableFile));
            int tempChar;
            boolean isChar = false;
            StringBuilder sb = new StringBuilder();

            while ((tempChar = reader.read()) != -1) {
                if (tempChar != '\t' && tempChar != '\n' && tempChar != ' '&&tempChar!='\r') {
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
            boolean isChar = false;
            StringBuilder localSb = new StringBuilder();
            while ((tempChar = reader.read()) != -1) {
                if ((tempChar != '\t' && tempChar != '\n' && tempChar != ' '&&tempChar!='\r')) {
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
            if (isChar)
                if (!IsInTable(wordTable, localSb.toString())) {
                    wordCount++;
                    isChar = false;
                }

            reader.close();
            sb.append(filePath + "单词数:" + wordCount);
            return wordCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordCount;
    }

    public static boolean IsInTable(ArrayList<String> tabel, String word) {
        for (String s :
                tabel) {
            if (s.equals(word))
                return true;
        }
        return false;
    }

    public static boolean OutPutFile(String outputPath, StringBuilder sb) {
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void AppendNewLine() {
        sb.append('\r');
        sb.append('\n');
    }

    private static boolean IsEmpty(String s) {
        char[] characters = s.toCharArray();
        boolean isAllSpace = true;
        int otherChar = 0;
        for (char c :
                characters) {
            if (c != 9 && c != 32)
                isAllSpace = false;
            if (!(c >= 65 && c <= 90) || (c >= 97 && c <= 122))
                otherChar++;
        }
        if (isAllSpace)
            return true;
        else if (otherChar <= 1)
            return true;
        return false;
    }

}


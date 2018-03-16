import com.sun.org.apache.xpath.internal.operations.Bool;

public class Test {

    public boolean Test_CharCount(int expResult,String path){
           return PrintErrorDetail(expResult,Main.ReadChar(path));
    }

    public boolean Test_WordCount(int expResult,String path){
        return PrintErrorDetail(expResult,Main.ReadWord(path));
    }

    public boolean Test_LineCount(int expResult,String path){
        return PrintErrorDetail(expResult,Main.ReadLine(path));
    }

    public  boolean Test_ReadDiffLine(int emptyLine,int noteLine,int codeLine,String path){
        int[] expResult=new int[3];
        expResult[0]=codeLine;
        expResult[1]=emptyLine;
        expResult[2]=noteLine;
        int[] testResult=Main.GetDifferentLine(path);
        for(int i=0;i<3;i++ ){
            if(expResult[i]!=testResult[i])
               return PrintErrorDetail(expResult[i],testResult[i]);
        }
        return true;
    }

    private boolean PrintErrorDetail(int expResult,int testResult){
        System.out.println("exp result: "+expResult);
        System.out.println("test result: "+testResult);
        if(expResult==testResult)
            return true;
        return false;
    }
}

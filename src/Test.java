import com.sun.org.apache.xpath.internal.operations.Bool;

public class Test {

    public boolean Test_CharCount(int expResult,String path){
        return (expResult==Main.ReadChar(path));
    }

    public boolean Test_WordCount(int expResult,String path){
        return (expResult==Main.ReadWord(path));
    }

    public boolean Test_LineCount(int expResult,String path){
        return (expResult==Main.ReadLine(path));
    }


}

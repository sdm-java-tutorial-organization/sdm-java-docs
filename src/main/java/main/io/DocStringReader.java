package main.io;

import sun.misc.IOUtils;

import java.io.StringReader;

public class DocStringReader {

    public static void main(String[] args) throws Exception {

        StringReader sr = getInstance("\\\'");

        // toString
        System.out.println(readerToString(sr));
    }

    // == new StringReader() ==
    public static StringReader getInstance(String target) {
        return new StringReader(target);
    }

    // == StringReader -> String [방법1] ==
    public static String readerToString(StringReader sr) throws Exception {
        StringBuilder builder = new StringBuilder();
        int charsRead = -1;
        char[] chars = new char[100];
        do{
            charsRead = sr.read(chars,0,chars.length);
            //if we have valid chars, append them to end of string.
            if(charsRead>0)
                builder.append(chars,0,charsRead);
        }while(charsRead>0);
        return builder.toString();
    }

    // == StringReader -> String [방법2] ==
    public static String raderToString(StringReader sr) throws Exception {
        /*return IOUtils.toString(sr);*/
        return null;
    }
}

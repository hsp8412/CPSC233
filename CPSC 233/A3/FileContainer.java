import java.io.FileReader;
import java.io.BufferedReader;

/*
 * * Name: Sipeng He
 * No modification has been made to this class 
 * 
  Author:  James Tam
  Version: February 16, 2021

*/

public class FileContainer
{
    private BufferedReader br;
    private FileReader fr; 

    public FileContainer(BufferedReader aBufferedReader, 
                         FileReader aFileReader)
    {
        br = aBufferedReader;
        fr = aFileReader;
    }

    public BufferedReader getBufferedReader()
    {
        return(br);
    }

    public FileReader getFileReader()
    {
        return(fr);
    }
}

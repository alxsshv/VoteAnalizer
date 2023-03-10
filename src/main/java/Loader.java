
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class Loader {

    public static void main(String[] args) throws Exception {
        String fileName = "res/data-1572M.xml";

        long start = System.currentTimeMillis();
        System.out.print("Extracting data from a file ...");
        parseFile(fileName);

        System.out.println("\n Extraction time " + ((System.currentTimeMillis()-start)/1000/60) + " minutes");


        System.out.println("Calculate voting station work times ...");
        DBConnection.printStationWorkingTime();

        System.out.println("Searching duplicated voters ...");
        DBConnection.printVoterCounts();
        DBConnection.getConnection().close();
    }

    private static void parseFile(String fileName) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        saxParser.parse(new File(fileName),handler);

    }

}
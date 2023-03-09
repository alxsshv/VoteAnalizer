import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class XMLHandler extends DefaultHandler {
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static final int BUFFER_SIZE = 1000000;
    private final VoterParseBuffer votersBuffer = new VoterParseBuffer();
    private final VisitParseBuffer visitsBuffer = new VisitParseBuffer();
    private long start;

    public XMLHandler(){
        //voterCounts = new HashMap<>();
    }

    @Override
    public void startDocument() throws SAXException {
        start = System.currentTimeMillis();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("voter")) {
            String name = attributes.getValue("name");
            String birthDay = attributes.getValue("birthDay");
            votersBuffer.addRecord(name,birthDay);
        }
//        if (qName.equals("visit")){
//            String station = attributes.getValue("station");
//            String[] dateTime = attributes.getValue("time").split(" ");
//            String visitDate = dateTime[0];
//            String visitTime = dateTime[1];
//            visitsBuffer.addRecord(station, visitDate, visitTime);
//        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("voter")){
            if (votersBuffer.getSize() >= BUFFER_SIZE){
                try {
                    long time = (System.currentTimeMillis() - start)/1000;
                    System.out.println("Выполняем инсерт, прошло времени " + time + " сек");
                    DBConnection.executeMultiInsertVoters(votersBuffer.toString());
                    votersBuffer.clear();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
//        if (qName.equals("visit")) {
//            if (visitsBuffer.getSize() >= BUFFER_SIZE) {
//                try {
//                    DBConnection.executeMultiInsertVisits(visitsBuffer.toString());
//                    visitsBuffer.clear();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
    }

    @Override
    public void endDocument() throws SAXException {
        if (!votersBuffer.isEmpty()){
            try {
                DBConnection.executeMultiInsertVoters(votersBuffer.toString());
                votersBuffer.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
//        if (!visitsBuffer.isEmpty()){
//            try {
//                DBConnection.executeMultiInsertVisits(visitsBuffer.toString());
//                visitsBuffer.clear();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }


}

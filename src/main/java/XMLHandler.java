import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;

public class XMLHandler extends DefaultHandler {
    private static final int BUFFER_SIZE = 1000000;
    private final VoterParseBuffer votersBuffer = new VoterParseBuffer();
    private final VisitParseBuffer visitsBuffer = new VisitParseBuffer();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        if (qName.equals("voter")) {
            String name = attributes.getValue("name");
            String birthDay = attributes.getValue("birthDay");
            votersBuffer.addRecord(name,birthDay);
        }
        if (qName.equals("visit")){
            String station = attributes.getValue("station");
            String[] dateTime = attributes.getValue("time").split(" ");
            String visitDate = dateTime[0];
            String visitTime = dateTime[1];
            visitsBuffer.addRecord(station, visitDate, visitTime);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("voter")){
            if (votersBuffer.getSize() >= BUFFER_SIZE){
                try {
                    System.out.print(".");
                    DBConnection.executeMultiInsertVoters(votersBuffer.toString());
                    votersBuffer.clear();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (qName.equals("visit")) {
            if (visitsBuffer.getSize() >= BUFFER_SIZE) {
                try {
                    DBConnection.executeMultiInsertVisits(visitsBuffer.toString());
                    visitsBuffer.clear();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void endDocument() {
        if (!votersBuffer.isEmpty()){
            try {
                DBConnection.executeMultiInsertVoters(votersBuffer.toString());
                votersBuffer.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (!visitsBuffer.isEmpty()){
            try {
                DBConnection.executeMultiInsertVisits(visitsBuffer.toString());
                visitsBuffer.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


}

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class XMLHandler extends DefaultHandler {
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static final int BUFFER_SIZE = 30000;
    private final VoterParseBuffer votersBuffer = new VoterParseBuffer();
    private final VoterParseBuffer visitsBuffer = new VoterParseBuffer();

    public XMLHandler(){
        //voterCounts = new HashMap<>();
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("voter")) {
            String name = attributes.getValue("name");
            String birthDay = attributes.getValue("birthDay");
            votersBuffer.addVoterRecord(name,birthDay);
        }
        if (qName.equals("visit")){
            int station = Integer.parseInt(attributes.getValue("station"));
            LocalDate visitDate()
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("voter")){
            if (votersBuffer.getSize() >= BUFFER_SIZE){
                try {
                    DBConnection.executeMultiInsertVoters(votersBuffer.toString());
                    votersBuffer.clear();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
    }


}

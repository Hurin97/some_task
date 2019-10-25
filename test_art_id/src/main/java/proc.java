import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class proc {

    public static void main(String[] args) throws SQLException, ParserConfigurationException, SAXException, IOException {
        String path = "info.xml";
        System.out.println("First task: ");
        firstQuest(path);
        System.out.println("Second task: ");
        secondQuest(path);
        System.out.println("Third task: ");
        thirdQuest(path);
    }

    public static void firstQuest (String path) {
        try (StAX processor = new StAX(Files.newInputStream(Paths.get(path)))) {
            XMLStreamReader reader = processor.getReader();
            List<String> par_lists = new ArrayList<String>();
            while (reader.hasNext()) {
                int event= reader.next();
                if (event== XMLEvent.START_ELEMENT && "par".equals(reader.getLocalName()) &&
                        "1".equals(reader.getAttributeValue(null,"step"))&&
                        "ВИД_ДОК".equals(reader.getAttributeValue(null,"name"))) {
                    while(!(event==XMLEvent.END_ELEMENT&&"par".equals(reader.getLocalName())))  {
                        event = reader.next();
                        if (event == XMLEvent.START_ELEMENT && "par_list".equals(reader.getLocalName())) {
                            par_lists.add(reader.getAttributeValue(null, "value"));
                        }
                    }

                }
            }
            List<String> sortPar_list=par_lists.stream().sorted().collect(Collectors.toList());
            System.out.println(sortPar_list);


        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void secondQuest (String path) {
        try (StAX processor = new StAX(Files.newInputStream(Paths.get(path)))) {
            XMLStreamReader reader = processor.getReader();
            while (reader.hasNext()) {
                int event= reader.next();

                if (event== XMLEvent.START_ELEMENT && "par".equals(reader.getLocalName()) &&
                        "1".equals(reader.getAttributeValue(null,"step"))&&
                        "ГРАЖДАНСТВО".equals(reader.getAttributeValue(null,"name"))) {

                    for (int i=0;i<reader.getAttributeCount();i++){
                        System.out.println(reader.getAttributeLocalName(i) + ":" + reader.getAttributeValue(i));
                    }

                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void thirdQuest (String path) throws SAXException, ParserConfigurationException, SQLException, IOException {
        dom Dom=new dom();
        Dom.toDbWithDOM(path);
        System.out.println("Third task complete");
    }
}

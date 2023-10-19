import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class PR140Main {
    public static void main(String[] args){
        String file = "./src/persones.xml";

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList listPersones = doc.getElementsByTagName("persona");

            System.out.printf("%-20s%-20s%-20s%-20s%-1s", "Nom", "Cognom", "Edat", "Ciutat", "\n\n");

            for(int i=0; i<listPersones.getLength(); i++){
                Node nodePersona = listPersones.item(i);

                if(nodePersona.getNodeType() == Node.ELEMENT_NODE){
                    Element elm = (Element) nodePersona;

                    NodeList nodeList = elm.getElementsByTagName("nom");
                    String nom = nodeList.item(0).getTextContent();
                    nodeList = elm.getElementsByTagName("cognom");
                    String cognom = nodeList.item(0).getTextContent();
                    nodeList = elm.getElementsByTagName("edat");
                    String edat = nodeList.item(0).getTextContent();
                    nodeList = elm.getElementsByTagName("ciutat");
                    String ciutat = nodeList.item(0).getTextContent();

                    System.out.printf("%-20s%-20s%-20s%-20s%-1s", nom, cognom, edat, ciutat, "\n");
                }
            }

        } catch (Exception e){

        }
    }
}

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.Scanner;

public class PR142Main {
    public static void main(String[] args){
        String path = "./src/cursos.xml";
        File file = new File(path);

        String menu =
                "1)Llistar id's dels cursos\n" +
                        "2)Llistar id's dels tutors\n" +
                        "3)Mostrar total d'alumnes\n" +
                        "4)Mostrar modul\n" +
                        "5)Mostrar alumnes\n" +
                        "6)Afegir alumne\n" +
                        "7)Eliminar alumne\n\nOption:";

        Scanner sc = new Scanner(System.in);

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(file);

            XPath xpath = XPathFactory.newDefaultInstance().newXPath();

            int opt;
            while (true){
                System.out.println(menu);

                opt = getOption(sc);
                manageOption(opt, sc, xpath, document);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }


    public static String makeQuery(XPath xPath, String query, Document document, int type) throws XPathExpressionException {
        Object objectResult;
        String textResult = "";

        switch (type){
            case 0 :
                objectResult = xPath.compile(query).evaluate(document, XPathConstants.NODESET);
                NodeList nodes = (NodeList) objectResult;

                for(int i=0; i<nodes.getLength(); i++){
                    textResult += nodes.item(i).getTextContent() + "\n";
                }

                break;
            case 1 :
                objectResult = xPath.compile(query).evaluate(document, XPathConstants.NODE);
                Node node = (Node) objectResult;
                textResult = node.getTextContent();

                break;
            case 2 :
                objectResult = xPath.compile(query).evaluate(document, XPathConstants.STRING);
                textResult = (String) objectResult;

                break;
        }

        return  textResult;
    }

    public static void manageOption(int opt, Scanner sc, XPath xPath, Document document) throws XPathExpressionException {
        String id;

        switch (opt){
            case 4 :
                id = getId(sc, xPath, document);
                System.out.println(mostrarModuls(id, xPath, document));

                break;
        }
    }

    public static int getOption(Scanner sc){
        while (!sc.hasNextInt()){

        }
        return sc.nextInt();
    }

    public static String mostrarModuls(String idCurs, XPath xPath, Document document) throws XPathExpressionException {
        return makeQuery(xPath,
                "/cursos/curs[@id='" + idCurs + "']/moduls/modul/@id | " +
                        "/cursos/curs[@id='" + idCurs + "']/moduls/modul/titol",
                document,
                0);
    }

    public static String getId(Scanner sc, XPath xPath, Document document) throws XPathExpressionException {
        sc.nextLine();
        System.out.println("ID: ");

        return sc.next();
    }
}

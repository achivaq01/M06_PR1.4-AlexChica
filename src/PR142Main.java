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
    static public boolean running = true;
    public static void main(String[] args){
        String path = "./src/cursos.xml";
        File file = new File(path);

        String menu =
                """
                        1)Llistar id's dels cursos
                        2)Llistar id's dels tutors
                        3)Mostrar total d'alumnes
                        4)Mostrar modul
                        5)Mostrar alumnes
                        6)Afegir alumne
                        7)Eliminar alumne
                        8)Sortir

                        Option:""";

        Scanner sc = new Scanner(System.in);

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(file);

            XPath xpath = XPathFactory.newDefaultInstance().newXPath();

            int opt;
            while (running){
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
            case 3:
                objectResult = xPath.compile(query).evaluate(document, XPathConstants.NUMBER);
                textResult = Double.toString((Double) objectResult);
        }

        return  textResult;
    }

    public static void manageOption(int opt, Scanner sc, XPath xPath, Document document) throws XPathExpressionException {
        String id;

        switch (opt){
            case 1 :
                System.out.println(makeQuery(xPath,
                        "//curs/@id",
                        document,
                        0));
                break;

            case 2 :
                System.out.println(makeQuery(xPath,
                        "//tutor/text()",
                        document,
                        0));
                break;

            case 3:
                System.out.println(makeQuery(xPath,
                        "count(//alumne)",
                        document,
                        3));
                break;

            case 4 :
                id = getId(sc, xPath, document);
                System.out.println(makeQuery(xPath,
                        "/cursos/curs[@id='" + id + "']/moduls/modul/@id | " +
                                "/cursos/curs[@id='" + id + "']/moduls/modul/titol",
                        document,
                        0));

                break;

            case 5:
                id = getId(sc, xPath, document);
                System.out.println(makeQuery(xPath,
                        "//curs[@id='"+ id +"']/alumnes/alumne/text()",
                        document,
                        0));

            case 8:
                running = false;
        }
    }

    public static int getOption(Scanner sc){
        while (!sc.hasNextInt()){
            System.out.println("Enter a valid option.");
            sc.nextLine();

        }
        return sc.nextInt();
    }


    public static String getId(Scanner sc, XPath xPath, Document document) throws XPathExpressionException {
        sc.nextLine();
        System.out.println("ID: ");

        return sc.next();
    }
}

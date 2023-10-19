import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class PR141Main {
    public static void main(String[] args){
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element elmRoot = doc.createElement("biblioteca");
            doc.appendChild(elmRoot);

            Element elmLlibre = doc.createElement("llibre");

            Attr attrId = doc.createAttribute("id");
            attrId.setValue("001");
            elmLlibre.setAttributeNode(attrId);

            Element elmTitol = doc.createElement("titol");
            Text titolText = doc.createTextNode("El viatge dels venturons");
            elmTitol.appendChild(titolText);

            Element elmAutor = doc.createElement("autor");
            Text autorText = doc.createTextNode("Joan pla");
            elmAutor.appendChild(autorText);

            Element elmAny = doc.createElement("anyPublicacio");
            Text anyText = doc.createTextNode("1998");
            elmAny.appendChild(anyText);

            Element elmEditorial = doc.createElement("editorial");
            Text editorialText = doc.createTextNode("Edicions Mar");
            elmEditorial.appendChild(editorialText);

            Element elmGenere = doc.createElement("genere");
            Text genereText = doc.createTextNode("Aventura");
            elmGenere.appendChild(genereText);

            Element elmPagines = doc.createElement("pagines");
            Text paginesText = doc.createTextNode("320");
            elmPagines.appendChild(paginesText);

            Element elmDisponible = doc.createElement("disponible");
            Text disponibleText = doc.createTextNode("true");
            elmDisponible.appendChild(disponibleText);

            elmRoot.appendChild(elmTitol);
            elmRoot.appendChild(elmAutor);
            elmRoot.appendChild(elmAny);
            elmRoot.appendChild(elmEditorial);
            elmRoot.appendChild(elmGenere);
            elmRoot.appendChild(elmPagines);
            elmRoot.appendChild(elmDisponible);

            write("./src/biblioteca.xml", doc);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
    static public void write(String path, Document doc){
        try{
            if(!new File(path).exists()){ new File(path).createNewFile(); }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            //trimWhitespace(doc);

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));

            transformer.transform(source, result);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

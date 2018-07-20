/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.rug.search.tools.bibtools;

import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 *
 * @author hcadavid
 */
public class TEIParser2 {

    public static void main(String... args)
            throws Exception {
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = dbf.newDocumentBuilder().parse(new InputSource(new FileReader("/home/hcadavid/tools/BibTeX-Stats-Analytics-Tools/samples/Gomes_et_al._-_2015_-_A_Systematic_Mapping_on_Discovery_and_Composition_.pdf.tei")));

        XPath xPath = XPathFactory.newInstance().newXPath();
        //Node result = (Node) xPath.evaluate("A/B[id = '1']", doc, XPathConstants.NODE);
        Node title = (Node) xPath.evaluate("TEI/teiHeader/fileDesc/titleStmt/title", doc, XPathConstants.NODE);
        Node refs = (Node) xPath.evaluate("TEI/text/back/div/listBibl", doc, XPathConstants.NODE);

        System.out.println(nodeToString(title));
        System.out.println(nodeToString(refs));
    }

    private static String nodeToString(Node node)
            throws TransformerException {
        StringWriter buf = new StringWriter();
        Transformer xform = TransformerFactory.newInstance().newTransformer();
        xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        xform.transform(new DOMSource(node), new StreamResult(buf));
        return (buf.toString());
    }

}

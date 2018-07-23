/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.rug.search.tools.bibtools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import nl.rug.search.tools.bibtools.snowballing.TEIReferencesParser;
import nl.rug.search.tools.bibtools.snowballing.model.Reference;
import nl.rug.search.tools.teianalytics.teimodel.Author;
import nl.rug.search.tools.teianalytics.teimodel.BiblStruct;
import nl.rug.search.tools.teianalytics.teimodel.Imprint;
import nl.rug.search.tools.teianalytics.teimodel.ListBibl;
import nl.rug.search.tools.teianalytics.teimodel.Monogr;
import nl.rug.search.tools.teianalytics.teimodel.PersName;
import nl.rug.search.tools.teianalytics.teimodel.TEI;
import nl.rug.search.tools.teianalytics.teimodel.Title;

/**
 *
 * @author hcadavid
 */
public class TEIRefExtractor {
    
    public static void main(String args[]) throws JAXBException, FileNotFoundException {
        
        /*if (args.length != 1) {
            System.err.println("Usage: java TEIRefExtractor teifile");
            System.exit(1);
        }*/
        
        Set<Reference> refs= TEIReferencesParser.extract(new FileInputStream("/Users/hcadavid/RUG/Tools/QGS-Calculator/samples/Gomes_et_al._-_2015_-_A_Systematic_Mapping_on_Discovery_and_Composition_.pdf.tei"));
        
        for (Reference ref:refs){
            System.out.println(ref);
        }
        
        
    }
    
}

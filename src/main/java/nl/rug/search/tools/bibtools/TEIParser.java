/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.rug.search.tools.bibtools;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
public class TEIParser {
    
    public static void main(String args[]) throws JAXBException {
        File file = new File("/home/hcadavid/tools/BibTeX-Stats-Analytics-Tools/samples/Gomes_et_al._-_2015_-_A_Systematic_Mapping_on_Discovery_and_Composition_.pdf.tei");
        JAXBContext jaxbContext = JAXBContext.newInstance(TEI.class);

        Unmarshaller jaxbUnmarshaller;
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        
        TEI tei = (TEI) jaxbUnmarshaller.unmarshal(file);
        
        Title paper_title=(Title)tei.getTeiHeader().getFileDesc().getTitleStmt().getTitle().get(0);
        
        System.out.println(paper_title.getContent().get(0));
        
        
        ListBibl lb=((ListBibl)tei.getText().getBack().getDiv().getPOrTrashOrBibl().get(0));

        int numrefs=lb.getTeiModelBiblLike().size();
        
        System.out.println(numrefs);
        
        for (int i=0;i<numrefs;i++){
            
            BiblStruct ref1=(BiblStruct)lb.getTeiModelBiblLike().get(i);
            
            
            //Author aut=(Author)monogrref1.getImprintOrAuthorOrEditor().get(1);
            //Imprint imp=(Imprint)monogrref1.getImprintOrAuthorOrEditor().get(2);
                        
            if (ref1.getAnalytic().size()>0){
                Title titlref1=(Title)ref1.getAnalytic().get(0).getAuthorOrEditorOrTitle().get(0);
                
                System.out.println(titlref1.getContent().get(0));
                
                if (ref1.getAnalytic().get(0).getAuthorOrEditorOrTitle().get(1) instanceof Author){
                    Author authorref1 = (Author)ref1.getAnalytic().get(0).getAuthorOrEditorOrTitle().get(1);
                    System.out.println(","+((PersName)authorref1.getContent().get(1)));
                }
                else{
                    System.out.println(",no author");
                }
                
                
            }//empty analytic, title is in monog
            else{
                Monogr monogrref1 = (Monogr)ref1.getMonogrAndSeries().get(0);            
                Title venue_title=(Title)monogrref1.getImprintOrAuthorOrEditor().get(0);
            }
            //Title titlref1=(Title)ref1.getAnalytic().get(0).getAuthorOrEditorOrTitle().get(0);                
            //Author authref1=(Author)ref1.getAnalytic().get(i).getAuthorOrEditorOrTitle().get(1);
            //System.out.println(titlref1.getContent().get(0));
            //System.out.println(authref1.getContent().get(0));
        }
        
        
        
        
        
        
        //System.out.println(((BiblStruct)((ListBibl)tei.getText().getBack().getDiv().getPOrTrashOrBibl().get(0)).getTeiModelBiblLike().get(0)).getId());
        //System.out.println(customer);
    }
    
}

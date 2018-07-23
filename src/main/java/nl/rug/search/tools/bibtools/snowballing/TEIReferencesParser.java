/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.rug.search.tools.bibtools.snowballing;

import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import nl.rug.search.tools.bibtools.snowballing.model.ComparisonHeuristic;
import nl.rug.search.tools.bibtools.snowballing.model.Reference;
import nl.rug.search.tools.teianalytics.teimodel.Author;
import nl.rug.search.tools.teianalytics.teimodel.BiblStruct;
import nl.rug.search.tools.teianalytics.teimodel.ListBibl;
import nl.rug.search.tools.teianalytics.teimodel.Monogr;
import nl.rug.search.tools.teianalytics.teimodel.PersName;
import nl.rug.search.tools.teianalytics.teimodel.TEI;
import nl.rug.search.tools.teianalytics.teimodel.Title;

/**
 *
 * @author hcadavid
 */
public class TEIReferencesParser {

    public static Set<Reference> extract(InputStream is) {
        Set<Reference> rs = new LinkedHashSet<>();

        try {

            ComparisonHeuristic ch = new ComparisonHeuristic() {
                @Override
                public boolean sameRerefence(Reference ref1, Reference ref2) {
                    return ref1.getTitle().equals(ref2.getTitle());
                }
            };

            JAXBContext jaxbContext = JAXBContext.newInstance(TEI.class);

            Unmarshaller jaxbUnmarshaller;
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            TEI tei = (TEI) jaxbUnmarshaller.unmarshal(is);

            Title paperTitle = (Title) tei.getTeiHeader().getFileDesc().getTitleStmt().getTitle().get(0);

            String paperTitleContent = (String) paperTitle.getContent().get(0);

            ListBibl lb = ((ListBibl) tei.getText().getBack().getDiv().getPOrTrashOrBibl().get(0));

            int numrefs = lb.getTeiModelBiblLike().size();

            for (int i = 0; i < numrefs; i++) {

                Reference ref = new Reference(ch);

                ref.setReferencedFrom(paperTitleContent);

                BiblStruct biblstruct = (BiblStruct) lb.getTeiModelBiblLike().get(i);

                if (biblstruct.getAnalytic().size() > 0) {
                    Title titlref1 = (Title) biblstruct.getAnalytic().get(0).getAuthorOrEditorOrTitle().get(0);

                    ref.setTitle(titlref1.getContent().get(0).toString());

                    if (biblstruct.getAnalytic().get(0).getAuthorOrEditorOrTitle().get(1) instanceof Author) {
                        Author authorref1 = (Author) biblstruct.getAnalytic().get(0).getAuthorOrEditorOrTitle().get(1);

                        ref.addAuthorSurname(((PersName) authorref1.getContent().get(1)).getContent().get(0).toString());

                    } else {

                        ref.addAuthorSurname("UNDEFINED");

                    }

                }//empty analytic, title is in monog
                else {

                    Monogr monogrref1 = (Monogr) biblstruct.getMonogrAndSeries().get(0);
                    Title reftitle = (Title) monogrref1.getImprintOrAuthorOrEditor().get(0);

                    ref.setTitle(reftitle.getContent().get(0).toString());
                }
                //Title titlref1=(Title)ref1.getAnalytic().get(0).getAuthorOrEditorOrTitle().get(0);                
                //Author authref1=(Author)ref1.getAnalytic().get(i).getAuthorOrEditorOrTitle().get(1);
                //System.out.println(titlref1.getContent().get(0));
                //System.out.println(authref1.getContent().get(0));
                
                rs.add(ref);
            }

        } catch (JAXBException ex) {
            Logger.getLogger(TEIReferencesParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

}

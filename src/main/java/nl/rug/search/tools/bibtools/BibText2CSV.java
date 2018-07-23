/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.rug.search.tools.bibtools;

import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;
import static nl.rug.search.tools.bibtools.BibtexSetTools.printReference;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.jbibtex.ParseException;
import org.jbibtex.StringValue;
import org.jbibtex.Value;

/**
 *
 * @author hcadavid
 */
public class BibText2CSV {

    public static void main(String args[]) throws FileNotFoundException, ParseException {
        
        if (args.length != 1) {
            System.err.println("usage: path-to-bib-file");
            return;
        }

        
        Reader bibtx1Reader = new FileReader(new File(args[0]));

        org.jbibtex.BibTeXParser bibtex1Parser = new org.jbibtex.BibTeXParser();

        org.jbibtex.BibTeXDatabase bibtxdb1 = bibtex1Parser.parse(bibtx1Reader);

        Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap1 = bibtxdb1.getEntries();

        System.out.println("bibtex 1:" + "(" + args[0] + "):" + entryMap1.size());

        for (org.jbibtex.Key k : entryMap1.keySet()) {
            BibTeXEntry bibte = entryMap1.get(k);
            printReference(k, bibte);
        }

    }

    public static void printReference(org.jbibtex.Key k, BibTeXEntry bte) {

        StringBuilder sb = new StringBuilder();

        sb.append(k.toString());

        sb.append(",");

        String strtitle = null;
        
        Value title = bte.getField(new Key("Title"));

        if (title==null){
            strtitle = "UNDEFINED";
        }
        else{
            strtitle = ((StringValue) title).getString();
        }

        if (strtitle.contains(",")) {
            sb.append("\"");
            sb.append(strtitle);
            sb.append("\"");
        } else {
            sb.append(strtitle);
        }

        sb.append(",");

        Value doi = bte.getField(new Key("DOI"));

        if (doi != null) {
            sb.append(((StringValue) doi).getString());
        }

        sb.append(",");

        Value kw = bte.getField(new Key("keywords"));

        if (kw != null) {
            sb.append(((StringValue) kw).getString());
        }

        System.out.println(sb.toString());

    }

}

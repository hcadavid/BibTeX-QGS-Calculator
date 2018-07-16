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
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.jbibtex.ParseException;
import org.jbibtex.StringValue;
import org.jbibtex.TokenMgrException;
import org.jbibtex.Value;

/**
 *
 * @author hcadavid
 */
public class BibtexSetTools {

    public static void main(String args[]) {

                
        if (args.length < 3 || (args[1].equals("SD") && args[1].equals("U")) ) {
            
            System.err.println("Usage: java " + BibtexSetTools.class + " bibtex-file1 [OPERATION] bibtex-file2 [fields]");            
            
            System.err.println("[OPERATION]s: ");
            System.err.println("                [SD] Symetric Difference");            
            System.err.println("                [U] Union");                        
            
            System.exit(0);
        }
        
        //Sets.u
        
        Reader bibtx1Reader,bibtx2Reader;
        try {
            bibtx1Reader = new FileReader(new File(args[0]));
            bibtx2Reader = new FileReader(new File(args[2]));            
            org.jbibtex.BibTeXParser bibtex1Parser = new org.jbibtex.BibTeXParser();
            org.jbibtex.BibTeXDatabase bibtxdb1 = bibtex1Parser.parse(bibtx1Reader);            
            org.jbibtex.BibTeXParser bibtex2Parser = new org.jbibtex.BibTeXParser();
            org.jbibtex.BibTeXDatabase bibtxdb2 = bibtex2Parser.parse(bibtx2Reader);            
            
            Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> combinedMaps = bibtxdb1.getEntries();
            
            //combinedMaps.putAll(bibtxdb2.getEntries());
                                    
            Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap1 = bibtxdb1.getEntries();
            Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap2 = bibtxdb2.getEntries();
                         
            System.out.println("bibtex 1:"+"("+args[0]+"):"+entryMap1.size());
            System.out.println("bibtex 2:"+"("+args[2]+"):"+entryMap2.size());
            
            Sets.SetView<org.jbibtex.Key> sdiffview= Sets.symmetricDifference(entryMap1.keySet(), entryMap2.keySet());
                                               
            System.out.println("sep=;");  
            
            System.out.println("Bibtex-key,title,DOI,keywords");
            
            for (org.jbibtex.Key k:sdiffview.immutableCopy()){
                printReference(k, entryMap1, entryMap2);
            }
            
            
        } catch (FileNotFoundException ex) {
            System.err.println("Wrong arguments: File not found:"+ex.getLocalizedMessage());
        } catch (ParseException | TokenMgrException ex) {
            System.err.println("Invalid/Malforned input files:"+ex.getLocalizedMessage());
        } 

    }

    
    public static void printReference(org.jbibtex.Key k, Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap1,Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap2) {
        BibTeXEntry bte=null;
        
        if (entryMap1.containsKey(k)){
            bte=entryMap1.get(k);
        }
        else{
            bte=entryMap2.get(k);
        }
        
        StringBuilder sb=new StringBuilder();
        
        sb.append(k.toString());
        
        sb.append(",");
        
        Value title=bte.getField(new Key("Title")); 
        
        String strtitle=((StringValue)title).getString();
        
        if (strtitle.contains(",")){
            sb.append("\"");
            sb.append(strtitle);
            sb.append("\"");                   
        }
        else{
            sb.append(strtitle);
        }
        
        sb.append(",");
        
        Value doi=bte.getField(new Key("DOI"));
        
        if (doi!=null){
            sb.append(((StringValue)doi).getString());            
        }

        sb.append(",");
        
        Value kw=bte.getField(new Key("keywords"));

        if (kw!=null){
            sb.append(((StringValue)kw).getString());
        }
                
        System.out.println(sb.toString());
        

    }
    
}

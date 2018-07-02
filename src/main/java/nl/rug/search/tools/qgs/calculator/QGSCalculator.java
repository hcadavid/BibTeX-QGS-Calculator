/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.rug.search.tools.qgs.calculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.jbibtex.Key;
import org.jbibtex.ParseException;
import org.jbibtex.TokenMgrException;

/**
 *
 * @author h.f.cadavid.rengifo@rug.nl
 */
public class QGSCalculator {

    public static void main(String args[]) {

        //System.err.println("Quasi-Gold-Standard Calculator for Systematic Reviews Search String Assessment.");
        
        if (args.length < 2) {
            System.err.println("Usage: java " + QGSCalculator.class + " <QGS-bibtex-file> <Automated-search-bibtex1> <Automated-search-bibtex2> ... <Automated-search-bibtexN>");
            System.exit(0);
        }
        
        LinkedList<Key> relevantStudiesNotRetrieved=null;
        Reader sgsReader;
        try {
            sgsReader = new FileReader(new File(args[0]));
            org.jbibtex.BibTeXParser bibtexParser = new org.jbibtex.BibTeXParser();
            org.jbibtex.BibTeXDatabase sgsDatabase = bibtexParser.parse(sgsReader);            
            Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> qgsEntryMap = sgsDatabase.getEntries();
            
            System.out.println("sep=,");
            System.out.println("Quasi-Gold-Standard file name:"+args[0]);
            System.out.println("datasource,relevantStudies, retrivedStudies, relevantStudiesRetrieved, sensitivity, precision, QGS missing studies");
            for (int i=1;i<args.length;i++){
                relevantStudiesNotRetrieved=new LinkedList<>();
                float[] data=getData(qgsEntryMap, args[i],relevantStudiesNotRetrieved);                                  
                StringBuilder rsnrbuff=new StringBuilder();
                for (Key rsnr:relevantStudiesNotRetrieved){
                    rsnrbuff.append(rsnr.toString());
                    rsnrbuff.append(";");
                }
                
                System.out.println(args[i]+","+data[0]+","+data[1]+","+data[2]+","+data[3]+","+data[4]+","+rsnrbuff.toString());                

            }
        } catch (FileNotFoundException ex) {
            System.err.println("Wrong arguments: File not found:"+ex.getLocalizedMessage());
        } catch (ParseException | TokenMgrException ex) {
            System.err.println("Invalid/Malforned input files:"+ex.getLocalizedMessage());
        } 

    }

    /**
     * 
     * @param qgsEntryMap
     * @param asBibtexFilePath
     * @return [relevantStudies, retrivedStudies, relevantStudiesRetrieved, sensitivity, precision]
     * @throws FileNotFoundException
     * @throws ParseException 
     */
    private static float[] getData(Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> qgsEntryMap,String asBibtexFilePath, LinkedList<Key> relevantStudiesNotRetrieved) throws FileNotFoundException, ParseException{

        org.jbibtex.BibTeXParser bibtexParser = new org.jbibtex.BibTeXParser();
        Reader asReader = new FileReader(new File(asBibtexFilePath));
        org.jbibtex.BibTeXDatabase asDatabase = bibtexParser.parse(asReader);

        Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> asEntryMap = asDatabase.getEntries();
                
        int relevantStudiesCount = qgsEntryMap.size();

        int retrievedStudiesCount = asEntryMap.size();

        int relevantStudiesRetrivedCount = getRelevantStudiesRetrievedCount(qgsEntryMap, asEntryMap,relevantStudiesNotRetrieved);

        float sensitivity = (float) relevantStudiesRetrivedCount / (float) relevantStudiesCount;

        float precision = (float) relevantStudiesRetrivedCount / (float) retrievedStudiesCount;

        return new float[]{relevantStudiesCount,retrievedStudiesCount,relevantStudiesRetrivedCount, sensitivity,precision};
        
    }
    
    
    private static int getRelevantStudiesRetrievedCount(Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> sgsEntryMap, Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> asEntryMap, List<Key> relevantStudiesNotRetrieved) {
        int matchCount = 0;
        for (Key entry : sgsEntryMap.keySet()) {
            if (asEntryMap.get(entry) != null) {
                matchCount++;
            }
            else{
                relevantStudiesNotRetrieved.add(entry);                
            }
        }
        return matchCount;
    }

}
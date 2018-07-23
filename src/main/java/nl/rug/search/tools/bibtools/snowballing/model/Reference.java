/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.rug.search.tools.bibtools.snowballing.model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hcadavid
 */
public class Reference {

    private String referencedFrom;
    
    private String title;
    
    private List<String> authorsSurnames;
    
    private int year;
    
    private String venue;
    
    private ComparisonHeuristic comph;

    public Reference(String title, List<String> authors, int year, String venue, ComparisonHeuristic comph) {
        this.title = title;
        this.authorsSurnames = authors;
        this.year = year;
        this.venue = venue;
        this.comph = comph;
    }

    public Reference(ComparisonHeuristic comph) {
        this.comph = comph;
        authorsSurnames = new LinkedList<>();
    }

    public void setReferencedFrom(String referencedFrom) {
        this.referencedFrom = referencedFrom;
    }

    public void addAuthorSurname(String sn){
        authorsSurnames.add(sn);
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    
    
    public String getTitle() {
        return title;
    }

    public List<String> getAuthorsSurnames() {
        return authorsSurnames;
    }

    public int getYear() {
        return year;
    }

    public String getVenue() {
        return venue;
    }

    @Override
    public String toString() {
        return "Reference{" + "referencedFrom=" + referencedFrom + ", title=" + title + ", authorsSurnames=" + authorsSurnames + ", year=" + year + ", venue=" + venue + '}';
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Reference){
            return comph.sameRerefence(this, (Reference)obj);
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
}

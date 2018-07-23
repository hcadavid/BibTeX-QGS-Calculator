/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.rug.search.tools.bibtools.snowballing.model;

/**
 *
 * @author hcadavid
 */
public interface ComparisonHeuristic {
    
    public boolean sameRerefence(Reference ref1,Reference ref2);
    
    
    
}

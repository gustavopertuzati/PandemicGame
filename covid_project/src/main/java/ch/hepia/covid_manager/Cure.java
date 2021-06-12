package ch.hepia.covid_manager;

import java.util.List;
import java.util.ArrayList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class Cure{
  
    private double impact; // impact of the cure on the virus -> 0 at first, then when progression == 100% -> grows
    private double progression; // current state of cure developpement
  
    private List<PropertyChangeListener> lstObservers;

    //Design Pattern Singleton
    private static Cure cure;

    public static Cure getInstance(){
        if (Cure.cure == null){
            Cure.cure = new Cure();
        }
        return Cure.cure;
    }
  
    private Cure(){
        //Takes constants "similar" to the real covid-19's "stats"
        this.impact =  0.0; 
        this.progression = 0.0;
    }

    public double impact(){
        return this.impact;
    }

    public double progression(){
        return this.progression;
    }

    public void updateCure(){
        if(this.progression < 100){
            this.progression += 0.05;
        } else{
            this.impact += 0.05;
        } 
        this.updateObservers();
    }

    public void updateObservers(){
        this.lstObservers.forEach(i->
            i.propertyChange(new PropertyChangeEvent(this, "cure", new Double(this.progression - 0.05), new Double(this.progression)))
        );
    }

    public void addListener(PropertyChangeListener pcl){
        this.lstObservers.add(pcl);
    }
}
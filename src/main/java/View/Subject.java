package View;

import Model.ArticolEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;


public class Subject {

    private List<Observer> observers = new ArrayList<Observer>();
    private ObservableList<ArticolEntity> articolEntities;

    public Subject() {
        articolEntities=FXCollections.observableArrayList();
    }

    public ObservableList<ArticolEntity> getArticolEntities() {
        return articolEntities;
    }

    public void setArticolEntities(ObservableList<ArticolEntity> articolEntities) {
        this.articolEntities = articolEntities;
        notifyAllObservers();
    }


    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }

}

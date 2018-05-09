package View;

import Model.ArticolEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;


public class Subject {

    private List<Observer> observers = new ArrayList<Observer>();
    private ObservableList<ArticolEntity> articolEntities;
    private ObservableList<String> titleArticolEntities;

    public Subject() {
        articolEntities=FXCollections.observableArrayList();
        titleArticolEntities=FXCollections.observableArrayList();
    }

    public ObservableList<ArticolEntity> getArticolEntities() {
        return articolEntities;
    }

    public void setArticolEntities(ObservableList<ArticolEntity> articolEntities) {
        this.articolEntities = articolEntities;

       ObservableList<String> list=FXCollections.observableArrayList();
       for(ArticolEntity articolEntity:articolEntities)
       {
           list.add(articolEntity.getTitlu());
       }

       this.titleArticolEntities=list;

        notifyAllObservers();
    }

    public ObservableList<String> getTitleArticolEntities() {
        return titleArticolEntities;
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

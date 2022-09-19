package prj.pt.assignment4.BusinessLayer;

import prj.pt.assignment4.PresentationLayer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    private List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

    protected void notifyObservers(T source, String propertyName, Object newValue) {
        final PropertyChanged<T> changedEventArgs = new PropertyChanged<>(source, propertyName, newValue);
        for (Observer<T> observer : observers) {
            observer.update(changedEventArgs);
        }

    }
}

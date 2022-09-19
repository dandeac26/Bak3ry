package prj.pt.assignment4.PresentationLayer;

import prj.pt.assignment4.BusinessLayer.PropertyChanged;

public interface Observer<T> {
    void update(PropertyChanged<T> args);
}

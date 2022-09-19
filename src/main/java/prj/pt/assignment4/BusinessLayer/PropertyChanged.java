package prj.pt.assignment4.BusinessLayer;

public class PropertyChanged<T> {
    public T source;
    public String propertyName;
    public Object newValue;

    public PropertyChanged(T source, String propertyName, Object newValue) {
        this.source = source;
        this.propertyName = propertyName;
        this.newValue = newValue;
    }

    @Override
    public String toString() {
        return "PropertyChanged{" +
                "source=" + source +
                ", propertyName='" + propertyName + '\'' +
                ", newValue=" + newValue +
                '}';
    }
}

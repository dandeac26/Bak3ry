package prj.pt.assignment4.BusinessLayer;

public abstract class MenuItem {
    protected String title;
    protected Integer price;
    public abstract Integer computePrice();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

package prj.pt.assignment4.BusinessLayer;

public class BaseProduct extends MenuItem {

    private Double rating;
    private Integer calories;
    private Integer protein;
    private Integer fat;
    private Integer sodium;
    //private Integer price;

    public BaseProduct() {

    }

    public BaseProduct(String title, Double rating, Integer calories,
                       Integer protein, Integer fat, Integer sodium, Integer price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }

    public Integer computePrice() {
        return price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getProtein() {
        return protein;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public Integer getSodium() {
        return sodium;
    }

    public void setSodium(Integer sodium) {
        this.sodium = sodium;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return
                "title='" + title +
                        ", rating=" + rating +
                        ", calories=" + calories +
                        ", protein=" + protein +
                        ", fat=" + fat +
                        ", sodium=" + sodium +
                        ", price=" + price
                ;
    }
}

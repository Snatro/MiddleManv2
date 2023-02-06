package entities;

public class Supply extends Name{
    private Category category;

    public Supply(int id, String name,Category category) {
        super(id, name);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

package entities;

public record Category(int id,String name) {
    @Override
    public String toString() {
        return name;
    }
}

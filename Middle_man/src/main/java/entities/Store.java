package entities;

public class Store extends Name {
    private Person manager;
    private String city;
    private String address;


    public Store(int id, String name, Person manager,String city,String address) {
        super(id, name);
        this.manager = manager;
        this.city = city;
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person getManager() {
        return manager;
    }

    public void setManager(Person manager) {
        this.manager = manager;
    }
}



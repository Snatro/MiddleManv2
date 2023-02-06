package entities;

public class Person extends Name{

    private Role role;

    public Person(int id, String name,Role role) {
        super(id, name);
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return this.getName() + ", " + role.getCode();
    }
}

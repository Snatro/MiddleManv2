package entities;

public class Role extends Name{
    private String code;

    public Role(int id, String name,String code) {
        super(id, name);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return getName() + ", " + getCode();
    }
}

package Integrate;

public class Register {

    private String name;
    private String purpose;
    private int value;

    public Register(String name, String purpose, int value){
        this.name=name;
        this.purpose=purpose;
        this.value=value;
    }

    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

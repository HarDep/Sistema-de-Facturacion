package co.edu.uptc.pojo;

public class Person implements Cloneable{
    private String documentTye;
    private String documentNumber;
    private String name;
    private String lastName;
    private String resDirection;
    private String city;

    public Person(String documentTye, String documentNumber, String name, String lastName, String resDirection, String city) {
        this.documentTye = documentTye;
        this.documentNumber = documentNumber;
        this.name = name;
        this.lastName = lastName;
        this.resDirection = resDirection;
        this.city = city;
    }

    public String getDocumentTye() {
        return documentTye;
    }

    public void setDocumentTye(String documentTye) {
        this.documentTye = documentTye;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getResDirection() {
        return resDirection;
    }

    public void setResDirection(String resDirection) {
        this.resDirection = resDirection;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public Person clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

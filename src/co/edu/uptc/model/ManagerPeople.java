package co.edu.uptc.model;
import co.edu.uptc.model.simpleList.UPTCList;
import co.edu.uptc.pojo.Person;

import java.util.List;

public class ManagerPeople {
    private final List<Person> people;

    public ManagerPeople() {
        this.people = new UPTCList<>();
        //añadir valores predeterminados
        people.add(new Person("C.C.","1","juan","perez","xx","ii"));
        people.add(new Person("T.I.","1","pedro","perez","xx","ii"));
        people.add(new Person("C.C.","2","carlos","perez","xx","ii"));
    }

    public boolean isThisPerson(String docType,String docNum){
        for (Person person:people) {
            if (person.getDocumentTye().equals(docType)&&person.getDocumentNumber().equals(docNum)){
                return true;
            }
        }
        return false;
    }
    public boolean isThisPerson(String docType,String docNum,Person per){
        for (Person person:people) {
            if ((!isTheSamePerson(person,per))&&person.getDocumentTye().equals(docType)&&person.getDocumentNumber().equals(docNum)){
                return true;
            }
        }
        return false;
    }
    private boolean isTheSamePerson(Person person1,Person person2){
        return person1.getDocumentTye().equals(person2.getDocumentTye())&&
                person1.getDocumentNumber().equals(person2.getDocumentNumber())&&
                person1.getName().equals(person2.getName())&&
                person1.getLastName().equals(person2.getLastName())&&
                person1.getResDirection().equals(person2.getResDirection())&&
                person1.getCity().equals(person2.getCity());
    }
    public void addPerson(Person person){
        people.add(person.clone());
    }
    public Person getPerson(String docType,String docNum){
        for (Person person:people) {
            if (person.getDocumentTye().equals(docType)&&person.getDocumentNumber().equals(docNum)){
                return person.clone();
            }
        }
        return null;
    }
    public void deletePerson(Person person) {
        Person remove = null;
        for (Person per:people) {
            if (per.getDocumentTye().equals(person.getDocumentTye())&&per.getDocumentNumber().equals(person.getDocumentNumber())){
                remove = per;
                break;
            }
        }
        people.remove(remove);
    }
    public void updatePerson(Person personToUpdate,String typeIdToChange,String idNumToChange,String nameToChange,
                             String lastNameToChange,String resDirToChange,String cityResToChange){
        for (Person person:people) {
            if (person.getDocumentNumber().equals(personToUpdate.getDocumentNumber())&&
                    person.getDocumentTye().equals(personToUpdate.getDocumentTye())){
                person.setDocumentTye(new String(typeIdToChange));
                person.setDocumentNumber(new String(idNumToChange));
                person.setName(new String(nameToChange));
                person.setLastName(new String(lastNameToChange));
                person.setResDirection(new String(resDirToChange));
                person.setCity(new String(cityResToChange));
                break;
            }
        }
    }
    public List<Person> getListPeople(){
        List<Person> list = new UPTCList<>();
        for (Person person:people) {
            list.add(person.clone());
        }
        return list;
    }
}

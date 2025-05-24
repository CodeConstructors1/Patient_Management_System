public class Patient {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String disease;

    // Constructor for when adding a patient
    public Patient(String name, int age, String gender, String disease) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
    }

    // Constructor for when retrieving from the database
    public Patient(int id, String name, int age, String gender, String disease) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    @Override
    public String toString() {
        return "Patient{id=" + id + ", name='" + name + "', age=" + age + ", gender='" + gender + "', disease='" + disease + "'}";
    }
}

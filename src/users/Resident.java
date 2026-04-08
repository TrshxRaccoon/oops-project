package users;

public class Resident extends User {

    private String course;

    public static class Guardian {
        private final String name;
        private final String phone;

        public Guardian(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }

        public void display() {
            System.out.println("Guardian: " + name + " | Phone: " + phone);
        }
    }

    private Guardian guardian;

    public Resident(String id, String name) {
        super(id, name);
    }

    public Resident(String id, String name, String course, Guardian guardian) {
        super(id, name);
        this.course = course;
        this.guardian = guardian;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }

    @Override
    public void display() {
        System.out.println("Resident ID: " + id + " | Name: " + name + " | Course: " + course);
        if (guardian != null) {
            guardian.display();
        }
    }
}
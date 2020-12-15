package pl.jaworskimateuszm.myleagues.model;

public class NewUser extends User{

    private String newPassword;
    private String repeatedNewPassword;
    private String actualPassword;


    public NewUser(long userId, String username, String password, int enabled, String role, String name, String surname, long pesel, long phoneNumber) {
        super(userId, username, password, enabled, role, name, surname, pesel, phoneNumber);
    }

    public NewUser(String username, String password, int enabled, String role, String name, String surname, long pesel, long phoneNumber) {
        super(username, password, enabled, role, name, surname, pesel, phoneNumber);
    }

    public NewUser(String username, String password, int enabled, String role, String name, String surname, long pesel, long phoneNumber, String newPassword, String repeatedNewPassword, String actualPassword) {
        this(username, password, enabled, role, name, surname, pesel, phoneNumber);
        this.newPassword = newPassword;
        this.repeatedNewPassword = repeatedNewPassword;
        this.actualPassword = actualPassword;
    }

    public NewUser() {
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatedNewPassword() {
        return repeatedNewPassword;
    }

    public void setRepeatedNewPassword(String repeatedNewPassword) {
        this.repeatedNewPassword = repeatedNewPassword;
    }

    public String getActualPassword() {
        return actualPassword;
    }

    public void setActualPassword(String actualPassword) {
        this.actualPassword = actualPassword;
    }
}

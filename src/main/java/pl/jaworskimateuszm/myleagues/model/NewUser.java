package pl.jaworskimateuszm.myleagues.model;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;

public class NewUser {

    @Size(max = 45, message= "Maksymalna długość to 45 znaków.")
    private String username;
    @Size(max = 64, message= "Maksymalna długość to 64 znaków.")
    private String password;
    @Size(max = 64, message= "Maksymalna długość to 64 znaków.")
    private String repeatedPassword;
    private int enabled;
    private String role;
    @NotEmpty
    @Size(max = 45, message= "Maksymalna długość to 45 znaków.")
    private String name;
    @NotEmpty
    @Size(max = 45, message= "Maksymalna długość to 45 znaków.")
    private String surname;
    @NotNull
    @NumberFormat
    @Min(0)
    private long pesel;
    @NotNull
    @NumberFormat
    @Min(0)
    private long phoneNumber;

    public NewUser() {
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

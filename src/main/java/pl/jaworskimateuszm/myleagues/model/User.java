package pl.jaworskimateuszm.myleagues.model;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

    private long userId;
    @Size(max = 45, message= "Maksymalna długość to 45 znaków.")
    private String username;
    @Size(max = 64, message= "Maksymalna długość to 64 znaków.")
    private String password;
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

    private String actualPassword;
    private String newPassword;
    private String repeatNewPassword;

    private int[] leagueIds;

    public User(String username, String password, int enabled, String role, String name, String surname, long pesel, long phoneNumber) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
    }

    public String getActualPassword() {
        return actualPassword;
    }

    public void setActualPassword(String actualPassword) {
        this.actualPassword = actualPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }

    public void setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
    }

    public User() {
    }

    public int[] getLeagueIds() {
        return leagueIds;
    }

    public void setLeagueIds(int[] leagueIds) {
        this.leagueIds = leagueIds;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

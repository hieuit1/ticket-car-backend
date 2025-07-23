package spring_project.dto;

public class DriverRequets {

    private Long driverId;
    private String fullName;
    private Long phoneNumber;
    private Long yearOfBirth;
    private String descriptions;
    private String gender;
    private String url;
    private String publicId;

    public DriverRequets() {

    }

    public DriverRequets(String fullName, Long phoneNumber, Long yearOfBirth, String descriptions, String gender) {
        this.gender = gender;
        this.descriptions = descriptions;
        this.yearOfBirth = yearOfBirth;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
    }


    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Long getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(Long yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

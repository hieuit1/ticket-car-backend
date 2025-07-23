package spring_project.dto;

public class CoachRequets {

    private Long coachId;
    private Long licensePlateNumberCoach;
    private String coachName;
    private String url;
    private String publicId;

    public CoachRequets() {

    }

    public CoachRequets(String coachName, Long licensePlateNumberCoach) {
        this.licensePlateNumberCoach = licensePlateNumberCoach;
        this.coachName = coachName;
    }

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }


    public void setLicensePlateNumberCoach(Long licensePlateNumberCoach) {
        this.licensePlateNumberCoach = licensePlateNumberCoach;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public Long getLicensePlateNumberCoach() {
        return licensePlateNumberCoach;
    }

    public String getCoachName() {
        return coachName;
    }

    public String getUrl() {
        return url;
    }

    public String getPublicId() {
        return publicId;
    }

}

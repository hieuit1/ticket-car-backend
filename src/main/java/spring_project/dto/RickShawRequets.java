package spring_project.dto;

public class RickShawRequets {
    private Long rickShawId;
    private String rickShawfullName;
    private Long rickShawphoneNumber;
    private Long rickShawyearOfBirth;
    private String rickShawdescriptions;
    private String rickShawgender;
    private String url;
    private String publicId;

    public RickShawRequets() {
    }

    public RickShawRequets(String rickShawfullName, Long rickShawphoneNumber, Long rickShawyearOfBirth, String rickShawdescriptions, String rickShawgender) {
        this.rickShawfullName = rickShawfullName;
        this.rickShawphoneNumber = rickShawphoneNumber;
        this.rickShawyearOfBirth = rickShawyearOfBirth;
        this.rickShawdescriptions = rickShawdescriptions;
        this.rickShawgender = rickShawgender;
    }

    public Long getRickShawId() {
        return rickShawId;
    }

    public void setRickShawId(Long rickShawId) {
        this.rickShawId = rickShawId;
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

    public Long getRickShawphoneNumber() {
        return rickShawphoneNumber;
    }

    public void setRickShawphoneNumber(Long rickShawphoneNumber) {
        this.rickShawphoneNumber = rickShawphoneNumber;
    }

    public String getRickShawfullName() {
        return rickShawfullName;
    }

    public void setRickShawfullName(String rickShawfullName) {
        this.rickShawfullName = rickShawfullName;
    }

    public Long getRickShawyearOfBirth() {
        return rickShawyearOfBirth;
    }

    public void setRickShawyearOfBirth(Long rickShawyearOfBirth) {
        this.rickShawyearOfBirth = rickShawyearOfBirth;
    }

    public String getRickShawdescriptions() {
        return rickShawdescriptions;
    }

    public void setRickShawdescriptions(String rickShawdescriptions) {
        this.rickShawdescriptions = rickShawdescriptions;
    }

    public String getRickShawgender() {
        return rickShawgender;
    }

    public void setRickShawgender(String rickShawgender) {
        this.rickShawgender = rickShawgender;
    }
}



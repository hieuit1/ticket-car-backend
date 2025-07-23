package spring_project.dto;

public class PaymentRequest {
    private String name;
    private String phone;
    private String email;
    private String seats;
    private String paymentMethod;
    private String tripName;
    private String departureDate;
    private String departureTime;
    private String departureEndTime;
    private String pickupPoint;
    private String payPonit;
    private String priceSeatNumber;
    private String coachName;
    private String licensePlateNumberCoach;
    private String totalPrice;
    private String url;

    public PaymentRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureEndTime() {
        return departureEndTime;
    }

    public void setDepartureEndTime(String departureEndTime) {
        this.departureEndTime = departureEndTime;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public String getPayPonit() {
        return payPonit;
    }

    public void setPayPonit(String payPonit) {
        this.payPonit = payPonit;
    }

    public String getPriceSeatNumber() {
        return priceSeatNumber;
    }

    public void setPriceSeatNumber(String priceSeatNumber) {
        this.priceSeatNumber = priceSeatNumber;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getLicensePlateNumberCoach() {
        return licensePlateNumberCoach;
    }

    public void setLicensePlateNumberCoach(String licensePlateNumberCoach) {
        this.licensePlateNumberCoach = licensePlateNumberCoach;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", seats='" + seats + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", tripName='" + tripName + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", departureEndTime='" + departureEndTime + '\'' +
                ", pickupPoint='" + pickupPoint + '\'' +
                ", payPonit='" + payPonit + '\'' +
                ", priceSeatNumber='" + priceSeatNumber + '\'' +
                ", coachName='" + coachName + '\'' +
                ", licensePlateNumberCoach='" + licensePlateNumberCoach + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public static void main(String[] args) {
        PaymentRequest paymentRequest = new PaymentRequest();
        System.out.println("PAYMENT REQUEST: " + paymentRequest);
    }
}
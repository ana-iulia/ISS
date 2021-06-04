package teatru.model;

public class Reservation extends Entity<Integer> {

    private Integer number;
    private String seats;
    private String username;

    public Reservation(Integer number, String seats,String username) {

        setId(number);
        this.number = number;
        this.seats = seats;
        this.username=username;
    }

    public Reservation() {
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getSeats() {
        return seats;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "number=" + number +
                ", seats='" + seats + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

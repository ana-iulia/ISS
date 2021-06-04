package teatru.model;

public class Spectator extends Entity<String> {

    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;

    public Spectator(String username,String password,String name,String phone,String email){
        //this.name=name;
        setId(username);
        this.username=username;
        this.password=password;
        this.name=name;
        this.phone=phone;
        this.email=email;
    }

    public Spectator(){}
    public Spectator(String username,String password) {
        setId(username);
        this.username = username;
        this.password = password;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Spectator{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

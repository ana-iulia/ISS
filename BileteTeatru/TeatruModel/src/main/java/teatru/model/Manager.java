package teatru.model;


public class Manager extends Entity<String> {
    //private String name;
    private String username;
    private String password;

    public Manager(String username,String password){
        //this.name=name;
        setId(username);
        this.username=username;
        this.password=password;
    }
//    public Employee(String username,String password){
//        //setId(id);
//        //this.name=name;
//        this.username=username;
//        this.password=password;
//    }
//    public Employee(String username, String passwd) {
//        this("",username,passwd);
//    }

//    public String getName() {
//        return name;
//    }

//    public void setName(String name) {
//        this.name = name;
//    }
    public Manager(){}
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

    @Override
    public String toString() {
        return "Manager[" + username + ' ' + password + ']';
    }
}

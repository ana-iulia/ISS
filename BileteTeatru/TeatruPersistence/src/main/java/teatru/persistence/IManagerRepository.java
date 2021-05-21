package teatru.persistence;


import teatru.model.Manager;

public interface IManagerRepository extends ICrudRepository<String, Manager> {

    public Manager findManager(String username,String password);

}
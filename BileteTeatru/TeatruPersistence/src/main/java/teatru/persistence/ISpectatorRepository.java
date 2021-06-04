package teatru.persistence;

import teatru.model.Performance;
import teatru.model.Spectator;

public interface ISpectatorRepository extends ICrudRepository<String, Spectator> {
    Spectator findSpectator(String username, String password);
}

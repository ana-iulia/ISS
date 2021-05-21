package teatru.persistence;

import teatru.model.Performance;

import java.time.LocalDate;

public interface IPerformanceRepository extends ICrudRepository<String, Performance> {
    //public Performance findPerformance(LocalDate date, String destination);
}
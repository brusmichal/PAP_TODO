package com.pap.database.statistics.repository;

import com.pap.database.statistics.DailyStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyStatisticsRepository extends MongoRepository<DailyStatistics,String> {
}

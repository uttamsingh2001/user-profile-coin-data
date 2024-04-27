package com.example.userprofilecoindata.repository;

import com.example.userprofilecoindata.entity.CoinDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinDataRepository extends JpaRepository<CoinDataEntity, Long> {
}


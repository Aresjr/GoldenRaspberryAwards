package com.grw.interval.repository;

import com.grw.interval.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Optional<Producer> findById(Long id);

    void deleteById(Long id);

    Optional<Producer> findByName(String name);

    @Query(value = """
            WITH cte AS (
            SELECT p.name as producer, m._year as followingWin,
            LAG(m._year) OVER (PARTITION BY mp.producer_id ORDER BY _year) AS previousWin
            FROM MOVIE m
            JOIN MOVIE_PRODUCER mp
            ON mp.movie_id = m.id
            JOIN PRODUCER p
            ON p.id = mp.producer_id
            WHERE m.winner = TRUE
            ORDER BY p.name, _year
            )

            select producer, followingWin - previousWin as _interval, previousWin, followingWin
            from cte
            WHERE previousWin is not null
            order by _interval
            """, nativeQuery = true
    )
    List<Object[]> findWinnerIntervals();
}

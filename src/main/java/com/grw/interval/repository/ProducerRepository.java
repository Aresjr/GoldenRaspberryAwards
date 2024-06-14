package com.grw.interval.repository;

import com.grw.interval.dto.ProducerIntervalDto;
import com.grw.interval.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Optional<Producer> findById(Long id);

    void deleteById(Long id);

    Optional<Producer> getOneByName(String name);

    @Query(value = """
            WITH cte AS (
                SELECT p.id as producer_id, p.name as Producer, m._year as _year,
                RANK() OVER (PARTITION BY p.name ORDER BY _year) AS rn
                FROM MOVIE m
                JOIN MOVIE_PRODUCER mp
                ON mp.movie_id = m.id
                JOIN PRODUCER p
                ON p.id = mp.producer_id
                WHERE m.winner = TRUE
                ORDER BY p.name, _year
            )

            select Producer, min(_year) as PreviousWin, max(_year) as FollowingWin, max(_year) - min(_year) as WinInterval
            from (
                select Producer, _year
                from cte
                where producer_id in (
                    select producer_id
                    from cte where rn > 1
                )
            )
            group by Producer
            order by WinInterval
            """, nativeQuery = true
    )
    List<ProducerIntervalDto> findWinnerIntervals();
}

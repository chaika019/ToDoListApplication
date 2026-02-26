package by.chaika19.repository;

import by.chaika19.entity.Record;
import by.chaika19.entity.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {
    @Modifying
    @Query("UPDATE Record SET status = :status WHERE id = :id")
    void update(int id, @Param("status") RecordStatus newStatus);

//    Examples
//    List<Record> findAllByStatus(RecordStatus status);
//    List<Record> findAllByStatusAndTitleContainsOrderByIdDesc(RecordStatus status, String titlePart);
//    int countAllByStatus(RecordStatus status);
//    Optional<Record> findFirstByTitleContains(String title);
}

package ru.rsoi.delivery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rsoi.delivery.entity.Delivery;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface DeliveryRepository extends PagingAndSortingRepository<Delivery, Integer> {

    @Query("SELECT p FROM Delivery p WHERE p.uid=:uid")
    Delivery findByUid(@Param("uid") long uid);

    @Query("SELECT d FROM Delivery d WHERE d.user_id=:user_id")
    Page<Delivery> findAllByUserId(@Param("user_id") Integer user_id, Pageable pageable);

    @Transactional
    @Modifying
    @Query("DELETE FROM Delivery as s Where s.uid =:uid")
    void deleteByUid(@Param("uid")long uid);
}
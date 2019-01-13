package ru.rsoi.authserver.repository;

import org.bouncycastle.asn1.cms.TimeStampedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rsoi.authserver.entity.User;

import java.sql.Timestamp;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT p FROM User p WHERE p.uid=:uid")
    User findByUid(@Param("uid") long uid);
    @Query("SELECT p FROM User p WHERE p.userlogin=:login")
    User findByLogin(@Param("login")String login);
    @Query("SELECT p FROM User p WHERE p.token=:token")
    User findByToken(@Param("token") String token);
    @Transactional
    @Modifying
    @Query("UPDATE User p Set p.token=:token, p.validity=:validity, p.timestamp=:time Where p.uid = :uid")
    void setToken(@Param("token")String token,@Param("validity") int validity, @Param("time") Timestamp time,@Param("uid") long user_uid);
    @Transactional
    @Modifying
    @Query("UPDATE User p Set p.timestamp=:date Where p.token = :login")
    void setTime(@Param("date")Timestamp date,@Param("login") String login);
    @Query("SELECT p.timestamp FROM User p WHERE p.token=:token")
    Timestamp getTime(@Param("token") String token);
    @Transactional
    @Modifying
    @Query("UPDATE User p Set p.enabled=false Where p.token = :token")
    void setDisabled(@Param("token") String token);
}

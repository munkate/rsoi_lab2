package ru.rsoi.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rsoi.authserver.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT p FROM User p WHERE p.uid=:uid")
    User findByUid(@Param("uid") long uid);
    @Query("SELECT p FROM User p WHERE p.userlogin=:login")
    User findByLogin(@Param("login")String login);
}

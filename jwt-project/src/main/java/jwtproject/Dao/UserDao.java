package jwtproject.Dao;

import jwtproject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,String> {

    List<User> findAll();

    Optional<User> findUserByUserName(String name);

    User findByUserName(String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

}

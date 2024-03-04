package jwtproject.Dao;

import jwtproject.Entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageDto extends JpaRepository<UserImage,Long> {

}

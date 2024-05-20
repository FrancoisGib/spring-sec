package app.user;

import app.user.models.User;
import app.user.models.UsersCountByDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  @Query(value =
    """
        SELECT
            (
            SELECT COUNT(*)
            FROM   user_app
            ) AS allUsersCount,
            (
            SELECT COUNT(*)
            FROM user_app WHERE account_creation_date BETWEEN :start and :end
            ) AS monthUsersCount;
    """, nativeQuery = true)
  List<UsersCountByDate> getAllUsersCountAndCountByDate(@Param("start") Date start, @Param("end") Date end);
}

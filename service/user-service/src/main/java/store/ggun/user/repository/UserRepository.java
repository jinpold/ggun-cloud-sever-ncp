package store.ggun.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import store.ggun.user.domain.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>, UserDao {

    @Query(value = "SELECT u.* FROM users u WHERE u.username = :username", nativeQuery = true)
    UserModel findByUsernames(@Param("username") String username);

    boolean existsByUsername(String username);

    @Query(value = "select u.* from users u where u.email=:email", nativeQuery = true)
    UserModel findByEmailOauth(@RequestParam String email);

    @Query(value = "select u.color from users u where u.users_id=:id", nativeQuery = true)
    String color(@RequestParam Long id);
}

package store.ggun.admin.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import store.ggun.admin.domain.model.AdminModel;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminModel, Long> {
    Optional<AdminModel> findAdminByUsername(String name);
    @Query("SELECT a FROM admins a WHERE a.email = :email")
    Optional<AdminModel> findAdminByEmail(@Param("email") String email);

    @Query("select count(id) as count from admins where email =:email")
    Integer existsByEmail(@Param("email") String username);

    @Query("select count(id) as count from admins where username = :username ")
    Integer existsByUsername(@Param("username") String username); //boolean 타입은 쿼리매소드 작성불가 => count로 대체

    @Modifying
    @Query("update admins set token=:token where id = :id")
    public void modifyTokenById(@Param("id") Long id, @Param("token") String deleteToken);


}

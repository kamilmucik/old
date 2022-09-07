package pl.estrix.app.backend.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.estrix.app.backend.user.model.User;

@Repository
public interface UserRepository extends UserRepositoryCustom,
        JpaRepository<User, Long>,
        QueryDslPredicateExecutor<User> {

    User findOneByEmail(String email);

}
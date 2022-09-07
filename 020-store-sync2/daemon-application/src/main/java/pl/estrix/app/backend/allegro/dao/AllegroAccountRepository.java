package pl.estrix.app.backend.allegro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.estrix.app.backend.allegro.model.AllegroAccount;

@Repository
public interface AllegroAccountRepository extends AllegroAccountRepositoryCustom,
        JpaRepository<AllegroAccount, Long>,
        QueryDslPredicateExecutor<AllegroAccount> {

    AllegroAccount findById(Long id);
}

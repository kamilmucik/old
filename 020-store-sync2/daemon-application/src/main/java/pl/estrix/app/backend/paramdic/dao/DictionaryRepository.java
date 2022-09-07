package pl.estrix.app.backend.paramdic.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.estrix.app.backend.paramdic.model.Dictionary;

import javax.transaction.Transactional;

@Repository
public interface DictionaryRepository extends DictionaryRepositoryCustom,
        JpaRepository<Dictionary, Long>,
        QueryDslPredicateExecutor<Dictionary> {

//    AllegroAccount findById(Long id);
    Dictionary findById(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dictionary d")
    void deleteAllBySellerId();
//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Dictionary d WHERE d.userId like :sellerId")
//    void deleteAllBySellerId(@Param("sellerId")  String sellerId);
}

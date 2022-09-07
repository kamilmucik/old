package pl.estrix.app.backend.settings.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.estrix.app.backend.settings.model.AppSetting;

@Repository
public interface SettingRepository extends SettingRepositoryCustom,
        JpaRepository<AppSetting, Long>,
        QueryDslPredicateExecutor<AppSetting>{

    AppSetting findById(Long id);

    AppSetting findByName(String name);

    AppSetting findByCode(String code);
}

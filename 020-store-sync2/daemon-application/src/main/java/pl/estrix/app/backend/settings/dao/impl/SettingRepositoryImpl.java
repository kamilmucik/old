package pl.estrix.app.backend.settings.dao.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.app.backend.settings.dao.SettingRepositoryCustom;
import pl.estrix.app.backend.settings.model.AppSetting;
import pl.estrix.app.backend.settings.model.QAppSetting;
import pl.estrix.app.common.dto.SettingSearchCriteriaDto;
import pl.estrix.app.common.dto.model.SettingDto;

import java.util.List;

public class SettingRepositoryImpl
        extends QueryDslRepositorySupportBase
        implements SettingRepositoryCustom {

    private static final QAppSetting qSetting = new QAppSetting("setting");

    public SettingRepositoryImpl() {
        super(AppSetting.class);
    }

    @Override
    public List<SettingDto> find(SettingSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(qSetting.id.desc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                SettingDto.class,
                qSetting.id,
                qSetting.name,
                qSetting.value,
                qSetting.code,
                qSetting.type
        ));
    }

    @Override
    public long findCount(SettingSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(SettingSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(qSetting);

        query.where(builder);
        return query;
    }
}

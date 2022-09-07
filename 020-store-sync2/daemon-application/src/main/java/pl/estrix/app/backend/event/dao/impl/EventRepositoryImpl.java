package pl.estrix.app.backend.event.dao.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.app.backend.event.dao.EventRepositoryCustom;
import pl.estrix.app.backend.event.model.Event;
import pl.estrix.app.backend.event.model.QEvent;
import pl.estrix.app.common.dto.EventSearchCriteriaDto;
import pl.estrix.app.common.dto.model.EventDto;

import java.util.List;

public class EventRepositoryImpl extends QueryDslRepositorySupportBase
        implements EventRepositoryCustom {

    private static final QEvent qEvent = new QEvent("event");

    public EventRepositoryImpl() {
        super(Event.class);
    }

    @Override
    public List<EventDto> find(EventSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(qEvent.id.desc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                EventDto.class,
                qEvent.id,
                qEvent.name,
                qEvent.lastUpdate,
                qEvent.active
        ));
    }

    @Override
    public long findCount(EventSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(EventSearchCriteriaDto searchCriteria) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(qEvent);

        if (searchCriteria.getActive() != null) {
            builder.and(qEvent.active.eq(searchCriteria.getActive()));
        }

        builder.and(qEvent.lastUpdate.isNotNull());
        builder.and(qEvent.active.isNotNull());

        query.where(builder);

        query.limit(100L);
        return query;
    }
}

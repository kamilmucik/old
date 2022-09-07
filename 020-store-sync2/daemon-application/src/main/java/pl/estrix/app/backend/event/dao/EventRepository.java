package pl.estrix.app.backend.event.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.estrix.app.backend.event.model.Event;

@Repository
public interface EventRepository extends EventRepositoryCustom,
        JpaRepository<Event, Long>,
        QueryDslPredicateExecutor<Event> {

    Event findById(Long id);
}
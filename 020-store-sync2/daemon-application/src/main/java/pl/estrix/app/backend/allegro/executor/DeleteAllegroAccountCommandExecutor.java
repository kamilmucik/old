package pl.estrix.app.backend.allegro.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.allegro.model.AllegroAccount;

@Component
public class DeleteAllegroAccountCommandExecutor extends BaseAllegroAccountCommandExecutor {

    public void delete(Long itemId) {
        AllegroAccount entity = getOne(itemId);
        if (entity != null) {
            repository.delete(entity);
        }
    }
}

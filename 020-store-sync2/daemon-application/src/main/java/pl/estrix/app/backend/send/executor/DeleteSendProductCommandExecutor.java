package pl.estrix.app.backend.send.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.send.model.SendProduct;

@Component
public class DeleteSendProductCommandExecutor extends BaseSendProductCommandExecutor {
    public void delete(Long itemId) {
        SendProduct entity = getOne(itemId);
        if (entity != null) {
            repository.delete(entity);
        }
    }
}

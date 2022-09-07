package pl.estrix.app.backend.paramdic.executor;

import org.springframework.stereotype.Component;

@Component
public class DeleteDictionaryCommandExecutor extends BaseDictionaryCommandExecutor {


    public void deleteAllBySellerId(String sellerId){
        repository.deleteAllBySellerId();
//        repository.deleteAllBySellerId(sellerId);
    }
}

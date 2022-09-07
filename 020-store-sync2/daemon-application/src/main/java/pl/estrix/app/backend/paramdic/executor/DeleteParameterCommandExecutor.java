package pl.estrix.app.backend.paramdic.executor;

import org.springframework.stereotype.Component;

@Component
public class DeleteParameterCommandExecutor extends BaseParameterCommandExecutor{

    public void deleteAllBySellerId(String sellerId){
        repository.deleteAllBySellerId();
//        repository.deleteAllBySellerId(sellerId);
    }
}

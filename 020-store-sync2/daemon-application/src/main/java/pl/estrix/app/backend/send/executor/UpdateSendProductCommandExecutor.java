package pl.estrix.app.backend.send.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.SendProductDto;
import pl.estrix.app.common.dto.model.SendProductImageDto;

import java.math.BigDecimal;

@Component
public class UpdateSendProductCommandExecutor extends BaseSendProductCommandExecutor {

    public SendProductDto update(SendProductDto dto) {
        SendProductDto result = this.mapToDto(
                repository.save(this.mapToEntity(dto)
                ));

        if (dto.getImages() != null) {
            for (SendProductImageDto img : dto.getImages()) {
                img.setSendProductId(result.getId());
                repositoryImage.save(this.mapToEntity(img));
            }
        }

        return result;
    }


    public int upgradeProductInShop(Boolean processed, BigDecimal price, Integer stock, String code){
        return repository.upgradeProductInShop(processed, price, stock, code);
    }

}

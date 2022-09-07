package pl.estrix.app.backend.send.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.SendProductDto;
import pl.estrix.app.common.dto.model.SendProductImageDto;

@Component
public class CreateSendProductCommandExecutor extends BaseSendProductCommandExecutor {

    public SendProductDto create(SendProductDto dto) {

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
}

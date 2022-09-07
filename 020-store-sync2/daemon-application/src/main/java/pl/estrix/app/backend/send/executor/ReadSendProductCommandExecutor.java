package pl.estrix.app.backend.send.executor;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.send.model.SendProduct;
import pl.estrix.app.backend.send.model.SendProductImage;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.SendProductSearchCriteriaDto;
import pl.estrix.app.common.dto.model.SendProductDto;
import pl.estrix.app.common.dto.model.SendProductImageDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class ReadSendProductCommandExecutor extends BaseSendProductCommandExecutor {

    public SendProductDto findById(Long id) {
        return mapToDto(getOne(id));
    }

    public ListResponseDto<SendProductDto> find(SendProductSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        return createListResponseDto(
                pagingCriteria,
                () -> repository.find(searchCriteria, pagingCriteria),
                () -> (int) repository.findCount(searchCriteria));
    }

    public SendProductDto getLastProductToSend() {
        List<SendProduct> prod = repository.findFirstByProcessed(false);
        if (prod.size() > 0){
            SendProductDto sendProductDto = mapToDto(prod.get(0));

            List<SendProductImage>  images = repositoryImage.findBySendProductId(sendProductDto.getId());
            for (SendProductImage image :images){
                SendProductImageDto im = new SendProductImageDto();
                im.setPosition(image.getPosition());
                im.setSrc(image.getSrc());
                im.setId(image.getId());
                im.setSendProductId(image.getSendProductId());
                sendProductDto.addImages(im);
            }
            return sendProductDto;
        } else {
            return null;
        }
    }

}

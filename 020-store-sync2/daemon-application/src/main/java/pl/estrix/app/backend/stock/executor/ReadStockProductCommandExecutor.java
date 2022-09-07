package pl.estrix.app.backend.stock.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.stock.model.StockProduct;
import pl.estrix.app.backend.stock.model.StockProductImage;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.StockProductSearchCriteriaDto;
import pl.estrix.app.common.dto.model.StockProductDto;
import pl.estrix.app.common.dto.model.StockProductImageDto;

import java.util.List;

@Component
public class ReadStockProductCommandExecutor extends BaseStockProductCommandExecutor {

    public StockProductDto findById(Long id) {
        StockProductDto result =  mapToDto(getOne(id));
            repositoryImage.findByProductId(result.getId()).stream().forEach( m->{
                StockProductImageDto img = new StockProductImageDto();
                img.setId(m.getId());
                img.setUrl(m.getUrl());
                result.addImage(img);
            });
        return result;
    }

    public ListResponseDto<StockProductDto> find(StockProductSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<StockProductDto> result = repository.find(searchCriteria, pagingCriteria);
        for (StockProductDto dto : result){
            List<StockProductImage> images = repositoryImage.findByProductId(dto.getId());
            for (int i = 0 ; i < images.size(); i++){
                    if (i == 0) dto.setMainThumb(images.get(i).getBase64Content());
                    dto.addImage(StockProductImageDto
                            .builder()
                            .url(images.get(i).getUrl())
                            .thumb(images.get(i).getBase64Content())
                            .build());
                }

        }

//        System.out.println("1. " + result.size());
//        System.out.println("2. " + (int) repository.findCount(searchCriteria));

        return createListResponseDto(
                pagingCriteria,
                () -> result,
                () -> (int) repository.findCount(searchCriteria));
    }


    public StockProductDto findByStockIdAndExternalId(Long shopId, String externalId) {
        List<StockProduct> stockProduct = repository.findByStockIdAndExternalId(shopId,externalId);
        StockProductDto dto = null;
        if (stockProduct.size() > 0) {
            dto = mapToDto(stockProduct.stream().findFirst().get());
        }
        return  dto;
    }

    public StockProductDto findByShopIdAndProductCode(Long shopId, String code) {
        List<StockProduct> stockProduct = repository.findByShopAndCode(shopId,code);
        StockProductDto dto = null;
        if (stockProduct.size() > 0) {
            dto = mapToDto(stockProduct.stream().findFirst().get());
            if (dto.getImages() == null) {
                List<StockProductImage> images = repositoryImage.findByProductId(dto.getId());
                for (StockProductImage image : images){
                    StockProductImageDto imgDto = new StockProductImageDto();
                    imgDto.setId(imgDto.getId());
                    imgDto.setUrl(image.getUrl());
                    dto.addImage(imgDto);
                }
            }
        }
        return  dto;
    }

//    public StockProductDto findByStockIdAndProductCode(Long shopId, String code) {
//        List<StockProduct> stockProduct = repository.findByStockAndCode(shopId,code);
//        StockProductDto dto = null;
//        if (stockProduct.size() > 0) {
//            dto = mapToDto(stockProduct.stream().findFirst().get());
//        }
//        return  dto;
//    }
    public StockProductDto findByStockIdAndProductCode(Long stockId, String code) {
        List<StockProduct> stockProduct = repository.findByStockAndCode(stockId,code);
        StockProductDto dto = null;
        if (stockProduct.size() > 0) {
            dto = mapToDto(stockProduct.stream().findFirst().get());
            if (dto.getImages() == null) {
                List<StockProductImage> images = repositoryImage.findByProductId(dto.getId());
                for (StockProductImage image : images){
                    StockProductImageDto imgDto = new StockProductImageDto();
                    imgDto.setId(imgDto.getId());
                    imgDto.setUrl(image.getUrl());
                    dto.addImage(imgDto);
                }
            }
        }
        return  dto;
    }
}

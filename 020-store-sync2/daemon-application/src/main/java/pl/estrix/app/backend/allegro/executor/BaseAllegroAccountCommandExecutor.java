package pl.estrix.app.backend.allegro.executor;

import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.app.backend.allegro.dao.AllegroAccountRepository;
import pl.estrix.app.backend.allegro.model.AllegroAccount;
import pl.estrix.app.backend.base.BaseCommandExecutor;
import pl.estrix.app.common.dto.model.AllegroAccountDto;

import java.time.LocalDateTime;

public class BaseAllegroAccountCommandExecutor extends BaseCommandExecutor<AllegroAccount, AllegroAccountDto> {

    @Autowired
    protected AllegroAccountRepository repository;

    @Override
    protected Class<AllegroAccountDto> getDtoClass() {
        return AllegroAccountDto.class;
    }

    protected AllegroAccountDto mapToDto(AllegroAccount entity) {
        if (entity == null){
            return null;
        }
        AllegroAccountDto dto = new AllegroAccountDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAccessToken(entity.getAccessToken());
        dto.setRefreshToken(entity.getRefreshToken());
        dto.setExpiresIn(entity.getExpiresIn());
        dto.setScope(entity.getScope());
        dto.setUserCode(entity.getUserCode());
        dto.setDeviceCode(entity.getDeviceCode());
        dto.setVerificationUri(entity.getVerificationUri());
        dto.setVerificationUriComplete(entity.getVerificationUriComplete());
        dto.setInterval(entity.getInterval());
        dto.setTokenType(entity.getTokenType());
        dto.setSellerId(entity.getSellerId());
        dto.setClientId(entity.getClientId());
        dto.setJti(entity.getJti());
        dto.setProvince(entity.getProvince());
        dto.setCity(entity.getCity());
        dto.setPostCode(entity.getPostCode());
        dto.setCountryCode(entity.getCountryCode());
        dto.setDefaultAccount(entity.getDefaultAccount());

        return dto;
    }

    protected AllegroAccount mapToEntity(AllegroAccountDto dto){
        if (dto == null){
            return null;
        }
        AllegroAccount entity = new AllegroAccount();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
//        System.out.println("dto.getAccessToken(): " + dto.getAccessToken().length());
//        System.out.println("dto.getAccessToken(): " + dto.getAccessToken());
        entity.setAccessToken(dto.getAccessToken());
        entity.setRefreshToken(dto.getRefreshToken());
//        System.out.println("dto.getRefreshToken(): " + dto.getRefreshToken().length());
//        System.out.println("dto.getRefreshToken(): " + dto.getRefreshToken());
        entity.setExpiresIn(dto.getExpiresIn());
        entity.setScope(dto.getScope());
        entity.setUserCode(dto.getUserCode());
        entity.setDeviceCode(dto.getDeviceCode());
        entity.setVerificationUri(dto.getVerificationUri());
        entity.setVerificationUriComplete(dto.getVerificationUriComplete());
        entity.setInterval(dto.getInterval());
        entity.setTokenType(dto.getTokenType());
        entity.setSellerId(dto.getSellerId());
        entity.setClientId(dto.getClientId());
        entity.setJti(dto.getJti());
        entity.setProvince(dto.getProvince());
        entity.setCity(dto.getCity());
        entity.setPostCode(dto.getPostCode());
        entity.setCountryCode(dto.getCountryCode());
        entity.setDefaultAccount(dto.getDefaultAccount());
        entity.setLastUpdate(LocalDateTime.now());

        return entity;
    }

    protected AllegroAccount getOne(Long id) {
        return repository.findById(id);
    }
}

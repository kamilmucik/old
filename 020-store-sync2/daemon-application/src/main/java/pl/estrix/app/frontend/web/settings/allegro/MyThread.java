package pl.estrix.app.frontend.web.settings.allegro;

import pl.estrix.app.backend.allegro.service.AllegroService;
import pl.estrix.app.backend.paramdic.service.ParamdicService;
import pl.estrix.app.common.dto.model.AllegroAccountDto;
import pl.estrix.app.common.dto.model.DictionaryDto;
import pl.estrix.app.lib.allegro.Allegro;
import pl.estrix.app.lib.allegro.model.*;

public class MyThread extends Thread {

    private AllegroService allegroService;
    private ParamdicService paramdicService;
    private AllegroAccountDto allegroAccountDto;
    private AllegroDeviceDto deviceDto;
    private Allegro allegro;

    public MyThread(AllegroAccountDto allegroAccountDto,AllegroService allegroService,ParamdicService paramdicService,AllegroDeviceDto deviceDto,Allegro allegro) {
        this.allegroService = allegroService;
        this.paramdicService = paramdicService;
        this.allegroAccountDto = allegroAccountDto;
        this.deviceDto = deviceDto;
        this.allegro = allegro;
    }

    public void run() {
        try {
            AllegroAccessDto accessDto = allegro.waitForUserAccept(deviceDto);
//                    allegroAccountDto.setId(allegroAccountDto.getId());
            allegroAccountDto.setAccessToken(accessDto.getAccess_token());
//                    allegroAccountDto.setTokenType(accessDto.getToken_type());
            allegroAccountDto.setTokenType("refresh_token");

            allegroAccountDto.setRefreshToken(accessDto.getRefresh_token());
            allegroAccountDto.setExpiresIn(accessDto.getExpires_in());
            allegroAccountDto.setScope(accessDto.getScope());
            allegroAccountDto = allegroService.create(allegroAccountDto);

            accessDto.setToken_type("refresh_token");
            System.out.println("getToken_type: " + accessDto.getToken_type());

            System.out.println("getAccess_token:  " + accessDto.getAccess_token());
            System.out.println("getRefresh_token: " + accessDto.getRefresh_token());
            accessDto = allegro.refreshToken(accessDto);
//
            allegroAccountDto.setJti(accessDto.getJti());
            allegroAccountDto.setClientId(accessDto.getClient_id());
            allegroAccountDto.setSellerId(accessDto.getUser_name());
//
            System.out.println("getAccess_token:  " + accessDto.getAccess_token());
            System.out.println("getRefresh_token: " + accessDto.getRefresh_token());
            allegroAccountDto.setExpiresIn(accessDto.getExpires_in());
            allegroAccountDto.setAccessToken(accessDto.getAccess_token());
            allegroAccountDto.setRefreshToken(accessDto.getRefresh_token());
            allegroAccountDto.setName(accessDto.getUser_name());
            allegroAccountDto = allegroService.create(allegroAccountDto);

            AllegroOffertShippingRatesDto returnDeliveryDto = allegro.getSaleShippingRates(accessDto.getAccess_token(), allegroAccountDto.getSellerId());
            for (AllegroDictionaryDto dto : returnDeliveryDto.getShippingRates()) {
                System.out.println("ReturnDelivery: " + dto);
                DictionaryDto dictionaryDto = DictionaryDto
                        .builder()
                        .dictionaryName("ReturnDelivery")
                        .userId(allegroAccountDto.getSellerId())
                        .allegroID(dto.getId())
                        .allegroName(dto.getName())
                        .build();
                paramdicService.createDictionary(dictionaryDto);
            }
            AllegroOffertImpliedWarrantyDto impliedWarrantyDto = allegro.getImpliedWarranty(accessDto.getAccess_token(), allegroAccountDto.getSellerId());
            for (AllegroDictionaryDto dto : impliedWarrantyDto.getImpliedWarranties()) {
                System.out.println("ImpliedWarranty: " + dto);
                DictionaryDto dictionaryDto = DictionaryDto
                        .builder()
                        .dictionaryName("ImpliedWarranty")
                        .userId(allegroAccountDto.getSellerId())
                        .allegroID(dto.getId())
                        .allegroName(dto.getName())
                        .build();
                paramdicService.createDictionary(dictionaryDto);
            }
            AllegroOffertReturnPolicyDto returnPolicyDto = allegro.getReturnPolicy(accessDto.getAccess_token(), allegroAccountDto.getSellerId());
            for (AllegroDictionaryDto dto : returnPolicyDto.getReturnPolicies()) {
                System.out.println("ReturnPolicy: " + dto);
                DictionaryDto dictionaryDto = DictionaryDto
                        .builder()
                        .dictionaryName("ReturnPolicy")
                        .userId(allegroAccountDto.getSellerId())
                        .allegroID(dto.getId())
                        .allegroName(dto.getName())
                        .build();
                paramdicService.createDictionary(dictionaryDto);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

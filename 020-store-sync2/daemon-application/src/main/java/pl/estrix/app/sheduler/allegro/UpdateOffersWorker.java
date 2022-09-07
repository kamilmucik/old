package pl.estrix.app.sheduler.allegro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.estrix.app.backend.allegro.service.AllegroService;
import pl.estrix.app.backend.settings.service.SettingService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.AllegroAccountSearchCriteriaDto;
import pl.estrix.app.common.dto.model.AllegroAccountDto;
import pl.estrix.app.common.dto.model.SettingDto;
import pl.estrix.app.lib.allegro.Allegro;
import pl.estrix.app.lib.allegro.AllegroAPI;
import pl.estrix.app.lib.allegro.model.*;
import pl.estrix.app.lib.allegro.oauth.OAuthConfig;

import java.io.IOException;
import java.util.Optional;

@Component
public class UpdateOffersWorker {

    @Autowired
    private AllegroService allegroService;
    @Autowired
    private SettingService settingService;

    private OAuthConfig config;
    private Allegro allegro;

    public UpdateOffersWorker(){
        config = new OAuthConfig("https://allegro.pl", "f0dddb5dc3bc4f8cb9faf362ddb98923", "cqxudGg5Xvjs8NzYykVakpzDZBK7k7hijbPnaCKXpOceIGr0EjafZLBdMagGCtVf");
        allegro = new AllegroAPI(config);
    }


    @Scheduled(cron="0 * * * * *")
    public void runTask() {
        Optional<SettingDto> optSetting = settingService.getByCode("SCHEDULER_SEND_PRODUCT_SHOP");
        if (optSetting.isPresent()) {
            if (optSetting.get().getValue().equals("1")) {
                ListResponseDto<AllegroAccountDto> responseDto = allegroService.getItems(AllegroAccountSearchCriteriaDto.builder().build(),null);

                if (!responseDto.isEmpty()){
                    for (AllegroAccountDto account : responseDto.getData()){
//                        System.out.println("ac: " + account);
                        try {
                            AllegroSearchOfferDto offers = allegro.getSearchOfferts(account.getAccessToken(), account.getSellerId());

                            if (offers.getItems().getPromoted() != null) {
                                for (AllegroSearchOfferItemDto offer : offers.getItems().getPromoted()) {
//                                    System.out.println("pro: " + offer);
                                }
                            }
                            if (offers.getItems().getPromoted() != null) {
                                for (AllegroSearchOfferItemDto offer : offers.getItems().getPromoted()) {
//                                    System.out.println("reg: " + offer);
                                }
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

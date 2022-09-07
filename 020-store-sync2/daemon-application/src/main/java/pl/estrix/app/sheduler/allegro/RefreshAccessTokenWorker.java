package pl.estrix.app.sheduler.allegro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.estrix.app.backend.allegro.service.AllegroService;
import pl.estrix.app.backend.event.service.EventService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.AllegroAccountSearchCriteriaDto;
import pl.estrix.app.common.dto.model.AllegroAccountDto;
import pl.estrix.app.common.dto.model.EventDto;
import pl.estrix.app.lib.allegro.Allegro;
import pl.estrix.app.lib.allegro.AllegroAPI;
import pl.estrix.app.lib.allegro.model.AllegroAccessDto;
import pl.estrix.app.lib.allegro.oauth.OAuthConfig;

import java.time.LocalDateTime;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;

@Component
public class RefreshAccessTokenWorker {


    private static final Logger LOG = LoggerFactory.getLogger(RefreshAccessTokenWorker.class);

    @Autowired
    private AllegroService allegroService;
    @Autowired
    private EventService eventService;


    private OAuthConfig config;
    private Allegro allegro;

    public RefreshAccessTokenWorker(){
        config = new OAuthConfig("https://allegro.pl", "f0dddb5dc3bc4f8cb9faf362ddb98923", "cqxudGg5Xvjs8NzYykVakpzDZBK7k7hijbPnaCKXpOceIGr0EjafZLBdMagGCtVf");
        allegro = new AllegroAPI(config);
    }

    @Scheduled(cron="0 * * * * *")
    public void runTask() {
        ListResponseDto<AllegroAccountDto> responseDto = allegroService.getItems(AllegroAccountSearchCriteriaDto.builder().build(),null);

        if (!responseDto.isEmpty()){
            for (AllegroAccountDto account : responseDto.getData()){
//                System.out.println("ac: " + account);
                if (account.getLastUpdated() != null){
                    long daysBetween = account.getExpiresIn() - MINUTES.between(account.getLastUpdated(), LocalDateTime.now()) ;
                    LOG.debug("\tac: " + daysBetween);
                    if (daysBetween < 5){
                        eventService.create(EventDto
                                .builder()
                                .name("Allegro: odświeżam token aplikacji ")
                                .active(true)
                                .build());
                        AllegroAccessDto accessDto = new AllegroAccessDto();
                        accessDto.setClient_id(account.getSellerId());
                        accessDto.setRefresh_token(account.getRefreshToken());
                        accessDto.setToken_type(account.getTokenType());

                        accessDto = allegro.refreshToken(accessDto);
                        account.setExpiresIn(accessDto.getExpires_in());
                        account.setAccessToken(accessDto.getAccess_token());
                        account.setRefreshToken(accessDto.getRefresh_token());
                        account.setName(accessDto.getUser_name());

                        allegroService.create(account);
                    }

                }


            }
        }


    }
}

package pl.estrix.app.frontend.web.settings.allegro;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.app.FacesViewScope;
import pl.estrix.app.backend.allegro.service.AllegroService;
import pl.estrix.app.backend.paramdic.service.ParamdicService;
import pl.estrix.app.common.dto.model.AllegroAccountDto;
import pl.estrix.app.common.dto.model.DictionaryDto;
import pl.estrix.app.common.dto.model.ShopDto;
import pl.estrix.app.frontend.web.MainController;
import pl.estrix.app.lib.allegro.Allegro;
import pl.estrix.app.lib.allegro.AllegroAPI;
import pl.estrix.app.lib.allegro.model.*;
import pl.estrix.app.lib.allegro.oauth.OAuthConfig;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Getter
@Setter
@Scope(FacesViewScope.NAME)
@Component
@ManagedBean(name = "allegroController")
public class AllegroController extends MainController {

    private static final Logger LOG = LoggerFactory.getLogger(AllegroController.class);

    @Autowired
    private AllegroService allegroService;
    @Autowired
    private ParamdicService paramdicService;

    private LazyDataModel<AllegroAccountDto> lazyModel;

    private AllegroAccountDto selected;

    private String searchText;

    private OAuthConfig config;
    private Allegro allegro;
    private AllegroDeviceDto deviceDto;

    private String resultText;

    @PostConstruct
    public void init() {
        super.updateOwnUrl();
        tablePageIndex = 1;
        lazyModel = new AllegroLazyDataModel(allegroService, searchText);
        selected = new AllegroAccountDto();

        config = new OAuthConfig("https://allegro.pl", "f0dddb5dc3bc4f8cb9faf362ddb98923", "cqxudGg5Xvjs8NzYykVakpzDZBK7k7hijbPnaCKXpOceIGr0EjafZLBdMagGCtVf");
        allegro = new AllegroAPI(config);
    }


    public void onConnectAccountAction() {
        LOG.debug("onConnectAccountAction");
        try {
            AllegroAccountDto allegroAccountDto = AllegroAccountDto
                    .builder()
                    .name("name")
                    .deviceCode(deviceDto.getDevice_code())
                    .userCode(deviceDto.getUser_code())
                    .verificationUriComplete(deviceDto.getVerification_uri_complete())
                    .verificationUri(deviceDto.getVerification_uri())
                    .interval(deviceDto.getInterval())
                    .accessToken("")
                    .tokenType("")
                    .refreshToken("")
                    .city("")
                    .postCode("00-000")
                    .province("")
                    .countryCode("PL")
                    .build();
            allegroAccountDto = allegroService.create(allegroAccountDto);

            MyThread myThread = new MyThread(allegroAccountDto, allegroService, paramdicService, deviceDto, allegro);
            myThread.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void refreshToken(Long id){
        select(id);

        AllegroAccessDto accessDto = new AllegroAccessDto();
        accessDto.setClient_id(selected.getSellerId());
        accessDto.setRefresh_token(selected.getRefreshToken());
        accessDto.setToken_type(selected.getTokenType());

//        allegro.refreshToken(accessDto);
        accessDto = allegro.refreshToken(accessDto);
        selected.setExpiresIn(accessDto.getExpires_in());
        selected.setAccessToken(accessDto.getAccess_token());
        selected.setRefreshToken(accessDto.getRefresh_token());
        selected.setName(accessDto.getUser_name());

        selected = allegroService.create(selected);


        resultText = selected.toString();

    }

    public void getOffer(Long id){
        resultText ="getOffer: " + id;
        System.out.println("getOffer: " + id);
        LOG.debug("getOffer: " + id);
        select(id);
        LOG.debug("selected: " + selected);

        try {
            AllegroSearchOfferDto offers = allegro.getSearchOfferts(selected.getAccessToken(), selected.getSellerId());

            if (offers.getItems().getPromoted() != null) {
                for (AllegroSearchOfferItemDto offer : offers.getItems().getPromoted()) {
                    System.out.println("pro: " + offer);
                    resultText += "pro: " + offer + "<br>";
                }
            }
            if (offers.getItems().getPromoted() != null) {
                for (AllegroSearchOfferItemDto offer : offers.getItems().getRegular()) {
                    System.out.println("reg: " + offer.getName());
                    resultText += "/r/nreg: " + offer.getName() + "";
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public String connectAccount() {
            try {
                deviceDto = allegro.doConnectionWithApp();
                return deviceDto.getVerification_uri_complete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return "";
    }

    public void select(Long id) {
        if (id == null || id == 0) {
            selected = new AllegroAccountDto();
//            selected.setName("");
        } else {
            selected = allegroService.get(id);
        }
    }

    public void onDateSelectRefresh(SelectEvent event) {

    }

    public void save() {
        if (selected.getId() != null){
//            allegroService.update(selected);
        } else {
            allegroService.create(selected);
        }
    }
    public void delete() {
        allegroService.delete(selected.getId());
    }

    public void search() {
        lazyModel = new AllegroLazyDataModel(allegroService, searchText);
    }


    public Integer getTablePageIndex() {
        return tablePageIndex;
    }

    public void setTablePageIndex(Integer tablePageIndex) {
        this.tablePageIndex = tablePageIndex;
    }
}

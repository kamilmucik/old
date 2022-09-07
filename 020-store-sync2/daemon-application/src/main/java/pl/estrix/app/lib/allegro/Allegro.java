package pl.estrix.app.lib.allegro;

import pl.estrix.app.lib.allegro.model.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface Allegro {

    AllegroDeviceDto doConnectionWithApp() throws IOException;

    AllegroAccessDto waitForUserAccept(AllegroDeviceDto device) throws IOException;

    AllegroAccessDto refreshToken(AllegroAccessDto accessDto);

    AllegroEventsDto getLastEvent(String token) throws IOException;

    List<AllegroEventsDto> getEventFrom(AllegroEventsDto event);

    List<AllegroProductDto> getProducts(String token) throws IOException;

    AllegroOffertListDto getOfferts(String token) throws IOException;
    AllegroSearchOfferDto getSearchOfferts(String token, String sellerId) throws IOException;

    AllegroCategoriesDto getCategories(String token) throws IOException;
    AllegroCategoriesDto getCategories(String token, String parentId) throws IOException;
    AllegroCategoryParamsDto getCategoryParams(String token, String categoryId) throws IOException;

    AllegroSaleTags getSaleOfferTags(String token, String userId) throws IOException;
    AllegroOffertShippingRatesDto getSaleShippingRates(String token, String userId) throws IOException;
    AllegroOffertImpliedWarrantyDto getImpliedWarranty(String token, String userId) throws IOException;
    AllegroOffertReturnPolicyDto getReturnPolicy(String token, String userId) throws IOException;

    List getAll(String endpointBase, Map<String, String> params);

    default List getAll(String endpointBase) {
        return getAll(endpointBase, Collections.emptyMap());
    }

    GetSaleOfferContactsResponse getSaleOfferContacts(String token, String sellerId) throws IOException;
    AllegroOfferContactDto createSaleOfferContact(String token, AllegroOfferContactDto dto) throws IOException;

    AllegroSaleImageDto sendSaleImage(String token, String imagePath) throws IOException;

    AllegroOffertResponse createOfferDraft(String token, AllegroOffertRequest request) throws IOException;

    AllegroOffertShippingRatesDto getUserInfo(String token, String userId) throws IOException;

}

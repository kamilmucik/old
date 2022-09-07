package pl.estrix.lib;

import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pl.estrix.app.lib.allegro.Allegro;
import pl.estrix.app.lib.allegro.AllegroAPI;
import pl.estrix.app.lib.allegro.model.*;
import pl.estrix.app.lib.allegro.oauth.OAuthConfig;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

@Ignore
public class AllegroTest {

    private OAuthConfig config;
    private Allegro allegro;
    private String token = "";


    //Hasło do aplikacji: 2dwNcw3CQ74Xn9I
    //Client ID / klucz WebAPI : f0dddb5dc3bc4f8cb9faf362ddb98923
    //Client Secret : cqxudGg5Xvjs8NzYykVakpzDZBK7k7hijbPnaCKXpOceIGr0EjafZLBdMagGCtVf
    @Before // setup()
    public void before() throws Exception {
        config = new OAuthConfig("https://allegro.pl", "f0dddb5dc3bc4f8cb9faf362ddb98923", "cqxudGg5Xvjs8NzYykVakpzDZBK7k7hijbPnaCKXpOceIGr0EjafZLBdMagGCtVf");
//        config = new OAuthConfig("https://allegro.pl.allegrosandbox.pl", "eed297d9685142bb825128c0ebe0017b", "cqxudGg5Xvjs8NzYykVakpzDZBK7k7hijbPnaCKXpOceIGr0EjafZLBdMagGCtVf");
        allegro = new AllegroAPI(config);

        token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTg0NjYzMDQsInVzZXJfbmFtZSI6IjU4MTM3MjU0IiwianRpIjoiYjRjZTY3YzQtZDY4OS00YjM2LWFjYTYtMDY0Y2UxMTNlYzdjIiwiY2xpZW50X2lkIjoiZjBkZGRiNWRjM2JjNGY4Y2I5ZmFmMzYyZGRiOTg5MjMiLCJzY29wZSI6WyJhbGxlZ3JvX2FwaSJdfQ.NERzcx8wIVvtti61pkGIKfK8cT6g25M2O2XctBHJl8pHLCrIiVSDuIm-pfJdoVBCKoUdTfTbPF1e0lcGwOYm6QZuJGwwpc1Uj9dc2zGH3pr9Q1bN7DK70f58Ve9-o6ULt9kO6gGzMAJPXOGvKM5wqTZsr1JaAIuJZH4OROEYd2DjZMi2Jh_GbYJ0YpTBqqolefb_j_dIeYlN8eWl9nPZ5ld_jAufIkBO8Gu5JQoPThZGUkE9ShDvT5EhgOfqYjQqOo-0GHO121T85EjLACTZXyElX7oOzbLMU9UXvdeyHEIxePQso4nOMZFfWkw8sP5fX790b8h6WskOOYL57aErQw";
    }

    @After // tearDown()
    public void after() throws Exception {
        allegro = null;
        config = null;

        assertNull(allegro);
        assertNull(config);
    }

    @Test
    public void shouldConnectToAllegroApi() throws IOException {
        AllegroDeviceDto deviceDto = allegro.doConnectionWithApp();

        java.awt.Desktop.getDesktop().browse(java.net.URI.create(deviceDto.getVerification_uri_complete()));

        AllegroAccessDto accessDto = allegro.waitForUserAccept(deviceDto);
        AllegroAccessDto accessDto2 = allegro.refreshToken(accessDto);

        System.out.println(accessDto);
      //  System.out.println(accessDto2);

        assertNotNull(accessDto);
      //  assertNotNull(accessDto2);
    }

    @Test
    public void shouldCreateContact() throws IOException {

        AllegroOfferContactDto contact = new AllegroOfferContactDto();
        AllegroOfferContactEmailDto contactEmail = new AllegroOfferContactEmailDto("sklep@4ext.pl");
        AllegroOfferContactPhoneDto contactPhone = new AllegroOfferContactPhoneDto("782-051-477");

        contact.setName("Kamil z 4ext");
        contact.setPhones(new ArrayList<>());
        contact.setEmails(new ArrayList<>());
        contact.getPhones().add(contactPhone);
        contact.getEmails().add(contactEmail);

        AllegroOfferContactDto result = allegro.createSaleOfferContact("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTYwNjc2NjYsInVzZXJfbmFtZSI6IjU4MTM3MjU0IiwianRpIjoiZDBmYzI4NGMtZDFiMi00NWVhLThmMmUtMGM4N2E1OTcxNjI0IiwiY2xpZW50X2lkIjoiZjBkZGRiNWRjM2JjNGY4Y2I5ZmFmMzYyZGRiOTg5MjMiLCJzY29wZSI6W119.PNnj6-NRvsrliiIJnf_o6fF8AeU046O-GLku6cSxdboIi-5to1HNCjYXuMkMqWQCFr3w-8HIIKwUJcs-L3F_u6pGTxmDZ3rgWREiuQ5UF3M4_6L0azvH7z4VPQCCTAeOS-qZ8kSo0UXxEaLNuhUc-gJYV7Xkac_TYGQ4FAL1WFBRFd3YDoapjj4mfRQj9DVF7vOiM5hbyR7DAECLlmewBqNGQis9-7ZF_NBu8vRJmjon1cyvmrbMcX-hFXX2LANUEcqqXUrx8aA6zxOAgdYvOLeNcFZmtYkmRpUgpZ1djeRpgxxMsIA0iIkNYYqnna8ynCcOeoS5M1knumPWDCFvag", contact);

        assertNotNull(result.getId());
    }

    @Test
    public void shouldReturnAllContacts() throws IOException {
        GetSaleOfferContactsResponse resultDto = allegro.getSaleOfferContacts("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTYwNjc2NjYsInVzZXJfbmFtZSI6IjU4MTM3MjU0IiwianRpIjoiZDBmYzI4NGMtZDFiMi00NWVhLThmMmUtMGM4N2E1OTcxNjI0IiwiY2xpZW50X2lkIjoiZjBkZGRiNWRjM2JjNGY4Y2I5ZmFmMzYyZGRiOTg5MjMiLCJzY29wZSI6W119.PNnj6-NRvsrliiIJnf_o6fF8AeU046O-GLku6cSxdboIi-5to1HNCjYXuMkMqWQCFr3w-8HIIKwUJcs-L3F_u6pGTxmDZ3rgWREiuQ5UF3M4_6L0azvH7z4VPQCCTAeOS-qZ8kSo0UXxEaLNuhUc-gJYV7Xkac_TYGQ4FAL1WFBRFd3YDoapjj4mfRQj9DVF7vOiM5hbyR7DAECLlmewBqNGQis9-7ZF_NBu8vRJmjon1cyvmrbMcX-hFXX2LANUEcqqXUrx8aA6zxOAgdYvOLeNcFZmtYkmRpUgpZ1djeRpgxxMsIA0iIkNYYqnna8ynCcOeoS5M1knumPWDCFvag","58137254");
        assertFalse(resultDto.getContacts().isEmpty());
        //etSaleOfferContacts: {"contacts":[{"id":"6607ef6a-268d-4546-b351-2ad4f16251b2","name":"Kamil","emails":[{"address":"kontakt@e-strix.pl"}],"phones":[{"number":"+48 663 321 302"}]},{"id":"dd7becd5-c49c-4aff-b6c0-4811b7b1178d","name":"Kamil z 4ext","emails":[{"address":"sklep@4ext.pl"}],"phones":[{"number":"+48 782 051 477"}]}]}

    }
    @Test
    public void shouldCategoryParams() throws IOException {
        AllegroCategoryParamsDto resultDto = allegro.getCategoryParams(token,"269");
//        System.out.println("resultDto: " + resultDto);

        for (CategoryParamDto paramDto : resultDto.getParameters()){
            System.out.println("param: " + paramDto);
            if (paramDto.getDictionary()!= null) {
                for (CategoryParamDictionaryDto dic : paramDto.getDictionary()) {
                    System.out.println("\tic: " + dic);
                }
            }
        }
    }


    @Test
    public void shouldSendImageToAllegroServer() throws IOException {
        AllegroSaleImageDto resultDto = allegro.sendSaleImage(token,"d:\\data\\test.png");
        System.out.println("resultDto: " + resultDto.getLocation());
    }

    /**
     * Tagi są dostępne po wykupieniu abonamentu
     * @throws IOException
     */
    @Test
    public void shouldReturnTags() throws IOException{
        AllegroSaleTags resultDto = allegro.getSaleOfferTags(token,"58137254");
        System.out.println("resultDto: " + resultDto.getTags());
    }
    @Test
    public void shouldReturnDelivery() throws IOException{
        AllegroOffertShippingRatesDto resultDto = allegro.getSaleShippingRates(token,"58137254");
        System.out.println("resultDto: " + resultDto);
        for(AllegroDictionaryDto dto : resultDto.getShippingRates()){
            System.out.println("dto: " + dto);
        }

    }
    @Test
    public void shouldReturnImpliedWarranty() throws IOException{
        AllegroOffertImpliedWarrantyDto resultDto = allegro.getImpliedWarranty(token,"58137254");
        System.out.println("resultDto: " + resultDto);
        for(AllegroDictionaryDto dto : resultDto.getImpliedWarranties()){
            System.out.println("dto: " + dto);
        }
    }
    @Test
    public void shouldReturnReturnPolicy() throws IOException{
        AllegroOffertReturnPolicyDto resultDto = allegro.getReturnPolicy(token,"58137254");
        System.out.println("resultDto: " + resultDto);

        for(AllegroDictionaryDto dto : resultDto.getReturnPolicies()){
            System.out.println("dto: " + dto);
        }
    }

    @Test
    public void shouldPrepareOfferDraft() throws IOException {
        //Dom i ogród -> Narzędzia -> Pozostałe (269)
        AllegroOffertRequest request = new AllegroOffertRequest
                .Builder()
                .name("Aluminiowy najazd rampa dla motocykli")
                .category("269")//Dom i ogród -> Narzędzia -> Pozostałe (269)
                .addParam("11323_1")//{"parameters":[{"id":"11323","name":"Stan","type":"dictionary","required":true,"unit":null,"options":{"variantsAllowed":false,"variantsEqual":true},"dictionary":[{"id":"11323_1","value":"Nowy"},{"id":"11323_2","value":"Używany"},{"id":"11323_238066","value":"Po zwrocie"},{"id":"11323_238058","value":"Powystawowy"}],"restrictions":{"multipleChoices":false}},{"id":"11325","name":"Uszkodzony","type":"dictionary","required":false,"unit":null,"options":{"variantsAllowed":false,"variantsEqual":false},"dictionary":[{"id":"11325_1","value":"Tak"},{"id":"11325_2","value":"Nie"}],"restrictions":{"multipleChoices":false}},{"id":"17448","name":"Waga (z opakowaniem)","type":"float","required":false,"unit":"kg","options":{"variantsAllowed":false,"variantsEqual":false},"restrictions":{"min":0.0,"max":1000000.0,"range":false,"precision":3}},{"id":"219809","name":"Kod produktu","type":"string","required":false,"unit":null,"options":{"variantsAllowed":false,"variantsEqual":false},"restrictions":{"minLength":1,"maxLength":50,"allowedNumberOfValues":1}}]}
                .addParam("11325_2")
                .addDescriptionSection("IMAGE","https://e.allegroimg.com/original/01fd60/4ea8d18e4275b0878b7f0562067e")
                .addDescriptionSection("TEXT","<p>Tekstowy opis przedmiotu 1</p>")
                .addDescriptionSection("TEXT","<p>Tekstowy opis przedmiotu 2</p>")
                .addDescriptionSection("IMAGE","https://e.allegroimg.com/original/018474/c62b54a74120a7983b09d0fa4a4e")
                .addDescriptionSection("IMAGE","https://8.allegroimg.com/original/013a51/4dd0904a4169ad8b0d25a8984b48")
                .addDescriptionSection("IMAGE","https://2.allegroimg.com/original/01b48e/6e777def4a71ba68952358bd2072")
                .addImage("https://e.allegroimg.com/original/01fd60/4ea8d18e4275b0878b7f0562067e")
                .addImage("https://e.allegroimg.com/original/018474/c62b54a74120a7983b09d0fa4a4e")
                .addImage("https://8.allegroimg.com/original/013a51/4dd0904a4169ad8b0d25a8984b48")
                .addImage("https://2.allegroimg.com/original/01b48e/6e777def4a71ba68952358bd2072")
                .addSellinMode("BUY_NOW","1499")
                .addStock(4, "UNIT")
                .addPublication("P5D", "INACTIVE")
                .addDelivery("510340f5-b916-429a-bed2-8b0a40289438","P2D", "Dodatkowe informacje o dostawie")
                .addPayments("VAT")
                .addLocation("PL","LODZKIE","Bełchatów","97-400")
                .addAfterSellService("757e6a46-98b0-4e47-936d-adfa5abfcf49","51bccd3d-364c-47e0-9da1-5de1337db185")
//                .ean("123321")

                .build();

        AllegroOffertResponse response = allegro.createOfferDraft(token,request);
    }

    @Test
    public void createOffert(){
        //1. Utwórz/Pobierz kontakt przypisany do oferty
        // . Pobierz parametry kategorii
        //2. Przygotuj listę pakietów i opcji dodatkowych
        //3. Prześlij zdjęcia na nasz serwer
        //4. Tytuł ogłoszenia
        //5.
    }

    @Test
    @Ignore
    public void shouldDecryptedRSA() throws Exception {
        String jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTQ0MjQyMzAsInVzZXJfbmFtZSI6IjU4MTM3MjU0IiwianRpIjoiYTNiMzY1OWYtNzg0OS00MzJmLWE1ZDgtMjQwZDE1NjZhOTk1IiwiY2xpZW50X2lkIjoiZjBkZGRiNWRjM2JjNGY4Y2I5ZmFmMzYyZGRiOTg5MjMiLCJzY29wZSI6W119.fE4n1Ul7R1OuugeFzwK04xIweJ-i9IdN6ajyH4NM8-U7CQUTjtKsz_r1DK8dOIimHVmW9TJO4buj_IUlLcTGr1u281w5rHWsmt4W09VmDvZLw2iiwDzxJQUyvc_1wg-auB_kE_rpMZHMdDKjUUv_swqzw8EXGTM6pG1_A_GIIM5oTu8Y8Ndtcnlg58BabyPDp2fD8Qe9xov2UO-8eeIGnA09yu3OM_vYPFjTSjVVWdmsEAFCSgsLuuZMmk70QfpJwCWKYsvFBJaKB1t1cHg0IDxtZXC32RQAjOMIu8GBiicWJio9iPaK1Qt5H84KOPZtEB5VLQ9iSzXoOy3y54RSxA";

//        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0Iiwicm9sZXMiOiJST0xFX0FETUlOIiwiaXNzIjoibXlzZWxmIiwiZXhwIjoxNDcxMDg2MzgxfQ.1EI2haSz9aMsHjFUXNVz2Z4mtC0nMdZo6bo3-x-aRpw";
        System.out.println("------------ Decode JWT ------------");
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];

        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        Base64 base64Url = new Base64(true);
        String header = new String(base64Url.decode(base64EncodedHeader));
        System.out.println("JWT Header : " + header);


        System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
        String body = new String(base64Url.decode(base64EncodedBody));
        System.out.println("JWT Body : "+body);
    }


    @Test
    public void shouldReturnUserInfo() throws IOException{
        AllegroOffertShippingRatesDto resultDto = allegro.getUserInfo(token,"58137254");
        System.out.println("resultDto: " + resultDto);
    }

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}

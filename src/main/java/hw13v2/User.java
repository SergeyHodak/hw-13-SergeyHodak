package hw13v2;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

    @Data
    static class Address{
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;
    }

    @Data
    static class Geo{
        private double lat;
        private double lng;
    }

    @Data
    static class Company {
        private String name;
        private String catchPhrase;
        private String bs;
    }

    public static User defaultUser() {
        User    user    = new User();
        Geo     geo     = new Geo();
        Address address = new Address();
        Company company = new Company();

        user.setId(            8);
        user.setName(          "Сергей");
        user.setUsername(      "Serge");
        user.setEmail(         "sergey.hodak3@gmail.com");
        geo.setLat(            48.47073044006648d);
        geo.setLng(            35.028684139251716d);
        address.setStreet(     "Главная");
        address.setSuite(      "Д.9349");
        address.setCity(       "Днепр");
        address.setZipcode(    "49_000");
        address.setGeo(        geo);
        user.setAddress(       address);
        user.setPhone(         "+380119911011");
        user.setWebsite(       "www.facebook.com");
        company.setName(       "Laser_design.dp");
        company.setCatchPhrase("Всей роботы не переделать");
        company.setBs(         "Наружная и внутренняя реклама, декор");
        user.setCompany(       company);
        return user;
    }
}
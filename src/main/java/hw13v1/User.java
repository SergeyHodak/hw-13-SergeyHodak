package hw13v1;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User { // Объект Пользователь
    private int id;
    private String name;
    private String username;
    private String email;
    private Object lat;
    private Object lng;
    private Map<String, Object> address = new HashMap<>();
    private String phone;
    private String website;
    private String namec;
    private String catchPhrase;
    private String bs;
    private Map<String, String> company = new HashMap<>();

    User() { // задать коллекциям значения
        address.put("street", null);
        address.put("suite", null);
        address.put("city", null);
        address.put("zipcode", null);
        Map<String, Object> geo = new HashMap<>();
        geo.put("lat", lat);
        geo.put("lng", lng);
        address.put("geo", geo);
        company.put("name", namec);
        company.put("catchPhrase", catchPhrase);
        company.put("bs", bs);
    }

    // сеттеры и геттеры для полей класса
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public Map<String, Object> getAddress() {return address;}
    public void setAddress(Object street, Object suite, Object city, Object zipcode, Object lat, Object lng) {
        this.lat = lat;
        this.lng = lng;
        Map<String, Object> result = new HashMap<>();
        result.put("street", street);
        result.put("suite", suite);
        result.put("city", city);
        result.put("zipcode", zipcode);
        Map<String, Object> geo = new HashMap<>();
        geo.put("lat", this.lat);
        geo.put("lng", this.lng);
        result.put("geo", geo);
        address = result;
    }
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}
    public String getWebsite() {return website;}
    public void setWebsite(String website) {this.website = website;}
    public Map<String, String> getCompany() {return company;}
    public void setCompany(String namec, String catchPhrase, String bs) {
        this.namec = namec;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
        Map<String, String> result = new HashMap<>();
        result.put("name", this.namec);
        result.put("catchPhrase", this.catchPhrase);
        result.put("bs", this.bs);
        this.company = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!Objects.equals(name, user.name)) return false;
        if (!Objects.equals(username, user.username)) return false;
        if (!Objects.equals(email, user.email)) return false;
        return Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User {\n" +
                "    \"id\": " + id + ",\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"username\": \"" + username + "\",\n" +
                "    \"email\": \"" + email + "\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"" + address.get("street") + "\",\n" +
                "        \"suite\": \"" + address.get("suite") + "\",\n" +
                "        \"city\": \"" + address.get("city") + "\",\n" +
                "        \"zipcode\": \"" + address.get("zipcode") + "\",\n" +
                "        \"geo\": {\n" +
                "            \"lat\": \"" + lat + "\",\n" +
                "            \"lng\": \"" + lng + "\",\n" +
                "        }\n" +
                "    },\n" +
                "    \"phone\": \"" + phone + "\",\n" +
                "    \"website\": \"" + website + "\",\n" +
                "    \"company\": {\n" +
                "        \"name\": \"" + namec + "\",\n" +
                "        \"catchPhrase\": \"" + catchPhrase + "\",\n" +
                "        \"bs\": \"" + bs + "\",\n" +
                "    }\n" +
                "}";
    }

    public static User defaultUser() { // создаст дефолтный экземпляр Объекта Пользователь
        User user = new User();
        user.setId(10);
        user.setName("Сергей");
        user.setUsername("Serge");
        user.setEmail("sergey.hodak3@gmail.com");
        user.setAddress("Главная", "Д.9349", "Днепр",
                49_000, 48.47073044006648, 35.028684139251716);
        user.setPhone("+380119911011");
        user.setWebsite("https://www.facebook.com/profile.php?id=100015632683239");
        user.setCompany("Laser_design.dp", "Всей роботы не переделать",
                "Наружная и внутренняя реклама, декор");
        return user;
    }
}
package tests.petstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;
import java.util.Locale;

public class TestData {
    private static Faker faker = new Faker();
    static ObjectMapper objectMapper = new ObjectMapper();

    public static Integer petId = faker.number().numberBetween(1000, 9999);
    public static Integer notExistPetId = faker.number().numberBetween(999999999, 1000000000);
    public static String petName = faker.funnyName().name();
    public static String availableStatus = "available";
    public static String petTag = "test_tag";
    public static int petTagId = 1;

    public ObjectNode newPet = generateAvailablePet(petId, petName, availableStatus);
    public ObjectNode petWithTag = generatePetWithTag(petId);

    public String userName = faker.name().lastName().toLowerCase(Locale.ROOT);
    public String userPassword = faker.hipster().word();

    public static ObjectNode generateAvailablePet(int id, String name, String status) {
        ObjectNode pet = objectMapper.createObjectNode();
        pet.put("id", id);
        pet.put("name", name);
        pet.put("status", status);

        return pet;
    }

    public static ObjectNode generatePetWithTag(int id) {
        ObjectNode pet = objectMapper.createObjectNode();

        ArrayNode tags = objectMapper.createArrayNode();
        ObjectNode tag = objectMapper.createObjectNode();
        tag.put("id", petTagId);
        tag.put("name", petTag);
        tags.add(tag);

        pet.set("tags", tags);
        pet.put("id", id);

        return pet;
    }
}

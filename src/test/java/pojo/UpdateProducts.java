package pojo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateProducts {
    private String title;
    private float price;
    private String description;
    private String image;
    private String category;

    @Override
    public String toString(){
        return "{\n" +
                "    \"title\": \"test product\",\n" +
                "    \"price\": 13.5,\n" +
                "    \"description\": \"lorem ipsum set\",\n" +
                "    \"image\": \"https://i.pravatar.cc\",\n" +
                "    \"category\": \"electronic\"\n" +
                "}";
    }
}

package pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
    }
}

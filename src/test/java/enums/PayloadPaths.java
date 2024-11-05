package enums;

public enum PayloadPaths {

    LOGIN("src/test/resources/payloads/loginSuccess.json"),
    UPDATE_PRODUCT("src/test/resources/payloads/updateProduct.json");;

    private final String param;

    PayloadPaths(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}

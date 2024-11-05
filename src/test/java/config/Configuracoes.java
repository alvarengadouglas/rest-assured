package config;

import org.aeonbits.owner.Config;

@Config.Sources("file:src\\test\\resources\\test.properties")
public interface Configuracoes extends Config {

    @Key("baseURI")
    String baseUri();

}

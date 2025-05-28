package cursoSpringBoot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* Las clases controladoras se encargan de gestionar las peticiones http
* que llegan del lado del cliente
* */
@RestController
public class HelloWorldRestController {
    // al acceder al endpoint http://localhost:8080/hello o http://localhost:8080/hw o http://localhost:8080/hola
    // vamos a obtener el mensaje "hello world"
    @GetMapping({"/hello", "/hw", "/hola"})
    public String helloWorld() {
        return "hello world";
    }
}

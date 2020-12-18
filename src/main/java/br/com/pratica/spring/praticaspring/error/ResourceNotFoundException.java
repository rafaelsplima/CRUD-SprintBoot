package br.com.pratica.spring.praticaspring.error;

// RuntimeException - Para não ser uma expection do tipo checked (ao utiliza-la obriga try-catch)
// Checked são aquelas no qual você é obrigado a tratá-la, seja com um bloco try-catch ou mesmo com um throws
// Unchecked não é obrigatório o tratamento da mesma, você pode tratar apenas se quiser, se sentir que é necessário para o bom funcionamento da sua aplicação.

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Resposta HTTP - 404
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

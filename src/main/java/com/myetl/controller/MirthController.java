package com.myetl.controller;

import com.myetl.model.OruR01JsonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para recibir mensajes HL7v2 en formato JSON desde Mirth Connect.
 *
 * <p>Este controlador expone un endpoint para recibir y procesar mensajes
 * HL7v2 transformados a JSON.</p>
 */
@RestController
@RequestMapping("/api/hl7")
public class MirthController {

    /**
     * Endpoint para recibir y validar datos HL7v2 en formato JSON.
     *
     * <p>Este método recibe un objeto JSON enviado desde Mirth Connect,
     * lo imprime en la consola y responde con un mensaje de confirmación.</p>
     *
     * @param dto Objeto JSON basado en HL7v2 (ORU R01).
     * @return Confirmación de recepción.
     */
    @PostMapping("/receive")
    public ResponseEntity<String> receive(@RequestBody OruR01JsonDto dto) {
        System.out.println("JSON recibido:");
        System.out.println(dto);
        return ResponseEntity.ok("Mensaje recibido correctamente");
    }

}

package com.myetl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.myetl.model.OruR01JsonDto;
import com.myetl.service.FhirProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(MirthController.class);
    private final FhirProcessorService fhirProcessorService; // Inyecta el servicio

    // Instancia de ObjectMapper para convertir objetos Java a JSON (para depuración)
    private final ObjectMapper objectMapper;

    // Constructor para inyección de dependencias
    public MirthController(FhirProcessorService fhirProcessorService) {
        this.fhirProcessorService = fhirProcessorService;
        this.objectMapper = new ObjectMapper(); // Se inicializa el ObjectMapper
        // Habilita el formato de salida indentado para que el JSON sea legible en los logs tipo pretty print
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * Endpoint para recibir y validar datos HL7v2 en formato JSON.
     *
     * <p>Este método recibe un objeto JSON enviado desde Mirth Connect,
     * lo imprime en la consola y responde con un mensaje de confirmación para auditoria.</p>
     *
     * @param dto Objeto JSON basado en HL7v2 (ORU R^01).
     * @return Confirmación de recepción.
     */
    @PostMapping("/receive")
    public ResponseEntity<String> receive(@RequestBody OruR01JsonDto dto) {
        try {
            // Convierte el DTO de nuevo a una cadena JSON para imprimirlo en los logs
            String receivedJson = objectMapper.writeValueAsString(dto);
            logger.info("JSON recibido:\n{}", receivedJson);

            fhirProcessorService.processOruR01(dto);

            return ResponseEntity.ok("Mensaje recibido correctamente (procesamiento FHIR pendiente).");
        } catch (Exception e) {
            logger.error("Error al procesar el JSON recibido: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error al procesar el JSON: " + e.getMessage());
        }
    }
}
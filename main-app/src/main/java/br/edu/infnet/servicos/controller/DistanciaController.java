package br.edu.infnet.servicos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.servicos.dto.response.DistanciaResponseDTO;
import br.edu.infnet.servicos.service.DistanciaService;

@RestController
@RequestMapping("/api/distancia")
public class DistanciaController {

	
	private final DistanciaService distanciaService;
	
	public DistanciaController(DistanciaService distanciaService) {
        this.distanciaService = distanciaService;
    }
	
	@GetMapping("/consulta/{cepOrigem}/{cepCliente}")
    public ResponseEntity<DistanciaResponseDTO> consultarDistancia(@PathVariable String cepOrigem,
    		@PathVariable String cepCliente) {
        DistanciaResponseDTO resultado = distanciaService.calcularDistancia(cepOrigem, cepCliente);
        
        System.out.println("cepOrigem: " + cepOrigem);
        System.out.println("cepCliente: " + cepCliente);
        
        if (resultado == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(resultado);
    }
    
    @GetMapping("/teste-awesome/{cep}")
    public ResponseEntity<?> testarAwesomeCep(@PathVariable String cep) {
        try {
            var resultado = distanciaService.testarAwesomeCep(cep);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
    
    @GetMapping("/teste-openroute")
    public ResponseEntity<?> testarOpenRoute() {
        try {
            var resultado = distanciaService.testarOpenRoute();
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
}

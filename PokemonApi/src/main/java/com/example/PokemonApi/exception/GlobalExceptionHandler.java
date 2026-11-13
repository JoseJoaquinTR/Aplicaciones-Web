package com.example.PokemonApi.exception;

import com.example.PokemonApi.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// 400: falló alguna anotación de validación (@NotBlank, @Min, @Max...)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDTO> manejarValidacion(MethodArgumentNotValidException ex) {
		List<String> detalles = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.sorted()
				.toList();
		ErrorDTO error = new ErrorDTO("Error de validación: hay datos incorrectos en la petición", detalles);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	// 400: JSON mal formado o con tipos que no coinciden (ej. nivel: "abc", fecha: "2050-99-99")
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorDTO> manejarJsonInvalido(HttpMessageNotReadableException ex) {
		ErrorDTO error = new ErrorDTO("El cuerpo de la petición no se pudo leer",
				List.of("Revisa que el JSON esté bien formado, que los números sean números y que las fechas usen el formato yyyy-MM-dd"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	// 400: el id de la URL no es un número (ej. /api/pokemones/abc)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorDTO> manejarTipoIncorrecto(MethodArgumentTypeMismatchException ex) {
		ErrorDTO error = new ErrorDTO("Parámetro inválido",
				List.of("El valor '" + ex.getValue() + "' no es válido para '" + ex.getName() + "'"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	// 404: no existe el Pokémon buscado
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDTO> manejarNoEncontrado(ResourceNotFoundException ex) {
		ErrorDTO error = new ErrorDTO("Recurso no encontrado", List.of(ex.getMessage()));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	// 500: salvavidas para cualquier otro error inesperado
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDTO> manejarGenerico(Exception ex) {
		HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
		String mensaje = "Error interno del servidor";
		// errores propios de Spring (405, 415, ruta inexistente...) conservan su código original
		if (ex instanceof ErrorResponse errorSpring) {
			status = errorSpring.getStatusCode();
			mensaje = "No se pudo procesar la petición";
		}
		String detalle = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
		return ResponseEntity.status(status).body(new ErrorDTO(mensaje, List.of(detalle)));
	}

}

package com.example.PokemonApi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "El nombre es obligatorio y no puede estar vacío")
	@Size(max = 50, message = "El nombre no puede pasar de 50 caracteres")
	private String nombre;

	@NotBlank(message = "El tipo es obligatorio y no puede estar vacío")
	@Size(max = 30, message = "El tipo no puede pasar de 30 caracteres")
	private String tipo;

	@NotNull(message = "El nivel es obligatorio")
	@Min(value = 1, message = "El nivel mínimo es 1")
	@Max(value = 100, message = "El nivel máximo es 100")
	private Integer nivel;

	@NotNull(message = "El ataque es obligatorio")
	@PositiveOrZero(message = "El ataque no puede ser negativo")
	private Integer ataque;

	@NotNull(message = "La defensa es obligatoria")
	@PositiveOrZero(message = "La defensa no puede ser negativa")
	private Integer defensa;

	@Size(max = 500, message = "La descripción no puede pasar de 500 caracteres")
	@Column(length = 500)
	private String descripcion;

	@Pattern(regexp = "^https?://\\S+$", message = "La URL de la imagen debe empezar con http:// o https://")
	private String imagenUrl;

	@PastOrPresent(message = "La fecha de captura no puede ser futura")
	private LocalDate fechaCaptura;

}

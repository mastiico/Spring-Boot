package br.com.marcio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcio.exceptions.UnsupportedMathOperationException;
import br.com.marcio.model.MathModel;

@RestController
public class MathController extends MathModel {

	@GetMapping("/sum/{numberOne}/{calc}/{numberTwo}")
	public Double sum(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "calc") String calc,
			@PathVariable(value = "numberTwo") String numberTwo
		) throws Exception {

		if(!isNumeric(numberOne) || !isNumeric(numberTwo)){  
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		};
		return resultado(numberOne, calc, numberTwo);
	}
}

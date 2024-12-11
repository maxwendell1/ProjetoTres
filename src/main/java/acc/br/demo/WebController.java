package acc.br.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {
	@Autowired
	public ScoreRepository scoreRepo;
	
	@ResponseBody
	@GetMapping("/score")
	public Score getScore(){
		Score score;
		try{
			score = scoreRepo.findById(new Integer(1)).get();
		}
		catch (Exception e){
			score = new Score(0,0,0);
			scoreRepo.save(score);
		}
		return score;
	}
	
	
	@GetMapping("/teste")
	public String teste(@RequestParam(name="escolha")String aEscolha, Model model){
		String saida = "Empate";
		if (aEscolha.equalsIgnoreCase("Papel")) {
			saida = "Ganhou!";
		}
		if (aEscolha.equalsIgnoreCase("Tesoura")) {
			saida = "Perdeu!";
		}
		model.addAttribute("saida", saida);
		model.addAttribute("aEscolha", aEscolha);
		return "resultado";
	}
}

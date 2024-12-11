package acc.br.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    @Autowired
    public ScoreRepository scoreRepo;

    @ResponseBody
    @GetMapping("/score")
    public Score getScore() {
        Score score;
        try {
            score = scoreRepo.findById(1).orElseThrow();
        } catch (Exception e) {
            score = new Score(0, 0, 0);
            scoreRepo.save(score);
        }
        return score;
    }

    @PostMapping("/score/increment")
    @ResponseBody
    public String incrementScore(@RequestParam(name = "type") String type) {
        try {
            Score score = scoreRepo.findById(1).orElseThrow();
            switch (type) {
                case "Ganhou!":
                    score.setVitorias(score.getVitorias() + 1);
                    break;
                case "Perdeu!":
                    score.setDerrotas(score.getDerrotas() + 1);
                    break;
                case "Empate":
                    score.setEmpates(score.getEmpates() + 1);
                    break;
                default:
                    return "Tipo Inválido";
            }
            scoreRepo.save(score);
            return "Score updated successfully!";
        } catch (Exception e) {
            return "Error updating score: " + e.getMessage();
        }
    }

    @PostMapping("/score/reset")
    public String resetScore() {
        try {
            Score score = scoreRepo.findById(1).orElseThrow();
            score.setVitorias(0);
            score.setDerrotas(0);
            score.setEmpates(0);
            scoreRepo.save(score);
        } catch (Exception e) {
            e.getMessage();
        }
       return "redirect:/";
    }

    @GetMapping("/teste")
    public String teste(@RequestParam(name = "escolha") String aEscolha, Model model) {
        String saida = "Empate";
        Score score = getScore();
        try {
            score = scoreRepo.findById(1).orElseThrow();
            if (aEscolha.equalsIgnoreCase("Papel")) {
                saida = "Ganhou!";
                score.setVitorias(score.getVitorias() + 1);
            } else if (aEscolha.equalsIgnoreCase("Tesoura")) {
                saida = "Perdeu!";
                score.setDerrotas(score.getDerrotas() + 1);
            } else {
                score.setEmpates(score.getEmpates() + 1);
            }
            scoreRepo.save(score);
        } catch (Exception e) {
            saida = "Erro ao atualizar o score: " + e.getMessage();
        }
        model.addAttribute("saida", saida.toUpperCase());
        model.addAttribute("aEscolha", aEscolha.toUpperCase());
        model.addAttribute("vitorias", score.getVitorias());
        model.addAttribute("derrotas", score.getDerrotas());
        model.addAttribute("empates", score.getEmpates());
        return "resultado";
    }
}

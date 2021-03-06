package org.healthclinics.controllers;

import java.time.LocalDate;

import org.healthclinics.daos.PacienteDAO;
import org.healthclinics.model.Paciente;
import org.healthclinics.model.ProfissionalSaude;
import org.healthclinics.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AcessoController {
	@Autowired
	PacienteDAO pacientedao;
	@Autowired
	 EspecializacaoDAO espdao;
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String entrarForm() {
		return "loginForm";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Usuario usuario) {
		System.out.println(usuario.toString());
		return "index";
	}

	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public String paginaCadastro() {
		return "cadastro";
	}

	// Cadastro de Pacientes
	@RequestMapping(value = "/cadastro/paciente",name = "cad_pac", method = RequestMethod.POST)
	public ModelAndView cadastrar(Paciente paciente, String senhaP, String senhaRepetir, String dataNasc,
			String numeroCPF) {
		System.out.println(dataNasc);
		paciente.setDataNascimento(LocalDate.parse(dataNasc));
		if (!senhaP.equals(senhaRepetir)) {
			return new ModelAndView("erro");
		}
		paciente.setSenha(senhaP);
		paciente.setCpf(numeroCPF);
		System.out.println(paciente.toString());
		pacientedao.salva(paciente);
		ModelAndView andView = new ModelAndView("index");
		return andView;
	}

	@RequestMapping(value = "/cadastro/paciente",name = "cad_pac_bd", method = RequestMethod.GET)
	public String cadastroPaciente() {
		return "cadastro-p";
	}

	// Cadastro Profissonal da Saude
	@RequestMapping(value = "/cadastro/profsaude",name = "cad_prof", method = RequestMethod.GET)
	public String cadastroProfSaude() {
		
		return "cadastro-e";
	}
	@RequestMapping(value = "/cadastro/profsaude",name = "cad_prof_bd", method = RequestMethod.POST)
	public String cadastroProfSaudebd(ProfissionalSaude pf, String especialidade) {
		System.out.println(pf.toString()+especialidade);
		return "index";
	}
	@ModelAttribute("especialidades")
	public void getEspecializacao(Model model){
	     model.addAttribute("especialidades", espdao.getEspecializacoes());
	}
}

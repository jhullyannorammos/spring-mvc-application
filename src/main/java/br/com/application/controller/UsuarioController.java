package br.com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

import javax.validation.Valid;
import br.com.application.model.Grupo;
import br.com.application.model.Usuario;
import br.com.application.service.GrupoService;
import br.com.application.service.UsuarioService;

import java.util.List;

@Controller
@RequestMapping("/usuario") 
public class UsuarioController {
	
	@Autowired private GrupoService grupoService; 
	@Autowired private UsuarioService usuarioService;
	
	@RequestMapping(value="/novo", method= RequestMethod.GET)	
	public ModelAndView novo(Model model) {
		model.addAttribute("grupos", grupoService.consultarGrupos());
		model.addAttribute("usuario", new Usuario());
	    return new ModelAndView("novo");
	}
	
	@RequestMapping(value="/cadastro", method= RequestMethod.POST)
	public ModelAndView salvarUsuario(@ModelAttribute 
								@Valid Usuario usuario, 
								final BindingResult result,
								Model model,
								RedirectAttributes redirectAttributes){
		if(result.hasErrors()){
			List<Grupo> grupos = grupoService.consultarGrupos();			
			grupos.forEach(grupo ->{
				if(usuario.getGrupos() != null && usuario.getGrupos().size() > 0){
					usuario.getGrupos().forEach(grupoSelecionado->{
						if(grupoSelecionado!= null){
							if(grupo.getId().equals(grupoSelecionado))
								grupo.setChecked(true);
						}					
					});				
				}
			});
			model.addAttribute("grupos", grupos);
			model.addAttribute("usuario", usuario);
			return new ModelAndView("cadastro");	
		}
		else{
			usuarioService.persist(usuario);
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/usuario/novoCadastro");
		redirectAttributes.addFlashAttribute("msg_resultado", "Registro salvo com sucesso!");
		return modelAndView;
	}
	
	@RequestMapping(value="/consulta", method= RequestMethod.GET)	
	public ModelAndView consultar(Model model) {
		model.addAttribute("usuarios", this.usuarioService.findAll());
	    return new ModelAndView("consulta");
	}
	
	@RequestMapping(value="/excluir", method= RequestMethod.POST)
	public ModelAndView excluir(@RequestParam("codigoUsuario") Long codigo){
		ModelAndView modelAndView = new ModelAndView("redirect:/usuario/consultar");
		this.usuarioService.remove(codigo);
		return modelAndView;
	}
	
	@RequestMapping(value="/editarCadastro", method= RequestMethod.GET)		
	public ModelAndView editarCadastro(@RequestParam("codigoUsuario") Long codigoUsuario, Model model) {
		List<Grupo> grupos = grupoService.consultarGrupos();			
		Usuario usuario = this.usuarioService.findBy(codigoUsuario);
		grupos.forEach(grupo ->{
			usuario.getGrupos().forEach(grupoCadastrado->{
				if(grupoCadastrado!= null){
					if(grupo.getId().equals(grupoCadastrado))
						grupo.setChecked(true);
				}					
			});				
		});
		model.addAttribute("grupos", grupos);
		model.addAttribute("usuarioModel", usuario);
	    return new ModelAndView("editarCadastro");
	}
	
	@RequestMapping(value="/edicao", method= RequestMethod.POST)
	public ModelAndView edicao(@ModelAttribute 
								@Valid 
								Usuario usuario, 
								final BindingResult result,
								Model model,
								RedirectAttributes redirectAttributes){
		
		boolean isErroNullCampos = false;
		for (FieldError fieldError : result.getFieldErrors()) {
			if(!fieldError.getField().equals("senha")){
				isErroNullCampos = true;	
			}	
		}
		if(isErroNullCampos){
			
			List<Grupo> grupos = grupoService.consultarGrupos();			
			
			grupos.forEach(grupo ->{
				if(usuario.getGrupos() != null && usuario.getGrupos().size() >0){
					usuario.getGrupos().forEach(grupoSelecionado->{
						if(grupoSelecionado!= null){
							if(grupo.getId().equals(grupoSelecionado))
								grupo.setChecked(true);
						}					
					});				
				}
				
			});
			model.addAttribute("grupos", grupos);
			model.addAttribute("usuario", usuario);
			return new ModelAndView("edicao");	
		}
		else{
			usuarioService.update(usuario);
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/usuario/consultar");
		return modelAndView;
	}

}

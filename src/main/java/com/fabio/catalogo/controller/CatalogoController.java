package com.fabio.catalogo.controller;

import com.fabio.catalogo.service.CatalogoService;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.fabio.catalogo.model.Musica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CatalogoController {

    @Autowired
    CatalogoService catalogoService;

    @RequestMapping(value = "/musicas", method = RequestMethod.GET)
    public ModelAndView getMusicas() {
        ModelAndView mv = new ModelAndView("musicas");
        List<Musica> musicas = catalogoService.findAll();
        mv.addObject("musicas", musicas);
        return mv;
    }

    @RequestMapping(value = "/musicas/{id}", method = RequestMethod.GET)
    public ModelAndView getMusicasDetalhes(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("musicaDetalhes");
        Musica musica = catalogoService.findById(id);
        mv.addObject("musica", musica);
        return mv;
    }

    @RequestMapping(value = "/updateMusica/{id}", method = RequestMethod.GET)
    public ModelAndView updateMusica(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("musicaForm");
        Musica musica = catalogoService.findById(id);
        mv.addObject("musica", musica);
        return mv;
    }

    @RequestMapping(value = "/deleteMusica/{id}", method = RequestMethod.GET)
    public String deleteMusica(@PathVariable("id") long id, RedirectAttributes attributes) {
        catalogoService.deleteById(id);
        return "redirect:/musicas";
    }

    @RequestMapping(value = "/addMusica", method = RequestMethod.GET)
    public String getMusicaForm() {
        return "musicaForm";
    }

    @RequestMapping(value = "/addMusica", method = RequestMethod.POST)
    public String salvarMusicas(@Valid Musica musica, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Campos obrigatórios não preenchidos!");
            return "redirect:/addMusica";
        }
        musica.setData(LocalDate.now());
        catalogoService.save(musica);
        return "redirect:/musicas";
    }

}
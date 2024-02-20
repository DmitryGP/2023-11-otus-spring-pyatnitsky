package org.dgp.hw.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.dgp.hw.dto.BookCreateDto;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.dto.BookUpdateDto;
import org.dgp.hw.exceptions.NotFoundException;
import org.dgp.hw.mappers.BookMapper;
import org.dgp.hw.services.AuthorService;
import org.dgp.hw.services.BookService;
import org.dgp.hw.services.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookMapper bookMapper;

    @GetMapping("/")
    public String listPage(Model model) {
        var books = bookService.findAll();
        model.addAttribute("books", books);

        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        var book = bookService.findById(id);
        var authors = authorService.findAll();
        var genres = genreService.findAll();

        model.addAttribute("book", bookMapper.toDto(book));
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);

        return "edit";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        var newBook = new BookCreateDto();
        var authors = authorService.findAll();
        var genres = genreService.findAll();

        model.addAttribute("book", newBook);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);

        return "create";
    }

    @PutMapping("/edit")
    public String editBook( @Valid BookUpdateDto book) {

        bookService.update(book);

        return "redirect:/";
    }

    @PostMapping("/create")
    public String createBook(@Valid BookCreateDto book) {

        bookService.create(book);

        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);

        return "redirect:/";
    }


}

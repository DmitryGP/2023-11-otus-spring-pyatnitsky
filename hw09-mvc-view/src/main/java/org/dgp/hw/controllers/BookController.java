package org.dgp.hw.controllers;

import lombok.AllArgsConstructor;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.services.AuthorService;
import org.dgp.hw.services.BookService;
import org.dgp.hw.services.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String listPage(Model model) {
        var books = bookService.findAll();
        model.addAttribute("books", books);

        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        var book = bookService.findById(id).orElseThrow(NotFoundException::new);
        var authors = authorService.findAll();
        var genres = genreService.findAll();

        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);

        return "edit";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        var newBook = new BookDto();
        var authors = authorService.findAll();
        var genres = genreService.findAll();

        model.addAttribute("book", newBook);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);

        return "edit";
    }

    @PostMapping("/edit")
    public String saveBook(BookDto book) {
        if (book.getId() == 0) {
            bookService.create(book.getTitle(), book.getAuthor().getId(), book.getGenre().getId());
        } else {
            bookService.update(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getGenre().getId());
        }

        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);

        return "redirect:/";
    }


}

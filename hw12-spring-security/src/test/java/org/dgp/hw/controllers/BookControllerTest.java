package org.dgp.hw.controllers;

import org.dgp.hw.dto.AuthorDto;
import org.dgp.hw.dto.BookCreateDto;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.dto.BookUpdateDto;
import org.dgp.hw.dto.GenreDto;
import org.dgp.hw.mappers.BookMapper;
import org.dgp.hw.security.repositories.UserRepository;
import org.dgp.hw.security.configuration.SecurityConfiguration;
import org.dgp.hw.services.AuthorService;
import org.dgp.hw.services.BookService;
import org.dgp.hw.services.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(SecurityConfiguration.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookMapper bookMapper;

    @MockBean
    private UserRepository userRepository;


    @Test
    @WithMockUser
    void shouldReturnListOfBooks() throws Exception {
        var books = getBooks();
        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attribute("books", books));
    }

    @Test
    @WithMockUser
    void shouldReturnEditPage() throws Exception {
        var books = getBooks();
        var book = books.stream().filter(b -> b.getId() == 1).findAny();
        when(bookService.findById(1)).thenReturn(book.get());
        var bookUpdateDto = new BookUpdateDto(book.get().getId(),
                book.get().getTitle(),
                book.get().getAuthor(),
                book.get().getGenre());
        when(bookMapper.toDto(book.get()))
                .thenReturn(bookUpdateDto);

        var authors = getAuthors();
        var genres = getGenres();

        when(authorService.findAll()).thenReturn(authors);
        when(genreService.findAll()).thenReturn(genres);

        mockMvc.perform(get("/edit?id=1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().size(3))
                .andExpect(model().attribute("book", bookUpdateDto))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres));
    }

    @Test
    @WithMockUser
    void shouldReturnCreatePage() throws Exception {
        var authors = getAuthors();
        var genres = getGenres();

        when(authorService.findAll()).thenReturn(authors);
        when(genreService.findAll()).thenReturn(genres);

        mockMvc.perform(get("/create")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres));
    }

    @Test
    @WithMockUser
    void shouldSaveNewBook() throws Exception {
        var bookServiceInOrder = inOrder(bookService);

        var genre = getGenres().get(0);
        var author = getAuthors().get(0);

        mockMvc.perform(post("/create")
                        .with(csrf())
                        .content("title=The Best Book&author.id=1&genre.id=1")
                        .contentType("application/x-www-form-urlencoded"))
                .andExpect(status().is(302));

        var authorDto = AuthorDto.builder()
                .id(author.getId()).build();
        var genreDto = GenreDto.builder()
                .id(genre.getId()).build();

        var bookDto = new BookCreateDto("The Best Book", authorDto, genreDto);

        bookServiceInOrder
                .verify(bookService, times(1))
                .create(bookDto);
    }

    @Test
    @WithMockUser
    void shouldSaveBookWhenEditIt() throws Exception {
        var bookServiceInOrder = inOrder(bookService);

        var genre = getGenres().stream().filter(g -> g.getId() == 2).findAny().get();
        var author = getAuthors().stream().filter(a -> a.getId() == 2).findAny().get();

        mockMvc.perform(put("/edit")
                        .with(csrf())
                        .param("id", "1")
                        .content("title=The Best Book&author.id=2&genre.id=2")
                        .contentType("application/x-www-form-urlencoded"))
                .andExpect(status().is(302));

        var authorDto = AuthorDto.builder()
                .id(author.getId()).build();
        var genreDto = GenreDto.builder()
                .id(genre.getId()).build();

        var bookDto = new BookUpdateDto(1L, "The Best Book", authorDto, genreDto);

        bookServiceInOrder
                .verify(bookService, times(1))
                .update(bookDto);
    }

    @Test
    @WithAnonymousUser
    void getEditPageShouldReturn4xxWhenAnonymousUser() throws Exception {
        var books = getBooks();
        var book = books.stream().filter(b -> b.getId() == 1).findAny();
        when(bookService.findById(1)).thenReturn(book.get());
        var bookUpdateDto = new BookUpdateDto(book.get().getId(),
                book.get().getTitle(),
                book.get().getAuthor(),
                book.get().getGenre());
        when(bookMapper.toDto(book.get()))
                .thenReturn(bookUpdateDto);

        var authors = getAuthors();
        var genres = getGenres();

        when(authorService.findAll()).thenReturn(authors);
        when(genreService.findAll()).thenReturn(genres);

        mockMvc.perform(get("/edit?id=1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
    @Test
    @WithAnonymousUser
    void getShouldReturn4xxWhenAnonymousUser() throws Exception {
        var books = getBooks();
        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithAnonymousUser
    void createPageShouldReturn4xxWhenAnonymousUser() throws Exception {
        var authors = getAuthors();
        var genres = getGenres();

        when(authorService.findAll()).thenReturn(authors);
        when(genreService.findAll()).thenReturn(genres);

        mockMvc.perform(get("/create")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithAnonymousUser
    void saveBookShouldReturn4xxWhenAnonymousUser() throws Exception {

        mockMvc.perform(post("/create")
                        .with(csrf())
                        .content("title=The Best Book&author.id=1&genre.id=1")
                        .contentType("application/x-www-form-urlencoded"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithAnonymousUser
    void editBookShouldReturn4xxWhenAnonymousUser() throws Exception {
        mockMvc.perform(put("/edit")
                        .with(csrf())
                        .param("id", "1")
                        .content("title=The Best Book&author.id=2&genre.id=2")
                        .contentType("application/x-www-form-urlencoded"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    @Test
    @WithMockUser
    void shouldDeleteBook() throws Exception {
        var bookServiceInOrder = inOrder(bookService);

        mockMvc.perform(post("/delete?id=1")
                        .with(csrf()))
                .andExpect(status().is(302));

        bookServiceInOrder
                .verify(bookService, times(1))
                .deleteById(1);
    }

    @Test
    @WithAnonymousUser
    void deleteBookShouldReturn4xxWhenAnonymousUser() throws Exception {
        mockMvc.perform(post("/delete?id=1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    private List<GenreDto> getGenres() {
        return List.of(GenreDto.builder()
                        .id(1)
                        .name("Genre1")
                        .build(),
                GenreDto.builder()
                        .id(2)
                        .name("Genre2")
                        .build());
    }

    private List<AuthorDto> getAuthors() {
        return List.of(AuthorDto.builder()
                        .id(1)
                        .fullName("Author1")
                        .build(),
                AuthorDto.builder()
                        .id(2)
                        .fullName("Author2")
                        .build());
    }

    private static List<BookDto> getBooks() {
        return List.of(
                BookDto.builder()
                        .id(1)
                        .title("Book1")
                        .author(AuthorDto.builder()
                                .id(1)
                                .fullName("Author1").build())
                        .genre(GenreDto.builder()
                                .id(1)
                                .name("Genre1").build())
                        .build(),
                BookDto.builder()
                        .id(2)
                        .title("Book2")
                        .author(AuthorDto.builder()
                                .id(2)
                                .fullName("Author2").build())
                        .genre(GenreDto.builder()
                                .id(2)
                                .name("Genre2").build())
                        .build());
    }

}

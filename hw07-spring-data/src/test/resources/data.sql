insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3');

insert into books(title, author_id, genre_id)
values ('BookTitle_1', 1, 1), ('BookTitle_2', 2, 2), ('BookTitle_3', 3, 3);

insert into comments(text, book_id)
values ('CommentText_1', 2), ('CommentText_2', 3), ('CommentText_3', 1),
        ('CommentText_4', 2), ('CommentText_5', 3), ('CommentText_6', 1);

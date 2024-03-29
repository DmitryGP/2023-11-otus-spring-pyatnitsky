insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3'), ('Author_4');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'), ('Genre_4');

insert into books(title, author_id, genre_id)
values ('BookTitle_1', 1, 1), ('BookTitle_2', 2, 2), ('BookTitle_3', 3, 3);

insert into comments(text, book_id)
values ('Comment_1', 1), ('Comment_2', 1), ('Comment_3', 2), ('Comment_4', 3), ('Comment_5', 3);

insert into users(username, password)
values  ('user1', '$2a$10$f.aAUho6ptyHCsXcH.8MSe/dO6hipk/Eq2OwVRNEtJtzhvLSRhTn2'), --password1
        ('user2', '$2a$10$NtJcT7983wJUEQiglsZEy.nHPpjmlxVSERdhfByaXaPvT06C9QsT6'), --password2
        ('admin', '$2a$10$E/BaWuvCh9CCTQOyTmExNuczrToBuXpWRecYkYuMPBgVvyoQyjTq.'); --adminPassword

insert into roles(name)
values ('ROLE_USER'),
values ('ROLE_ADMIN');

insert into user_role(user_id, role_id)
values (1, 1), (2, 1), (3, 2);

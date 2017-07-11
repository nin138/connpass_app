CREATE TABLE black_list(
  id int PRIMARY KEY,
  title text,
  url text
);

INSERT INT black_list VALUES (?,?,?);
SELECT id, title, url FROM black_list;

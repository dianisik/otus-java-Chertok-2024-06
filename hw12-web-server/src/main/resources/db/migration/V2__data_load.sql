insert into public.client (id,"name") values
                                          (1,'Вася'),
                                          (2,'Петя'),
                                          (3,'Владимир'),
                                          (4,'Елена');
select setval('"client_seq"', 4);

insert into public.address (street,client_id) values
                                                  ('Тверская 4',1),
                                                  ('Садовая 6',2),
                                                  ('Ленина 8',3),
                                                  ('Троцкого 10',4);


insert into public.phone ("number",client_id) values
                                                  ('495 2232222',1),
                                                  ('921 2348738',1),
                                                  ('495 9893434',2),
                                                  ('985 2343211',2),
                                                  ('912 5556677',3),
                                                  ('911 2328484',3),
                                                  ('912 5559823',3),
                                                  ('912 9871234',4);

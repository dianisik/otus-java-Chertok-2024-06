CREATE TABLE "user"
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name     VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT pk_user PRIMARY KEY (id)
);



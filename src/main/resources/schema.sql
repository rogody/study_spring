create table if not exists user(
    user_id bigint  AUTO_INCREMENT  primary key,
    username    varchar(50) not null,
    password    varchar(100),
    unique (username)
);

create table if not exists diary(
    diary_id    bigint  AUTO_INCREMENT PRIMARY KEY,
    title   text,
    content text,
    record_day  date,
    created_at  datetime default current_timestamp,
    user_id bigint not null,
    foreign key(user_id) references user(user_id)
);
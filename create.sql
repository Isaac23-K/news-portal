CREATE DATABASE company_news;
\c company_news;


CREATE TABLE users( id SERIAL PRIMARY KEY , username VARCHAR , idDepartment INT , role VARCHAR, position VARCHAR);
CREATE TABLE news( id SERIAL PRIMARY KEY, content VARCHAR, idDepartment INT, usernameid VARCHAR);
CREATE TABLE departments(id SERIAL PRIMARY KEY, nameOfDepartment VARCHAR, detail VARCHAR, numberEmployees INTEGER);

CREATE DATABASE company_news_test WITH TEMPLATE company_news;
\q
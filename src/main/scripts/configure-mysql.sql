# docker run --name mysqldb -p 3306:3306 - MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

#Create Databases
CREATE DATABASE TDD_dev;
CREATE DATABASE TDD_prod;

#Create database service accounts
CREATE USER 'TDD_dev_user'@'localhost' IDENTIFIED BY '12345';
CREATE USER 'TDD_prod_user'@'localhost' IDENTIFIED BY '12345';
CREATE USER 'TDD_dev_user'@'%' IDENTIFIED BY '12345';
CREATE USER 'TDD_prod_user'@'%' IDENTIFIED BY '12345';

#Database grants
GRANT SELECT ON TDD_dev.* to 'TDD_dev_user'@'localhost';
GRANT INSERT ON TDD_dev.* to 'TDD_dev_user'@'localhost';
GRANT DELETE ON TDD_dev.* to 'TDD_dev_user'@'localhost';
GRANT UPDATE ON TDD_dev.* to 'TDD_dev_user'@'localhost';
GRANT SELECT ON TDD_prod.* to 'TDD_prod_user'@'localhost';
GRANT INSERT ON TDD_prod.* to 'TDD_prod_user'@'localhost';
GRANT DELETE ON TDD_prod.* to 'TDD_prod_user'@'localhost';
GRANT UPDATE ON TDD_prod.* to 'TDD_prod_user'@'localhost';
GRANT SELECT ON TDD_dev.* to 'TDD_dev_user'@'%';
GRANT INSERT ON TDD_dev.* to 'TDD_dev_user'@'%';
GRANT DELETE ON TDD_dev.* to 'TDD_dev_user'@'%';
GRANT UPDATE ON TDD_dev.* to 'TDD_dev_user'@'%';
GRANT SELECT ON TDD_prod.* to 'TDD_prod_user'@'%';
GRANT INSERT ON TDD_prod.* to 'TDD_prod_user'@'%';
GRANT DELETE ON TDD_prod.* to 'TDD_prod_user'@'%';
GRANT UPDATE ON TDD_prod.* to 'TDD_prod_user'@'%';
create database GreenCartSecurity;
drop database GreenCartSecurity;
use GreenCartSecurity;

INSERT INTO roles (nombre) VALUES ('VENDEDOR');
INSERT INTO roles (nombre) VALUES ('COMPRADOR');
INSERT INTO roles (nombre) VALUES ('ADMINISTRADOR');

INSERT INTO categorias VALUES (1, 'Bolsas', 1);
INSERT INTO categorias VALUES (2, 'Letras', 1);

INSERT INTO PRODUCTOS VALUES (1, 500,'Es una bolsa', 1, 1, "CodigoImagen","Bolsa",1);
INSERT INTO PRODUCTOS VALUES (2, 500,'Es una letra', 1, 1, "CodigoImagen","A",2);
INSERT INTO PRODUCTOS VALUES (3, 500,'Es una letra', 1, 1, "CodigoImagen","B",2);
INSERT INTO PRODUCTOS VALUES (4, 500,'Es una letra', 1, 1, "CodigoImagen","C",2);

select * from usuario;
select * from roles;
select * from usuario_rol;
select * from productos;
create database GreenCartSecurity;
drop database GreenCartSecurity;
use GreenCartSecurity;

/*Solo ingresar los roles y categorias*/
INSERT INTO roles (nombre) VALUES ('VENDEDOR');
INSERT INTO roles (nombre) VALUES ('COMPRADOR');

INSERT INTO categorias (id_categoria, descripcion, estado) VALUES
(6, 'Bolsas Reutilizables', 1),
(7, 'Artículos Compostables', 1),
(3, 'Limpieza Ecológica', 1),
(4, 'Productos de Higiene Natural', 1),
(5, 'Accesorios Eco-Amigables', 1);

select * from usuario;
select * from roles;
select * from usuario_rol;
select * from productos;
select * from pedido;
select * from pedido_item;
select * from reclamos;
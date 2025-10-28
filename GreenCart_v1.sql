create database GreenCartSecurity;
drop database GreenCartSecurity;
use GreenCartSecurity;

/*Solo ingresar los roles y categorias*/
INSERT INTO roles (nombre) VALUES ('VENDEDOR');
INSERT INTO roles (nombre) VALUES ('COMPRADOR');
INSERT INTO roles (nombre) VALUES ('ADMINISTRADOR');

INSERT INTO categorias VALUES (1, 'Bolsas', 1);
INSERT INTO categorias VALUES (2, 'Letras', 1);

/*Por el momento no ingresar esto*/
INSERT INTO PRODUCTOS (nombre, precio, descripcion, id_categoria, cantidad_stock, imagen_Producto, estado, vendedor_id)
VALUES ('Bolsa', 500, 'Es una bolsa', 1, 1, 'CodigoImagen', true, 1);

select * from usuario;
select * from roles;
select * from usuario_rol;
select * from productos;
select * from pedido;
select * from pedido_item;

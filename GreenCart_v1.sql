DROP DATABASE IF EXISTS GreenCart_v1;
CREATE DATABASE IF NOT EXISTS GreenCart_v1;
USE GreenCart_v1;

CREATE TABLE Roles (
    id_rol INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Usuario (
    id_usuario INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    telefono BIGINT NOT NULL,
    dni BIGINT NOT NULL UNIQUE,
    correo VARCHAR(200) NOT NULL UNIQUE,
    contraseña VARCHAR(200) NOT NULL
);

CREATE TABLE CATEGORIAS (
  id_categoria INT AUTO_INCREMENT NOT NULL,
  descripcion VARCHAR(45) NOT NULL,
  estado BOOLEAN NOT NULL,
  PRIMARY KEY (id_categoria)
);

CREATE TABLE PRODUCTOS (
  id_producto INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  nombre VARCHAR(45),
  id_categoria INT NOT NULL,
  descripcion VARCHAR(50) NOT NULL,
  precio DECIMAL(16,2),
  cantidad_stock INT NOT NULL,
  imagen VARCHAR(150),
  estado BOOLEAN,
  CONSTRAINT fk_PRODUCTOS_CATEGORIAS
    FOREIGN KEY (id_categoria) REFERENCES CATEGORIAS (id_categoria)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

INSERT INTO categorias VALUES (1, 'Bolsas', 1);
INSERT INTO categorias VALUES (2, 'Letras', 1);

INSERT INTO PRODUCTOS VALUES (1, 'Bolsa', 1, 'Es una bolsa', 1, 500, "CodigoImagen","1");
INSERT INTO PRODUCTOS VALUES (2, 'A', 1, 'Es una letra', 2, 500, "CodigoImagen","1");
INSERT INTO PRODUCTOS VALUES (3, 'B', 1, 'Es una letra', 2, 500, "CodigoImagen","1");
INSERT INTO PRODUCTOS VALUES (4, 'C', 1, 'Es una letra', 2, 500, "CodigoImagen","1");

select * from PRODUCTOS;
select * from categorias;

CREATE TABLE Usuario_Rol (
    id_usuario INT NOT NULL,
    id_rol INT NOT NULL,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES Roles(id_rol) ON DELETE CASCADE
);

CREATE TABLE Pedido (
    id_pedido INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Pedido_Item (
    id_item INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    id_pedido INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_producto) REFERENCES PRODUCTOS(id_producto),
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id_pedido)
);

CREATE TABLE Reclamo (
    id_reclamo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    rol VARCHAR(50) NOT NULL,
    fecha_pedido DATE NOT NULL,
    motivo_reclamo VARCHAR(200) NOT NULL,
    detalle TEXT NOT NULL,
    estado ENUM('Pendiente', 'En Proceso', 'Resuelto', 'Rechazado') NOT NULL,
    fecha_reclamo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- Insertar roles
INSERT INTO Roles (nombre) VALUES 
('ROLE_ADMIN'),
('ROLE_VENDEDOR'),
('ROLE_COMPRADOR');

-- Insertar usuarios
INSERT INTO Usuario (nombre, apellido, direccion, telefono, dni, correo, contraseña) VALUES
('Juan', 'Pérez', 'Libertadores 123', 987654321, 72345678, 'juan.perez@gmail.com', 'contra1234'), 
('Ana', 'Rodríguez', 'Av. Abancay 742', 912345678, 77654321, 'ana.rodriguez@gmail.com', 'clave123'), 
('Carlos', 'Ramírez', 'Calle Falsa 123', 923456789, 78901234, 'carlos.ramirez@gmail.com', 'clave456'); 



-- Asignar roles a usuarios
INSERT INTO Usuario_Rol (id_usuario, id_rol) VALUES
(1, 1), -- ADMIN
(2, 3), -- COMPRADOR
(3, 2); -- VENDEDOR

-- Insertar pedidos
INSERT INTO Pedido (id_usuario, fecha, estado, total) VALUES
(1, '2025-09-10', 'Pendiente', 45.00),
(2, '2025-09-09', 'Completado', 90.00);

-- Insertar items de pedido
INSERT INTO Pedido_Item (id_producto, id_pedido, cantidad, precio_unitario, total) VALUES
(1, 1, 1, 45.00, 45.00),
(2, 2, 1, 90.00, 90.00);

-- Insertar reclamos
INSERT INTO Reclamo (id_usuario, rol, fecha_pedido, motivo_reclamo, detalle, estado) VALUES
(1, 'ROLE_COMPRADOR', '2025-09-10', 'Producto dañado', 'El envase llegó dañado en un costado', 'Pendiente');
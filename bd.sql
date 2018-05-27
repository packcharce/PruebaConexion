DROP DATABASE if exists `bd`;
create database bd;
use bd;

CREATE TABLE cliente (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    apellido1 VARCHAR(30) NOT NULL,
    apellido2 VARCHAR(30) DEFAULT NULL,
    tlfno VARCHAR(9) NOT NULL,
    calle VARCHAR(50),
    portal VARCHAR(10),
    piso VARCHAR(3) DEFAULT NULL,
    puerta VARCHAR(3) DEFAULT NULL,
    urbanizacion VARCHAR(100) DEFAULT NULL,
    usuario VARCHAR(30),
    contrasenia CHAR(96),
    codigoPostal VARCHAR(5),
    pedidoActual INT,
    fechaAlta DATETIME NOT NULL DEFAULT NOW(),
    fechaBaja DATETIME DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE historico (
    refCliente INT NOT NULL,
    idPedido INT UNIQUE NOT NULL
);

CREATE TABLE pedido (
    id INT NOT NULL AUTO_INCREMENT,
    refCliente INT NOT NULL,
    numPedido VARCHAR(30) NOT NULL,
    fechaPedido DATETIME NOT NULL default now(),
    extra_domicilio FLOAT DEFAULT 0,
    extra_local FLOAT DEFAULT 0,
    extra_recoger FLOAT DEFAULT 0,
    subtotal FLOAT DEFAULT 0,
    impuesto FLOAT DEFAULT 0,
    total FLOAT DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE listaPizzas (
	id INT NOT NULL AUTO_INCREMENT,
    refPedido INT NOT NULL,
    refPizza INT NOT NULL,
    PRIMARY KEY (id)

);

CREATE TABLE pizza (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50),
    precio float default 0,
    PRIMARY KEY (id)
);

CREATE TABLE listaIngredientes (
	id int not null auto_increment,
    refPizza INT NOT NULL,
    refIngrediente INT NOT NULL,
    mitad varchar(1) DEFAULT 0,
    primary key (id)
);

CREATE TABLE ingrediente (
    id INT NOT NULL AUTO_INCREMENT,
    tipo VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    precio FLOAT NOT NULL,
    CHECK (stock >= 0),
    PRIMARY KEY (id)
);

CREATE TABLE hamburguesa (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    ingredientes VARCHAR(255) NOT NULL,
    precio FLOAT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE lista_hamb (
	id int not null auto_increment,
    refPedido INT NOT NULL,
    refHamb INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ensalada (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    ingredientes VARCHAR(255) NOT NULL,
    precio FLOAT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE lista_ensal (
	id int not null auto_increment,
    refPedido INT NOT NULL,
    refEnsal INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE pasta (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    ingredientes VARCHAR(255) NOT NULL,
    precio FLOAT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE lista_pasta (
	id int not null auto_increment,
    refPedido INT NOT NULL,
    refPasta INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE lasania (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    ingredientes VARCHAR(255) NOT NULL,
    precio FLOAT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE lista_lasania (
	id int not null auto_increment,
    refPedido INT NOT NULL,
    refLasania INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE bebida (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    ingredientes VARCHAR(255) NOT NULL,
    precio FLOAT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE lista_bebida (
	id int not null auto_increment,
    refPedido INT NOT NULL,
    refBebida INT NOT NULL,
    PRIMARY KEY (id)
);

-- ----------------------------------------------------------------------------------------------------------------
-- ------------------------------------------- ALTERS -------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------

alter table lista_bebida
	-- add constraint pk_listaBebida primary key(refPedido, refBebida),
    add constraint fk_pedido_listaBebida foreign key(refPedido) references pedido(id),
    add constraint fk_listaBebida_bebida foreign key(refBebida) references bebida(id)
;

alter table lista_lasania
	-- add constraint pk_listaLasania primary key(refPedido, refLasania),
    add constraint fk_pedido_listaLasania foreign key(refPedido) references pedido(id),
    add constraint fk_listaLasania_lasania foreign key(refLasania) references lasania(id)
;

alter table lista_pasta
	-- add constraint pk_listaPasta primary key(refPedido, refPasta),
    add constraint fk_pedido_listaPasta foreign key(refPedido) references pedido(id),
    add constraint fk_listaPasta_pasta foreign key(refPasta) references pasta(id)
;

alter table lista_ensal
	-- add constraint pk_listaEnsal primary key(refPedido, refEnsal),
    add constraint fk_pedido_listaEnsal foreign key(refPedido) references pedido(id),
    add constraint fk_listaEnsal_ensal foreign key(refEnsal) references ensalada(id)
;

alter table lista_hamb
	-- add constraint pk_listaHamb primary key(refPedido, refHamb),
    add constraint fk_pedido_listaHamb foreign key(refPedido) references pedido(id),
    add constraint fk_listaHamb_hamb foreign key(refHamb) references hamburguesa(id)
;

alter table listaIngredientes
	-- add constraint pk_listaIngredientes primary key(id),
    add constraint fk_pizza_listaIngredientes foreign key(refPizza) references pizza(id),
    add constraint fk_listaIngredientes_ingrediente foreign key(refIngrediente) references ingrediente(id)
;

alter table listaPizzas
	-- add constraint pk_listaPizzas primary key(refPedido, refPizza),
    add constraint fk_pedido_listaPizzas foreign key(refPedido) references pedido(id),
    add constraint fk_listaPizzas_pizza foreign key(refPizza) references pizza(id)
;

alter table pedido	
    add constraint fk_pedido_cliente foreign key(refCliente) references cliente(id)
;        

alter table historico	
    add constraint fk_historico_pedido foreign key(idPedido) references pedido(id),
    add constraint fk_cliente_historico foreign key(refCliente) references cliente(id)
;

alter table cliente
	add constraint unique_user unique(usuario)
	-- add constraint fk_historial_pedidos foreign key(historialPedidos) references historico(id)
;
 
 
-- ----------------------------------------------------------------------------------------------------------------
-- ------------------------------------------- INSERTS ------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------
    
insert into bebida (nombre, ingredientes, precio) values ('Coca Cola', 'Misterio', 0.5);
insert into bebida (nombre, ingredientes, precio) values ('Fanta Naranja', 'Naranja, gases', 0.3);
insert into bebida (nombre, ingredientes, precio) values ('Fanta Limon', 'Limon, gases', 0.3);
insert into bebida (nombre, ingredientes, precio) values ('Nestea', 'Té verde', 0.45);

insert into lasania (nombre, ingredientes, precio) values ('Carne', 'Carne picada, Queso, Tomate', 3.5);
insert into lasania (nombre, ingredientes, precio) values ('Atun', 'Atun, Queso, Tomate', 3.5);
insert into lasania (nombre, ingredientes, precio) values ('Vegetal', 'Pimiento, Lechuga, Queso, Tomate', 3.5);

insert into hamburguesa (nombre, ingredientes, precio) values ('Hamburguesa de cerdo', 'Carne picada de cerdo, Queso, Tomate, ketchup, lechuga', 4.5);
insert into hamburguesa (nombre, ingredientes, precio) values ('Hamburguesa de tofu', 'Tofu, Queso, Tomate, ketchup, lechuga', 4.5);
insert into hamburguesa (nombre, ingredientes, precio) values ('Hamburguesa de ternera', 'Carne picada de ternera, Queso, Tomate, ketchup, lechuga', 4.5);

insert into ensalada (nombre, ingredientes, precio) values ('Ensalada César', 'Lechuga, Queso, Pan tostado, Salsa César', 6.5);
insert into ensalada (nombre, ingredientes, precio) values ('Ensalada Sola', 'Lechuga, Tomate, Aceitunas', 5.5);
insert into ensalada (nombre, ingredientes, precio) values ('Ensalada César', 'Lechuga, Queso, Pan tostado, Salsa César', 4.5);

insert into pasta (nombre, ingredientes, precio) values ('Carbonara', 'Salsa Carbonara, Bacon', 4.5);
insert into pasta (nombre, ingredientes, precio) values ('Boloñesa', 'Salsa Boloñesa, Carne', 4.5);

insert into ingrediente (tipo, nombre, stock, precio) values ('Lacteo', 'Queso mozzarela', 245, 0.5);
insert into ingrediente (tipo, nombre, stock, precio) values ('Lacteo', '4 Quesos', 297, 0.5);
insert into ingrediente (tipo, nombre, stock, precio) values ('Carne', 'Carne Picada', 185, 0.5);
insert into ingrediente (tipo, nombre, stock, precio) values ('Carne', 'Bacon', 221, 0.5);
insert into ingrediente (tipo, nombre, stock, precio) values ('Carne', 'Salami', 185, 0.5);
insert into ingrediente (tipo, nombre, stock, precio) values ('Vegetal', 'Pimiento', 175, 0.5);
insert into ingrediente (tipo, nombre, stock, precio) values ('Vegetal', 'Tomate', 275, 0.5);
insert into ingrediente (tipo, nombre, stock, precio) values ('Vegetal', 'Tomate Cherry', 158, 0.5);

-- insert into pizza (id, nombre) values (1, "Barbacoa");
-- insert into pizza (id, nombre) values (2, "Carbonara");
-- insert into pizza (id, nombre) values (3, "4 Quesos");
-- 
-- insert into listaIngredientes(refPizza, refIngrediente, mitad, extra) values (1, 3, 0, 0);
-- insert into listaIngredientes(refPizza, refIngrediente, mitad, extra) values (1, 4, 0, 0);
-- insert into listaIngredientes(refPizza, refIngrediente, mitad, extra) values (1, 6, 0, 0);
-- insert into listaIngredientes(refPizza, refIngrediente, mitad, extra) values (2, 1, 0, 0);
-- insert into listaIngredientes(refPizza, refIngrediente, mitad, extra) values (3, 2, 0, 0);


-- ----------------------------------------------------------------------------------------------------------------
-- ------------------------------------------- PROCEDURES ---------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------

drop procedure if exists crea_cliente;
DELIMITER //
CREATE PROCEDURE crea_cliente(
	in nombreArg varchar(30), in apellido1Arg varchar(30), in apellido2Arg varchar(30), 
    in tlfnoArg varchar(9), in calleArg varchar(50), in portalArg varchar(10), in pisoArg varchar(3), in puertaArg varchar(3) , in urbanizacionArg varchar(100),
    in usuarioArg varchar(30), in contraseniaArg varchar(50), in codigoPostalArg varchar(5)
)
	begin
	DECLARE exit handler for sqlexception
	  BEGIN
		-- ERROR
		signal sqlstate '45000' SET MESSAGE_TEXT = 'Error: Usuario Duplicado', MYSQL_ERRNO = 1062;
	  ROLLBACK;
	END;

	DECLARE exit handler for sqlwarning
	 BEGIN
		-- WARNING
		signal sqlstate '45000' SET MESSAGE_TEXT = 'Warning', MYSQL_ERRNO = 1062;
	 ROLLBACK;
	END;
	START TRANSACTION;
		insert into cliente 
			(nombre, apellido1, apellido2, tlfno, calle, portal, piso, puerta, urbanizacion, usuario, contrasenia, codigoPostal)
		values 
		(nombreArg, apellido1Arg, apellido2Arg, tlfnoArg, calleArg, portalArg, pisoArg, puertaArg, urbanizacionArg, usuarioArg, SHA2(contraseniaArg, 384), codigoPostalArg);
	commit;
end
//
DELIMITER ;

drop procedure if exists crea_pedido;
DELIMITER //
CREATE PROCEDURE crea_pedido(
	in refClienteA int, in numPedidoA varchar(30), in extra_domicilioA float, 
    in extra_recogerA float, in extra_localA float, in subtotalA float, in impuestoA float, in totalA float 
)
	begin
	DECLARE exit handler for sqlexception
	  BEGIN
		-- ERROR
		signal sqlstate '45000' SET MESSAGE_TEXT = 'Error: Pedido Duplicado', MYSQL_ERRNO = 1062;
	  ROLLBACK;
	END;

	DECLARE exit handler for sqlwarning
	 BEGIN
		-- WARNING
		signal sqlstate '45000' SET MESSAGE_TEXT = 'Warning', MYSQL_ERRNO = 1062;
	 ROLLBACK;
	END;
	START TRANSACTION;
		insert into pedido 
			(refCliente, numPedido, extra_domicilio, extra_local, extra_recoger, subtotal, impuesto, total)
		values 
		(refClienteA, numPedidoA, extra_domicilioA, extra_localA, extra_recogerA, subtotalA, impuestoA, totalA);
	commit;
end
//
DELIMITER ;


-- Test
insert into cliente 
		(nombre, apellido1, apellido2, tlfno, calle, portal, piso, puerta, urbanizacion, usuario, contrasenia, codigoPostal)
	values 
    ("Luis", "Sanchez", "ap2", "942745557", "Vargas", "54", 2, "B", "urb",  "a", SHA2('1234', 384), 39532);

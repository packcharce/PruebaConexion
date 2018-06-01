use bd;

SET GLOBAL general_log = 'ON';

select pizza.nombre, ingrediente.tipo, ingrediente.nombre, ingrediente.stock, ingrediente.precio  from ((pizza
	inner join listaIngredientes on pizza.id = listaIngredientes.refPizza)
    inner join ingrediente on listaIngredientes.refIngrediente = ingrediente.id);

select tipo, nombre, stock, precio from ingrediente;
SELECT SHA2('pepeluisamorasc', 384);

SELECT 
    *
FROM
    cliente;
    
SELECT id FROM cliente WHERE usuario = '43' AND contrasenia = SHA2('a', 384);

SELECT 
    *
FROM
    listaPizzas;
SELECT 
    *
FROM
    pedido;
    SELECT id, refPedido, numPedido, fechaPedido, extra_domicilio, extra_local, extra_local, subtotal, impuesto, total FROM pedido;
SELECT 
    *
FROM
    historico
WHERE
    refCliente = 2;

insert into cliente 
		(nombre, apellido1, apellido2, tlfno, calle, portal, piso, puerta, urbanizacion, usuario, contrasenia, codigoPostal)
	values 
    ("Luis", "Sanchez", "ap2", "942745557", "Vargas", "54", 2, "B", "urb",  "a", SHA2('1234', 384), 39532);
    
SHOW CREATE TABLE listaIngredientes;
 
 insert into pedido(refCliente, numPedido, fechaPedido, extra_domicilio, extra_local, extra_recoger,
subtotal, impuesto, total)
 values(1, "MOV-0", '2018-04-06 09:08:07', 0.0, 0.0, 0.0, 34.2, 1.1, 35.3);
 
insert into pedido(numPedido, fechaPedido, extra_domicilio, extra_local, extra_recoger,
subtotal, impuesto, total)
 values(1, '2018-04-06 09:08:07', 0.0, 0.0, 0.0, 34.2, 1.1, 35.3);

insert into pedido(refCliente, numPedido, fechaPedido, extra_domicilio, extra_local, extra_recoger,
		subtotal, impuesto, total)
	 values(1, 0, '2018-04-06 09:08:07', 0.0, 0.0, 0.0, 34.2, 1.1, 35.3);
 
insert into historico(refCliente, idPedido) values(1, 2);
insert into historico(refCliente, idPedido) values(2, 3);

UPDATE cliente 
SET 
    historialPedidos = 2,
    pedidoActual = 3
WHERE
    id = 2;


CALL crea_cliente("Luis", "Sanchez", "ap2", "942745557", "Vargas", "54", 2, "B", "urb",  "lsancfqesdh", "1234", 39532);
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
		(nombreArg, apellido1Arg, apellido2Arg, tlfnoArg, calleArg, portalArg, pisoArg, puertaArg, urbanizacionArg, usuarioArg, contraseniaArg, codigoPostalArg);
	commit;
end
//
DELIMITER ;



DELIMITER //
CREATE PROCEDURE crea_pedido_cliente(
	in numCliente int, in numeroPedido int,
    in extra_dom float, in extra_loc float,
    in extra_recoger float, in subtotal float,
    in impuesto float, in total float)
BEGIN

	declare auxPedido int;

	insert into pedido(refCliente, numPedido, fechaPedido, extra_domicilio, extra_local, extra_recoger,
		subtotal, impuesto, total)
	 values(numCliente, numeroPedido, now(), extra_dom, extra_loc, extra_recoger, subtotal, impuesto, total);

	SELECT 
    COUNT(*)
	INTO auxPedido FROM
		pedido;
		
	UPDATE cliente 
	SET 
		pedidoActual = auxPedido
	WHERE
		id = numCliente;
		
	insert into historico(refCliente, idPedido) values(numCliente, auxPedido);

END
//
DELIMITER ;

DELIMITER //

CREATE PROCEDURE anhade_pizza_pedido(in numeroPedido int, in nombrePizza varchar(50))
BEGIN
 
	insert into pizza(nombre) values (nombrePizza);
    insert into listaIngredientes(refPizza, refIngrediente, mitad, extra) 
		values ((SELECT id FROM pizza ORDER BY id DESC LIMIT 1)
		, 1, 0, 0);
 
	insert into listaPizzas(refPedido, refPizza)
		values (numeroPedido, (SELECT id FROM pizza ORDER BY id DESC LIMIT 1));

END

//
DELIMITER ;


SELECT 
    *
FROM
    pedido;
 SELECT 
    *
FROM
    lista_bebida;  
 SELECT 
    *
FROM
    listaIngredientes;
 SELECT 
    *
FROM
    lista_pasta;
SELECT 
    *
FROM
    lista_hamb;
    
SELECT 
    *
FROM
    listaPizzas;
 SELECT 
    *
FROM
    lista_ensal;  
    
 SELECT 
    *
FROM
    pizza; 
    
SELECT 
    *
FROM
    lista_lasania;
    
SELECT 
    *
FROM
    cliente;
    
    
call crea_pedido_cliente(1, 5, 0, 0, 0,0,0,0);
call crea_pedido_cliente(2, 1);
call anhade_pizza_pedido(1, "custom");

call crea_pedido(1, "1", 2.0, 1.0, 1.0, 10.0, 2.0, 12.0);

insert into pedido 
			(refCliente, numPedido, extra_domicilio, extra_local, extra_recoger, subtotal, impuesto, total)
		values 
		(1, "1", 2.2, 1.0, 1.4, 10.5, 2.0, 12.0);

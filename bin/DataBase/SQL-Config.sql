-- Cria a table da particulas
CREATE TABLE PARTICLE (
	id integer primary key auto_increment,
	x integer,
	y integer,
	image varchar(200),
	distCentro double,
	tipo integer
);

-- Tabela com o movimento Ou SEJA SOLUCAO
CREATE TABLE MOVIMENTO (
	id integer primary key auto_increment,
	mov_x integer,
	mov_y integer
);

-- VIZINHOS A DISTANCIA AO CENTRO SITIO ONDE ESTA
CREATE TABLE PROBLEMA(
	id integer primary key auto_increment,
	vizinho_CIMA integer,
	vizinho_BAIXO integer,
	vizinho_DIREITA integer,
	vizinho_ESQUERDA integer
);

-- Tabela de casos
CREATE TABLE CASO (
	id_PARTICLE integer,
	id_MOVIMENTO integer,
	id_PROBLEMA integer,
	success integer
);

-- ADICIONAR CHAVES ESTRANGEIRAS
ALTER TABLE CASO ADD FOREIGN KEY (id_PARTICLE) REFERENCES PARTICLE (id);
ALTER TABLE CASO ADD FOREIGN KEY (id_MOVIMENTO) REFERENCES MOVIMENTO (id);
ALTER TABLE CASO ADD FOREIGN KEY (id_PROBLEMA) REFERENCES PROBLEMA (id);

SELECT * FROM PARTICLE;
SELECT * FROM PROBLEMA;
SELECT * FROM MOVIMENTO;
SELECT * FROM CASO;
DELETE FROM MOVIMENTO WHERE mov_x > 0;
DELETE FROM PARTICLE WHERE tipo = 1;
DELETE FROM MOVIMENTO WHERE mov_y = 0;

-- O TIPO DE PROBLEMAS QUE PODE TER VIZINHO CIMA BAIXO ESQUERDA DIREITA
INSERT INTO PROBLEMA (vizinho_CIMA,vizinho_BAIXO,vizinho_DIREITA,vizinho_ESQUERDA) values (0,0,0,0);
INSERT INTO PROBLEMA (vizinho_CIMA,vizinho_BAIXO,vizinho_DIREITA,vizinho_ESQUERDA) values (0,0,0,1);
INSERT INTO PROBLEMA (vizinho_CIMA,vizinho_BAIXO,vizinho_DIREITA,vizinho_ESQUERDA) values (0,0,1,0);
INSERT INTO PROBLEMA (vizinho_CIMA,vizinho_BAIXO,vizinho_DIREITA,vizinho_ESQUERDA) values (0,1,0,0);
INSERT INTO PROBLEMA (vizinho_CIMA,vizinho_BAIXO,vizinho_DIREITA,vizinho_ESQUERDA) values (1,0,0,0);
INSERT INTO PROBLEMA (vizinho_CIMA,vizinho_BAIXO,vizinho_DIREITA,vizinho_ESQUERDA) values (0,0,1,1);
INSERT INTO PROBLEMA (vizinho_CIMA,vizinho_BAIXO,vizinho_DIREITA,vizinho_ESQUERDA) values (0,1,0,1);
INSERT INTO PROBLEMA (vizinho_CIMA,vizinho_BAIXO,vizinho_DIREITA,vizinho_ESQUERDA) values (1,0,0,1);
INSERT INTO PROBLEMA (vizinho_CIMA,vizinho_BAIXO,vizinho_DIREITA,vizinho_ESQUERDA) values (0,1,1,0);
INSERT INTO PROBLEMA (vizinho_CIMA,vizinho_BAIXO,vizinho_DIREITA,vizinho_ESQUERDA) values (1,0,1,0);
INSERT INTO PROBLEMA (vizinho_CIMA,vizinho_BAIXO,vizinho_DIREITA,vizinho_ESQUERDA) values (1,1,0,0);
INSERT INTO PROBLEMA (vizinho_CIMA,vizinho_BAIXO,vizinho_DIREITA,vizinho_ESQUERDA) values (0,1,1,1);
INSERT INTO PROBLEMA (vizinho_CIMA,vizinho_BAIXO,vizinho_DIREITA,vizinho_ESQUERDA) values (1,0,1,1);
INSERT INTO PROBLEMA (vizinho_CIMA,vizinho_BAIXO,vizinho_DIREITA,vizinho_ESQUERDA) values (1,1,1,1);

SELECT PAT.id,PAT.x,PAT.y,AVG(MOV.mov_x),MOV.mov_y 
	FROM PARTICLE PAT, CASO,MOVIMENTO MOV
WHERE PAT.id = CASO.id_PARTICLE and CASO.id_MOVIMENTO = MOV.id and PAT.x > 30 and PAT.x < 200
GROUP BY PAT.x;

SELECT PARTICLE.x,PARTICLE.Y FROM CASO,PARTICLE WHERE PARTICLE.y = 200;

SELECT MOV.mov_x mov_x,MOV.mov_y mov_y,PAT.x,PAT.y
	FROM MOVIMENTO MOV,CASO C,PARTICLE PAT
WHERE C.id_MOVIMENTO = MOV.id and C.id_PARTICLE = PAT.id;

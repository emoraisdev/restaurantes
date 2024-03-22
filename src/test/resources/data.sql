INSERT INTO endereco (id, bairro,cep,cidade,estado,numero,pais,rua) VALUES
    (201, 'Cruzeiro','12345-321','Curitiba','PR','121','Brasil','Rua XV'),
    (202, 'Centro','12345-321','São Paulo','SP','121','Brasil','Rua Barbosa'),
    (203, 'Xingu','12345-321','São Paulo','SP','121','Brasil','Rua Barbosa');

INSERT INTO restaurante (id, horario_funcionamento,nome,qtd_mesas,telefone,tipo_cozinha,endereco_id) VALUES
    (101, '16:00h as 23:30h todos os dias','Bar e Pizza',60,'11 11111-1111','Pizza',201),
    (102, '16:00h as 00:00h todos os dias','Bar do Pedro',10,'11 11111-1111','Bar',202),
    (103, '16:00h as 22:00h todos os dias','Bar do Zeca',30,'11 11111-1111','Bar',203);
# Use a imagem oficial do MySQL como base
FROM mysql:latest

COPY init.sql /docker-entrypoint-initdb.d/

# Exponha a porta 3306 para que outras aplicações possam se conectar
EXPOSE 3306
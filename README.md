# LibraryApplication

Aplicação de sistema de biblioteca utilizando Spring Boot.

O banco de dados postgres e seu manager estão em docker. Segue o código de instalação e configuração:
- para postgres: ```docker docker run --name pgadmin4 -p 15432:80 -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin --network library-network dpage/pgadmin4 ---

Requests http (get, post, put) funcionando perfeitamente seguindo os padrões REST.
Atualmente trabalhando com segurança e visualização.

# LibraryApplication

Aplicação de sistema de biblioteca utilizando Spring Boot.

O banco de dados postgres e seu manager estão em docker. Segue o código de instalação e configuração:
- para postgres:
  
```docker docker run --name pgadmin4 -p 15432:80 -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin --network library-network dpage/pgadmin4```
- para o pgAdmin:
  
```docker run --name librarydb -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=library -p 5432:5432 -d --network library-network postgres:16.3```

Requests http (get, post, put) funcionando perfeitamente seguindo os padrões REST.
Atualmente trabalhando com segurança e visualização.

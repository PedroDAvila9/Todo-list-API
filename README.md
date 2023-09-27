
## Usage

FIXME: explanation

    $ java -jar todo-list-api-0.1.0-standalone.jar [args]

# Todo List API

Este projeto é uma API simples para gerenciar uma lista de tarefas usando Clojure.

## Pré-requisitos

- Java
- Leiningen ou Clojure CLI (dependendo da sua configuração)
- PostgreSQL

## Configuração do Banco de Dados

1. **Instale o PostgreSQL**:
   Se você ainda não tem o PostgreSQL instalado, siga as instruções oficiais para sua plataforma: [https://www.postgresql.org/download/](https://www.postgresql.org/download/)

2. **Crie o Banco de Dados**:
   Abra o terminal ou o pgAdmin e execute os seguintes comandos:

   ```sql
   CREATE DATABASE postgres;

    CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    completed BOOLEAN NOT NULL
);

  **Configuração do Projeto**
    Clone o Repositório:
    
    git clone [URL_DO_SEU_REPOSITÓRIO]

**Baixe as Dependências:**

Se estiver usando Leiningen:

lein deps

Se estiver usando tools.deps:

clojure -R:alguma-tag

**Execute o Projeto:**


lein run
Endpoints
GET /tasks: Retorna todas as tarefas.
POST /tasks: Cria uma nova tarefa.
PUT /tasks/:id: Atualiza uma tarefa existente.
DELETE /tasks/:id: Exclui uma tarefa.


## License

Copyright © 2023 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

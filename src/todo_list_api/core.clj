(ns todo-list-api.core
  (:require [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.cors :refer [wrap-cors]]
            [compojure.core :refer [defroutes GET POST ANY DELETE PUT]]
            [clojure.java.jdbc :as jdbc]
            [ring.adapter.jetty :refer [run-jetty]])
  (:import [java.sql Timestamp]
           [org.joda.time DateTime]))

(def db-spec {:dbtype "postgresql"
              :dbname "postgres"
              :user "postgres"
              :password "postgres"
              :host "localhost"
              :port 5432})

(def tasks
  [{:description "Completar o projeto" :completed false}
   {:description "Estudar Clojure" :completed false}])

(defn insert-tasks []
  (doseq [task tasks]
    (try
      (jdbc/insert! db-spec
                    :tasks
                    {:description (:description task)
                     :created_at (Timestamp. (.getMillis (DateTime.)))
                     :completed (:completed task)})
      (catch Exception e
        (println "Erro ao inserir tarefa:" e)))))

(defn get-tasks []
  ;; Obter lista de tarefas do banco de dados
  (try
    (let [tasks (jdbc/query db-spec
                            ["SELECT * FROM tasks"]
                            {:row-fn (fn [row] (assoc row :created_at (str (:created_at row))))})]
      (println "Tarefas recuperadas:" tasks)
      {:status 200 :headers {"Content-Type" "application/json"} :body tasks})
    (catch Exception e
      (println "Erro ao obter tarefas:" e)
      {:status 500 :body "Erro ao obter tarefas"})))


(defn create-task [description]
  ;; Criar uma nova tarefa no banco de dados
  (try
    (let [result (jdbc/insert! db-spec
                               :tasks
                               {:description description
                                :created_at (Timestamp. (.getMillis (DateTime.)))
                                :completed false})]
      (println "Tarefa criada:" result)
      {:status 201 :body result})
    (catch Exception e
      (println "Erro ao criar tarefa:" e)
      {:status 500 :body "Erro ao criar tarefa"})))

(defn updated-task [id description completed]
  (try
    (jdbc/update! db-spec :tasks {:description description :completed completed} ["id = ?" id])
    (println "Tarefa atualizada com o ID:" id)
    {:status 200 :body "Tarefa atualizada com sucesso"}
    (catch Exception e
      (println "Erro ao atualizar tarefa com o ID:" id e)
      {:status 500 :body "Erro ao atualizar tarefa"})))


(defn delete-task [id]
  (try
    (jdbc/delete! db-spec :tasks ["id = ?" id])
    {:status 200 :body "Tarefa deletada com sucesso"}
    (catch Exception e
      (println "Erro ao deletar tarefa:" e)
      {:status 500 :body "Erro ao deletar tarefa"})))


(defroutes app-routes
  (GET "/tasks" [] (get-tasks))
  (POST "/tasks" [data] (create-task (:description data)))
  (DELETE "/tasks/:id" [id] (delete-task (Integer. id)))
  (PUT "/tasks/:id" [id data] (updated-task (Integer. id) (:description data) (:completed data)))
  (ANY "*" [] {:status 404 :body "Not Found"})) 

(def app
  (-> app-routes
      wrap-json-params
      wrap-json-response
      (wrap-cors :access-control-allow-origin [#"http://localhost:3000"]
                 :access-control-allow-methods [:get :put :post :delete])
      (wrap-defaults site-defaults)))


(defn -main []
  (insert-tasks)
  (run-jetty app {:port 3000}))

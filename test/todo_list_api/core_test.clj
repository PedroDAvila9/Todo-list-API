(ns todo-list-api.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [todo-list-api.core :refer [get-tasks create-task]]))

(deftest test-create-task
  (testing "Teste para criar uma tarefa"
    (is (= 1 (count (get-tasks))))
    (create-task "Minha primeira tarefa")
    (is (= 2 (count (get-tasks))))))

(deftest test-some-other-feature
  (testing "Outro teste"))

(ns cortex.tree-test
  (:require
    #?(:cljs [cljs.test :refer-macros [deftest is are testing]]
        :clj [clojure.test :refer [deftest is are testing]])
    [clojure.core.matrix :as mat]
    [clojure.core.matrix.random :as randm]
    #?(:cljs [thi.ng.ndarray.core :as nd])
    [clojure.zip :as zip]
    [cortex.tree :as tree]))

(def IRISES (read-string (slurp "test_data/iris.edn")))
(def X (mat/matrix (map drop-last IRISES)))
(def Y (mat/matrix (map last IRISES)))

(deftest tree-classify-test
  []
  (let [tree (tree/decision-tree X Y {:split-fn tree/best-splitter})
        sample (first IRISES)
        classification (tree/tree-classify tree (butlast sample))]
    (is (= classification (last sample)))))

(deftest forest-classify-test
  []
  (let [forest (tree/random-forest X Y {:n-trees 100
                                        :split-fn tree/best-splitter})
        sample (first IRISES)
        classification (tree/forest-classify forest (butlast sample))]
    (is (= classification (last sample)))))

(ns nado-web.routes.home
  (:use compojure.core)
  (:require [clojure.string :as str]
            [clj-redis.client :as redis] 
            [nado-web.views.layout :as layout]
            [nado-web.util :as util]
            [nado-web.models.redis-db :refer [rdb user-id]]))

(def user-keys ["Uid" "Name" "Money" "LingShi" "Level"])

(def prop-keys ["Pid" "Qpos" "Number" "Pos" "SaleNum"])

(def prop-key-pattern (format "nadouser:%s:item:*:iteminfo" @user-id))

(def user-headers (map str/capitalize user-keys)) 

(defn hmget [key fields]
    (apply redis/hmget (concat [@rdb key] fields)))

(defn user-data [] 
    (apply redis/hmget (concat [@rdb (format "nadouser:%s:userinfo" @user-id)] user-keys)))

(defn prop-data [key]
    (apply redis/hmget (concat [@rdb key] prop-keys)))

(defn first-and-num [col]
    (-> col (first) (Long/valueOf)))

(defn all-prop-data []
    (let [keys (redis/keys @rdb prop-key-pattern)]
     (sort-by first-and-num (map #(hmget % prop-keys) keys))))

(def config-keys (map #(format "nadouser:%s:%s" @user-id %) ["propslots" "runeslots" "battlearray"]))

(defn get-config-data  [name]
    (let [key (format "nadouser:%s:%s" @user-id name)
          length (redis/llen @rdb key)]
     (cons name (redis/lrange @rdb key  0 length))))

(defn all-config-data []
    (let [config-names ["propslots" "runeslots" "battlearray"]]
        (map get-config-data config-names)))

(defn home-page []
  (layout/render
    "home.html" {:content (util/md->html "/md/docs.md")
                 :user-headers user-headers
                 :user-data (user-data)
                 :configuration (all-config-data)
                 :columns (range 6)
                 :prop-fields prop-keys
                 :all-prop-data (all-prop-data)
                }))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page)))

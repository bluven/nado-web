(ns nado-web.models.redis-db
    [:require [clj-redis.client :as redis]]
    (:gen-class))

(defonce rdb (atom (redis/init)))

(defonce user-id (atom 10006))

(defn init
     [ & {:keys [url uid ] :or {url "redis://127.0.0.1:6379" uid @user-id }}]
     (reset! rdb (redis/init {:url url}))
     (reset! user-id uid))


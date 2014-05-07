(ns nado-web.models.schema)

(def db-spec
  {:subprotocol "mysql"
   :subname "//localhost:3306/nado_web"
   :user "root"
   :password "root"})

(def redis-spec 
    {:url "redis://"
      :uid 10006})

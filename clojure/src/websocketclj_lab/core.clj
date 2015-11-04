(ns websocketclj_lab.core
  (:require
    [immutant.web             :as web]
    [immutant.web.async       :as async]
    [immutant.web.middleware  :as web-middleware]
    [compojure.route          :as route]
    [environ.core             :refer (env)]
    [compojure.core           :refer (ANY GET defroutes)]
    [clj-json.core            :as json]
    [ring.util.response       :refer (response redirect content-type)])
  (:gen-class))

(def geohash_index (atom []))

(defn command-my-position
    "handler of 'my_position' command from client."
    [ch params]
    (let [longitude (get params "longitude")
          user (get params "user")
          latitude (get params "latitude")]

      (println "Command: [my-position]")
      (println (str "User: " user))
      (println (str "Long/lat: " longitude  "/" latitude " " ))

      )))



(defn command-wrong
  "Issue on a wrong command"
  [ch packet]
  (async/send! ch (str "Wrong command '" ("command" packet) "'")))

(defn call-command [ch command packet]
  (let [func-name (str "command-" command)
        func (ns-resolve 'websocketclj_lab.core (symbol func-name))]
    (println func-name)
    (println func)
    (when func
      (func ch packet))))


(def websocket-callbacks
  "WebSocket callback functions"
  {:on-open   (fn [channel]
                (println "New connect received")
                (async/send! channel "Ready to reverse your messages!"))
   :on-close   (fn [channel {:keys [code reason]}]
                 (println "close code:" code "reason:" reason))
   :on-message (fn [ch m]
                 (let [packet (json/parse-string m)
                       command (clojure.string/trim (get packet "command" "wrong"))]
                   (println (count command))
                   (call-command ch command packet)
                   (async/send! ch (apply str (reverse m)))))})


(defroutes routes
  (GET "/" {c :context} (redirect (str c "/index.html")))
  (route/resources "/"))


(defn -main [& {:as args}]
  (web/run
    (-> routes
      (web-middleware/wrap-session {:timeout 20})
      ;; wrap the handler with websocket support
      ;; websocket requests will go to the callbacks, ring requests to the handler
      (web-middleware/wrap-websocket websocket-callbacks))
      (merge {"host" (env :demo-web-host), "port" (env :demo-web-port)}
      args)))

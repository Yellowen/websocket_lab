(ns alpha-client.core
  (:require
   [gniazdo.core :as ws]
   [clojure.core.async :as async]))



(defn command-help [socket ch & args]
  (println "help"))

(defn command-users [])

(defn find-command [socket ch input]
  (let [command (clojure.string/trim (first input))
        func (ns-resolve 'alpha-client.core (symbol (str "command-" command)))]
    (if func
      (func socket ch (rest input))
      (println (str "Can't find '" command "'.")))))

(defn read-command []
  (print "> ")
  (flush)
  (clojure.string/split (read-line) #" "))

(defn -main [& {:as args}]
  (let [recv   (async/chan)
        socket (ws/connect (get args "connect")
                           :on-receive #(async/go (println (str "Log:" %))
                                                  (async/>! recv %)))]

    (println (str "Connecting to " (get args "connect")))
    (loop [input (read-command)]
      (when-not (= "exit" (clojure.string/trim (first input)))
        (try
          (find-command socket recv input)
          (catch Exception e (println e)))
        (recur (read-command))))

    (ws/close socket)))

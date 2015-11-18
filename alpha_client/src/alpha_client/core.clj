(ns alpha-client.core
  [gniazdo.core :as ws]
  (:gen-class))


(ws/send-msg socket "hello")
(ws/close socket)

(defn -main [& {:as args}]
  (def socket
    (ws/connect
     (:connect args)
     :on-receive #(prn 'received %)))

  (println args))

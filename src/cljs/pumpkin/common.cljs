(ns pumpkin.common
 (:require  [clojure.string   :as str]
            [cljs-time.core   :as t]
            [cljs-time.format :as tf]
            [cljs-time.coerce :as tc]))

(enable-console-print!)

(defn log [& msgs]
  (apply println msgs))

(defmulti unparse-date (fn [timestamp f] (type timestamp)))

(defmethod unparse-date js/Date [timestamp f]
  (when-not (nil? timestamp)
    (let [date (tc/from-date timestamp)]
      (tf/unparse (tf/formatter f) date))))

(defmethod unparse-date js/String [timestamp f]
  (when-not (nil? timestamp)
    (let [parsed (tf/parse (tf/formatter "yyyy-MM-dd'T'HH:mm:ss.SSSZ") timestamp)
          date   (tc/to-date parsed)]
      (unparse-date date f))))

(defn get-date-from-week
  "Turn unix representation of a first day of a week into Date."
  [w]
  (new js/Date (* w 1000)))

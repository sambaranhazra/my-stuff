(defproject my-stuff "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[nightlight/lein-nightlight "2.3.2"]]
  :dependencies [[org.clojure/clojure "1.9.0"] [proto-repl "0.3.1"]]
  :repl-options {:init (do (use 'my-stuff.core))
                 :skip-default-init true}
  :main my-stuff.core)

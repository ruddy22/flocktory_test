# Test task for Flocktory

A tiny Clojure project about data processing.
The project has a several main points:

1. Send requests (based on query params) to the Bing Search Sevice
2. Process the responses
3. Calculate some statistics and return it back to the user

## Usage

After downloading the repository, the application can be launched as follows:

```bash
> lein deps
...
> lein run
```

Also, the tests can be lounched as follows:

```lein test```

To get results use follow command:

```curl http://localhost:8080/search\?query\=haskell\&query\=scala\&query\=clojure\&query\=lisp```

## Requirements

Needs a Java 10+ and JDK 10+

## Dependencies

Dependencies list:

- `http-kit "2.2.0"` - lightweight server ([link](https://github.com/http-kit/http-kit))
- `compojure "1.6.0"` - router ([link](https://github.com/weavejester/compojure))
- `com.climate/claypoole` - threadpool tools ([link](https://github.com/TheClimateCorporation/claypoole))
- `org.clojars.scsibug/feedparser-clj "0.4.0"` - rss feed parser ([link](https://github.com/scsibug/feedparser-clj))
- `org.clojure/data.json "0.2.6"` - json tools ([link](https://github.com/clojure/data.json))
- `hiccup "1.0.5"` - html tools ([link](https://github.com/weavejester/hiccup))

## License

Copyright © 2018 Aleksei Petrov

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

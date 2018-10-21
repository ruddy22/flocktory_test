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

- `http-kit "2.2.0"` - lightweight server
- `compojure "1.6.0"` - router
- `com.climate/claypoole` - threadpool tools
- `org.clojars.scsibug/feedparser-clj "0.4.0"` - rss feed parser
- `org.clojure/data.json "0.2.6"` - json tools
- `hiccup "1.0.5"` - html tools

## License

Copyright © 2018 Aleksei Petrov

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

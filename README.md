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

```curl http://localhost:8080/search\?query\=haskell\&query\=scala\&query\=clojure\&query\=lisp\&query\=javascript```

## License

Copyright © 2018 Aleksei Petrov

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

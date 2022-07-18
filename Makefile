.PHONY: format
format:
	cljstyle check

.PHONY: lint
lint:
	clj-kondo --lint src

.PHONY: static-check
static-check: format lint

.PHONY: clean
clean:
	rm -fr target/

.PHONY: test
test:
	bin/kaocha --fail-fast

.PHONY: build
build: clean
	clojure -T:build uber

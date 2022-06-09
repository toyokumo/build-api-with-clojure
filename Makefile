.PHONY: format
format:
	cljstyle check

.PHONY: lint
lint:
	clj-kondo --lint src

.PHONY: static-check
static-check: format lint

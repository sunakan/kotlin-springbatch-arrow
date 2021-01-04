################################################################################
# 変数
################################################################################
IGNORE_OS       := linux,macos,windows
IGNORE_EDITOR   := vim,emacs,intellij+all,visualstudiocode
IGNORE_LANGUAGE := c,c++,rust,python,ruby,rust,go,java,kotlin,node,erlang,elixir,commonlisp,racket,scala
IGNORE_TOOL     := vagrant,ansible,terraform,gradle,maven
IGNORE_LIST     := $(IGNORE_LANGUAGE),$(IGNORE_OS),$(IGNORE_EDITOR),$(IGNORE_TOOL)
GIT_IGNORE_URL  := https://www.toptal.com/developers/gitignore/api/$(IGNORE_LIST)

################################################################################
# タスク
################################################################################
.gitignore:
	curl --output .gitignore $(GIT_IGNORE_URL)

.PHONY: setup-gitignore
setup-gitignore: .gitignore ## .gitignoreをsetup
	@make --no-print-directory add-ytt-for-gitignore
	@make --no-print-directory add-rq-for-gitignore

.PHONY: add-ytt-for-gitignore
add-ytt-for-gitignore:
	grep 'ytt' .gitignore || echo 'ytt' >> .gitignore

.PHONY: add-rq-for-gitignore
add-rq-for-gitignore:
	grep 'rq' .gitignore || echo 'rq' >> .gitignore

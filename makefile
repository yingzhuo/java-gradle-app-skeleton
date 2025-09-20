# ----------------------------------------------------------------------------------------------------------------------
# 本文件只保证在MacOS/Linux系统上能正确执行
# ----------------------------------------------------------------------------------------------------------------------

MAKEFILE_PATH := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
GRADLEW := $(MAKEFILE_PATH)/gradlew
GRADLE := $(shell which gradle)

.PHONY: usage \
clean purge rebuild-build-logic \
update-dependencies compile build check test \
build-docker-image remove-docker-image \
update-gradle-wrapper \
update-license-header \
push-to-vcs reset-and-push-to-vcs \
show-projects show-tasks

usage:
	@echo '======================================================================'
	@echo 'usage (default)                : 显示本菜单'
	@echo 'clean                          : 清理本项目构建产物'
	@echo 'purge                          : 清理本项目构建产物以及构建逻辑'
	@echo 'rebuild-build-logic            : 构建构建逻辑'
	@echo 'update-dependencies            : 更新依赖'
	@echo 'compile                        : 编译项目'
	@echo 'build                          : 构建项目'
	@echo 'build-docker-image             : 构建docker镜像'
	@echo 'remove-docker-image            : 删除docker镜像'
	@echo 'test                           : 执行单元测试'
	@echo 'check                          : 检查代码风格'
	@echo 'update-gradle-wrapper          : 设置gradle-wrapper'
	@echo 'stop-gradle-daemon             : 停止gradle-daemon'
	@echo 'update-license-header          : 添加代码的许可证头'
	@echo 'push-to-vcs                    : 提交文件'
	@echo 'show-projects                  : 展示项目信息'
	@echo 'show-tasks                     : 展示任务信息'
	@echo '======================================================================'

clean:
	@$(GRADLEW) 'clean' -q

purge:
	@$(GRADLEW) 'clean' ':buildSrc:clean' -q
	@rm -rf $(MAKEFILE_PATH)/.gradle/
	@rm -rf $(MAKEFILE_PATH)/buildSrc/.gradle/

rebuild-build-logic:
	@$(GRADLEW) ':buildSrc:clean' -q
	@$(GRADLEW) ':buildSrc:jar' -q

update-dependencies:
	@$(GRADLEW) -U

compile:
	@$(GRADLEW) 'classes'

build:
	@$(GRADLEW) 'build' -x 'test' -x 'check'

build-docker-image:
	@$(GRADLEW) 'buildDockerImage' -x 'test' -x 'check'

remove-docker-image:
	@$(GRADLEW) 'removeDockerImage'

check:
	@$(GRADLEW) 'check'

test:
	@$(GRADLEW) 'test'

update-gradle-wrapper:
	@$(GRADLEW) ':wrapper' -q

stop-gradle-daemon:
	@$(GRADLEW) --stop -q > /dev/null

update-license-header:
	@$(GRADLEW) 'licenseFormat' -q

push-to-vcs: update-license-header
	@$(GRADLEW) 'pushToVcs'

show-projects:
	@$(GRADLEW) ':projects' -q

show-tasks:
	@$(GRADLEW) ':tasks' -q

# 骨架项目专有
# 实际项目不需要本目标
reset-and-push-to-vcs:
	@rm -rf $(MAKEFILE_PATH)/.git/
	@git init > /dev/null
	@git remote add github git@github.com:yingzhuo/java-gradle-app-skeleton.git > /dev/null
	@$(GRADLEW) 'pushToVcs'

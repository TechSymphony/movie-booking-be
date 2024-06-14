setup:
	@make stop
	@make build-nocache
	@make start
dev:
	@make stop
	@make start-dev
start-dev:
	@make stop
	docker-compose up setup -d
	docker-compose up es01 -d
	docker-compose up kibana -d
	docker-compose up db -d
	mvnw spring-boot:run
start:
	@make stop
	docker-compose up setup -d
	docker-compose up es01 -d
	docker-compose up kibana -d
	docker-compose up app -d
stop:
	docker-compose down
build-nocache:
	docker build -t movie-self-learning-be:v1 . --no-cache --force-rm
build:
	docker build -t movie-self-learning-be:v1 .
build-dev:
	docker build -t movie-self-learning-be:dev . --target dev

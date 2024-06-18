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
	docker-compose up db -d
	docker-compose up logstash -d
start:
	@make stop
	docker-compose up setup -d
	docker-compose up app -d
	docker-compose up logstash -d
stop:
	docker-compose down
build-nocache:
	docker build -t movie-self-learning-be:v1 . --no-cache --force-rm
build:
	docker build -t movie-self-learning-be:v1 .
build-dev:
	docker build -t movie-self-learning-be:dev . --target dev

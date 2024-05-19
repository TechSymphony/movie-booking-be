setup:
	@make stop
	@make build-nocache
	@make start
dev:
	@make stop
	@make build-dev
	@make start-dev
start-dev:
	@make stop
	docker-compose up dev -d
	docker compose watch --no-up
start:
	@make stop
	docker-compose up app -d
stop:
	docker-compose down
build-nocache:
	docker build -t movie-booking-be:v1 . --no-cache --force-rm
build:
	docker build -t movie-booking-be:v1 .
build-dev:
	docker build -t movie-booking-be:dev . --target dev
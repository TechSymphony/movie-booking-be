setup:
	@make stop
	@make build-nocache
	@make start

start:
	@make stop
	docker-compose up -d
stop:
	docker-compose down
build-nocache:
	docker build -t movie-booking-be:v1 . --no-cache --force-rm
build:
	docker build -t movie-booking-be:v1 .
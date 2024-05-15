start:
	docker compose up -d
stop:
	docker compose down
build:
	docker build -t movie-booking-be:v1 .
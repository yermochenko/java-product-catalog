CREATE TABLE "product" (
	"id" SERIAL PRIMARY KEY,
	"category" TEXT NOT NULL,
	"name" TEXT NOT NULL,
	"price" BIGINT NOT NULL,
	"amount" INT NOT NULL,
	"date" DATE NOT NULL
);

CREATE TABLE "category" (
	"id" SERIAL PRIMARY KEY,
	"name" TEXT NOT NULL
);

CREATE TABLE "product" (
	"id" SERIAL PRIMARY KEY,
	"category_id" INTEGER NOT NULL REFERENCES "category",
	"name" TEXT NOT NULL,
	"price" BIGINT NOT NULL,
	"amount" INT NOT NULL,
	"date" DATE NOT NULL
);

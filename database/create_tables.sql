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

CREATE TABLE "user" (
	"id" SERIAL PRIMARY KEY,
	"login" TEXT NOT NULL UNIQUE,
	"password" TEXT NOT NULL,
	"role" INT NOT NULL CHECK ("role" IN (0, 1, 2, 3))
	/*
	 * 0 - org.itstep.domain.Role.ADMIN
	 * 1 - org.itstep.domain.Role.MANAGER
	 * 2 - org.itstep.domain.Role.COURIER
	 * 3 - org.itstep.domain.Role.CLIENT
	 */
);
